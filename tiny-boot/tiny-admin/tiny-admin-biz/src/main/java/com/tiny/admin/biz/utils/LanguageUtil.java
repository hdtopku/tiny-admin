package com.tiny.admin.biz.utils;

/**
 * Created by lxh at 2024-10-10 18:27:58
 */
public class LanguageUtil {
    private final static ThreadLocal<String> LANGUAGE_THREAD_LOCAL = new ThreadLocal<>();

    public static void setLanguage(String language) {
        LANGUAGE_THREAD_LOCAL.set(language);
    }

    public static String getLanguage() {
        return LANGUAGE_THREAD_LOCAL.get();
    }

    public void cleanLanguage() {
        LANGUAGE_THREAD_LOCAL.remove();
    }
}
