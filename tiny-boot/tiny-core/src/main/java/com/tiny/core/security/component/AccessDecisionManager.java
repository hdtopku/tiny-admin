package com.tiny.core.security.component;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Created by lxh at 2024-05-29 22:30:27
 */
//@Component
public class AccessDecisionManager implements AuthorizationManager<Collection<String>> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> userAuthentication,
                                       Collection<String> needAuthorities) {
        if (needAuthorities == null || needAuthorities.isEmpty()) {
            return new AuthorizationDecision(true);
        }
        Collection<? extends GrantedAuthority> currentAuthorities = userAuthentication.get().getAuthorities();
        for (GrantedAuthority authority : currentAuthorities) {
            if (needAuthorities.contains(authority.getAuthority())) {
                return new AuthorizationDecision(true);
            }
        }
        return new AuthorizationDecision(false);
    }
}
