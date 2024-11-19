package com.tiny.admin.biz.sms.entity;

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
 * Brand Recommendation Table
 * </p>
 *
 * @author lxh
 * @since 2024-11-19
 */
@Getter
@Setter
@TableName("sms_brand")
@Schema(name = "SmsBrand", description = "Brand Recommendation Table")
public class SmsBrand implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary Key ID")
    private String id;

    @Schema(description = "Brand ID")
    private String brandId;

    @Schema(description = "Sort Order (Higher value appears later)")
    private Integer sort;

    @Schema(description = "Brand Name")
    private String remark;

    @Schema(description = "Status (0:Disabled, 1:Enabled)")
    private Boolean enabled;

    @Schema(description = "Creation Time")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "Last Update Time")
    private LocalDateTime updateTime;
}
