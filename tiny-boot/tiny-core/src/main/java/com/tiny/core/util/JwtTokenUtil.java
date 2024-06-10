package com.tiny.core.util;

import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;

import java.util.Map;

/**
 * Created by lxh at 2024-06-10 09:09:43
 */
@SuppressWarnings("all")
public class JwtTokenUtil {
    private static final String SECRET = "8fbasunfbqwfbsaf";

    public static String generateToken(Map<String, Object> payloads) {
        return JWTUtil.createToken(payloads, JWTSignerUtil.hs256(SECRET.getBytes()));
    }

    public static Map<String, Object> parseToken(String token) {
        boolean verify = JWTUtil.verify(token, JWTSignerUtil.hs256(SECRET.getBytes()));
        if (verify) {
            return JWTUtil.parseToken(token).getPayloads();
        }
        return null;
    }
}
