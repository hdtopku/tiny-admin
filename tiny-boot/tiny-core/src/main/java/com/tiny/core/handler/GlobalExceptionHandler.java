package com.tiny.core.handler;

import com.tiny.core.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by lxh at 2024-06-20 09:11:12
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler implements ErrorController {

    @ExceptionHandler(RuntimeException.class)
    public Result<Object> handler(RuntimeException ex) throws Exception {
        log.error("Ops!", ex);
        return this.internalServerError(ex);
    }

    /**
     * 捕获404异常
     *
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<Object> notFoundException(NoHandlerFoundException e) {
        log.error("资源未找到", e);
        return Result.failure("资源未找到");
    }

    /**
     * 400——Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Object> handleHttpMessageNotReadableException() {
        return Result.failure("请求参数解析失败");
    }

    /**
     * 405——Method Not Allowed
     *
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<String> handleHttpRequestMethodNotSupportedException() {
        return Result.failure("请求方法不允许");
    }

    /**
     * 415——Unsupported Media Type
     *
     * @return
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result<Object> handleHttpMediaTypeNotSupportedException() {
        return Result.failure("不支持的媒体类型");
    }

    /**
     * 500：服务器内部异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public Result<Object> internalServerError(Exception e) throws Exception {
        if (e instanceof DuplicateKeyException) {
            String message = e.getMessage();
            if (message.contains("Duplicate entry")) {
                return Result.failure(message.split(" ")[2] + "已存在，请修改！");
            }
        }
        if (e instanceof AccessDeniedException
                || e instanceof AuthenticationException) {
            throw e;
        }
        return Result.failure(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        throw e;
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