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
 * Banner Table
 * </p>
 *
 * @author lxh
 * @since 2024-11-19
 */
@Getter
@Setter
@TableName("sms_banner")
@Schema(name = "SmsBanner", description = "Banner Table")
public class SmsBanner implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary Key ID")
    private String id;

    @Schema(description = "Banner Card Name")
    private String bannerName;

    @Schema(description = "Image URL")
    private String picUrl;

    @Schema(description = "Banner Platform (1:PC, 2:APP)")
    private Integer platform;

    @Schema(description = "Link To")
    private String linkTo;

    @Schema(description = "Remark")
    private String remark;

    @Schema(description = "Sort Order (Higher value appears later)")
    private Integer sort;

    @Schema(description = "Status (0:Disabled, 1:Enabled)")
    private Boolean enabled;

    @Schema(description = "Click Count")
    private Integer clickCount;

    @Schema(description = "Creation Time")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "Last Update Time")
    private LocalDateTime updateTime;
}
