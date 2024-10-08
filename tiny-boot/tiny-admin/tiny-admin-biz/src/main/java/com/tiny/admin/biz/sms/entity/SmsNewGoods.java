package com.tiny.admin.biz.sms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 品牌推荐表
 * </p>
 *
 * @author lxh
 * @since 2024-09-28
 */
@Getter
@Setter
@TableName("sms_new_goods")
@Schema(name = "SmsNewGoods", description = "品牌推荐表")
public class SmsNewGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "品牌ID")
    private String goodsId;

    @Schema(description = "排序(值越大越靠后)")
    private Integer sort;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "是否生效(0:未生效,1:已生效)")
    private Byte status;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
