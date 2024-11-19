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
 * Hot Sale Product Recommendation Table
 * </p>
 *
 * @author lxh
 * @since 2024-11-19
 */
@Getter
@Setter
@TableName("sms_hot_goods")
@Schema(name = "SmsHotGoods", description = "Hot Sale Product Recommendation Table")
public class SmsHotGoods implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary Key ID")
    private String id;

    @Schema(description = "Goods ID")
    private String goodsId;

    @Schema(description = "Sorting (the larger the number, the later it appears)")
    private Integer sort;

    @Schema(description = "Remarks")
    private String remark;

    @Schema(description = "Status (0: Disabled, 1: Enabled)")
    private Boolean enabled;

    @Schema(description = "Creation Time")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "Update Time")
    private LocalDateTime updateTime;
}
