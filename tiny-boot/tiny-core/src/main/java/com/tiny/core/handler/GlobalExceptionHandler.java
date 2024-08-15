package com.tiny.core.handler;

import com.tiny.core.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by lxh at 2024-06-20 09:11:12
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler implements ErrorController {

    /**
     * 400——Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Object> handleHttpMessageNotReadableException() {
        return Result.failure(HttpStatus.BAD_REQUEST.value(),"请求参数解析失败！");
    }

    /**
     * 401——Username or Password Error
     * 401——Unauthorized 未登录异常
     * 403——Forbidden 无法捕获
     * Spring Security 核心异常（如 AuthenticationException 和 AccessDeniedException）属于运行时异常。由于这些异常是由 DispatcherServlet 后面的 Authentication Filter 在调用 Controller 方法之前抛出的，因此 @ControllerAdvice 无法捕获这些异常。
     *      * 参考：<a href="https://docs.spring.io/spring-security/site/docs/current/reference/html5/#exception-translation">https://springdoc.cn/spring-security-exceptionhandler</a>
     *
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {BadCredentialsException.class, UsernameNotFoundException.class})
    public Result<Object> handleBadCredentialsException() {
        return Result.failure(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误！");
    }

    /**
     * 405——Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<String> handleHttpRequestMethodNotSupportedException() {
        return Result.failure("请求方法不允许");
    }

    /**
     * 415——Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result<Object> handleHttpMediaTypeNotSupportedException() {
        return Result.failure("不支持的媒体类型");
    }


    /**
     * 500：服务器内部异常
     *
     * @param e error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public Result<Object> internalServerError(Exception e) {
        if (e instanceof DuplicateKeyException) {
            String message = e.getMessage();
            if (message.contains("Duplicate entry")) {
                return Result.failure(message.split(" ")[2] + "已存在，请修改！");
            }
        }
        return Result.failure(e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result<Object> handleSqlException(DuplicateKeyException e) {
        String message = e.getCause().getMessage();
        if (message.contains("Duplicate entry")) {
            return Result.failure(message.split(" ")[2] + " 已被占用，请修改！");
        }
        return Result.failure(message);
    }
}