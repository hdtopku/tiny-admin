package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * i18n翻译表
 * </p>
 *
 * @author lxh
 * @since 2024-10-10
 */
@Getter
@Setter
@TableName("sys_i18n_translation")
@Schema(name = "SysI18nTranslation", description = "i18n翻译表")
public class SysI18nTranslation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "i18n原始内容表ID")
    private String rawId;

    @Schema(description = "语言代码(中文:CN,英文:EN)")
    private String languageCode;

    @Schema(description = "翻译后的内容")
    private String translatedContent;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
