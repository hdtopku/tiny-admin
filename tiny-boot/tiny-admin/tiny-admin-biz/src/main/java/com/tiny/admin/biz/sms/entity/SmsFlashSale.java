package com.tiny.admin.biz.sms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Flash Sale Table
 * </p>
 *
 * @author lxh
 * @since 2024-11-19
 */
@Getter
@Setter
@TableName("sms_flash_sale")
@Schema(name = "SmsFlashSale", description = "Flash Sale Table")
public class SmsFlashSale implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary Key ID")
    private String id;

    @Schema(description = "Activity Name")
    private String activityName;

    @Schema(description = "Remarks")
    private String remark;

    @Schema(description = "Sorting (the larger the number, the later it appears)")
    private Integer sort;

    @Schema(description = "Status (0: Disabled, 1: Enabled)")
    private Boolean enabled;

    @Schema(description = "Start Time")
    private LocalDateTime startTime;

    @Schema(description = "End Time")
    private LocalDateTime endTime;

    @Schema(description = "Creation Time")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "Update Time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private Set<String> goodsIds;
}
