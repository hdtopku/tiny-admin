package com.tiny.admin.biz.utils;

import com.tiny.admin.biz.system.service.ISysI18nRawService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lxh at 2024-10-10 18:27:58
 */
@Component
public class LanguageUtil {
    private static final ThreadLocal<String> LANGUAGE_THREAD_LOCAL = new ThreadLocal<>();
    private Map<String, Map<String, String>> TRANSLATION_MAP;
    @Resource
    private ISysI18nRawService iSysI18nRawService;

    public static void setLanguage(String language) {
        LANGUAGE_THREAD_LOCAL.set(language);
    }

    public static String getLanguage() {
        return LANGUAGE_THREAD_LOCAL.get();
    }

    public static void cleanLanguage() {
        LANGUAGE_THREAD_LOCAL.remove();
    }

    @PostConstruct
    public void init() {
        setLanguage("zh_CN");
        TRANSLATION_MAP = iSysI18nRawService.getTranslation();
    }

    public String getTranslation(String rawContent, String languageCode) {
        if (TRANSLATION_MAP.containsKey(rawContent)) {
            Map<String, String> translationMap = TRANSLATION_MAP.get(rawContent);
            return translationMap.get(languageCode);
        }
        return rawContent;
    }
    public boolean containsTranslation(String rawContent) {
        return TRANSLATION_MAP.containsKey(rawContent);
    }
}
