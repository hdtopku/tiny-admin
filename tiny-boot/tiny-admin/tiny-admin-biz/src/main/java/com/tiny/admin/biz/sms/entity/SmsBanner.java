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
 * 轮播表
 * </p>
 *
 * @author lxh
 * @since 2024-09-23
 */
@Getter
@Setter
@TableName("sms_banner")
@Schema(name = "SmsBanner", description = "轮播表")
public class SmsBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "轮播卡片名称")
    private String bannerName;

    @Schema(description = "图片链接")
    private String picUrl;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "轮播位置(1:PC,2:APP)")
    private Integer platform;

    @Schema(description = "链接至")
    private String linkTo;

    @Schema(description = "排序(值越大越靠后)")
    private Integer sort;

    @Schema(description = "是否生效(0:未生效,1:已生效)")
    private Boolean status;

    @Schema(description = "点击次数")
    private Integer clickCount;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
