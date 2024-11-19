package com.tiny.admin.biz.pms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Brand table
 * </p>
 *
 * @author lxh
 * @since 2024-11-19
 */
@Getter
@Setter
@TableName("pms_brand")
@Schema(name = "PmsBrand", description = "Brand table")
public class PmsBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary key ID")
    private String id;

    @Schema(description = "Brand name")
    private String brandName;

    @Schema(description = "Brand description")
    private String brandDesc;

    @Schema(description = "Brand logo")
    private String logo;

    @Schema(description = "Link to")
    private String linkTo;

    @Schema(description = "Sort order (higher values appear later)")
    private Integer sort;

    @Schema(description = "Brand status (0: disabled, 1: enabled)")
    private Boolean enabled;

    @Schema(description = "Brand story")
    private String brandStory;

    @Schema(description = "Creation time")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "Update time")
    private LocalDateTime updateTime;
}
