package com.tiny.admin.biz.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * i18n原始内容表
 * </p>
 *
 * @author lxh
 * @since 2024-10-10
 */
@Getter
@Setter
@TableName("sys_i18n_raw")
@Schema(name = "SysI18nRaw", description = "i18n原始内容表")
public class SysI18nRaw implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "原始数据表")
    private String rawContent;

    @Schema(description = "原始数据的md5")
    private String rawContentMd5;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
