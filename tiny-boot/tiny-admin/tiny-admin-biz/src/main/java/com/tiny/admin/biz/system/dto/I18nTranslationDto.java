package com.tiny.admin.biz.system.dto;

import com.tiny.admin.biz.system.entity.SysI18nTranslation;
import lombok.Data;

import java.util.List;

/**
 * Created by lxh at 2024-10-11 20:19:49
 */
@Data
public class I18nTranslationDto {
    private String id;
    private String rawContent;
    private List<SysI18nTranslation> translationList;
}
