package com.tiny.admin.biz.sms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>
 * 秒杀表
 * </p>
 *
 * @author lxh
 * @since 2024-09-30
 */
@Getter
@Setter
@TableName("sms_flash_sale")
@Schema(name = "SmsFlashSale", description = "秒杀表")
public class SmsFlashSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "活动名称")
    private String activityName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "是否生效(0:未生效,1:已生效)")
    private Boolean status;

    @Schema(description = "排序(数字越大越靠后)")
    private Integer sort;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private Set<String> goodsIds;
}
