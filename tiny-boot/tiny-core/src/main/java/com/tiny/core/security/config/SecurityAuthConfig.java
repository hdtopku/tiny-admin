package com.tiny.core.security.config;

import com.tiny.core.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by lxh at 2024-05-29 18:07:06
 */
@EnableWebSecurity
@EnableMethodSecurity
public abstract class SecurityAuthConfig<T extends UserDetailsBo> implements UserDetailsService {
    private static final String TOKEN_KEY = "user-token";
    @Resource
    private RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint;
    @Resource
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private AuthorizationManager<RequestAuthorizationContext> authorizationManager;
    @Value("${constant.redis-users-token-map-key}")
    private String redisUsersTokenMapKey;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/images/**", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/error", "/websocket").permitAll()
                .anyRequest().access(authorizationManager)
        );
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restfulAuthenticationEntryPoint)
        );
        http.addFilterBefore(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String token = request.getHeader("Authorization");
                if(token == null || !token.startsWith("Bearer ")) {
                    filterChain.doFilter(request, response);
                    return;
                }
                String authToken = token.substring(7);
                Map<String, Object> stringObjectMap = JwtTokenUtil.parseToken(authToken);
                if (stringObjectMap != null && stringObjectMap.containsKey("username")
                        && StringUtils.hasText((String) stringObjectMap.get("username"))) {
//                        Object o = redisService.get(TOKEN_KEY + ":" + stringObjectMap.get("username"));
                    RMapCache<String, Object> userMap = redissonClient.getMapCache(redisUsersTokenMapKey);
                    Object o = userMap.get(stringObjectMap.get("username"));
                    if (o != null) {
                        T userDetails = (T) o;
                        Set<String> tokens = userDetails.getTokens();
                        if (tokens != null && tokens.contains(authToken)) {
                            UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authenticated);
                        }
                    }
                }
                filterChain.doFilter(request, response);
            }
        }, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return new ProviderManager(authenticationProvider);
    }
}
