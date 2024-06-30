package com.tiny.core.web;

import lombok.Data;

/**
 * Created by lxh at 2024-05-29 15:27:40
 */
@Data
public class Result<T> {
    private static final int SUCCESS_CODE = 200;
    private static final String SUCCESS_MESSAGE = "success";
    private static final int FAILURE_CODE = 500;
    private static final String FAILURE_MESSAGE = "failure";
    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }
    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(FAILURE_CODE, message, null);
    }
}
