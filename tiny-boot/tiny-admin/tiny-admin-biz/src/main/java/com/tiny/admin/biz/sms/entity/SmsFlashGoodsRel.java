package com.tiny.admin.biz.sms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author lxh
 * @since 2024-10-01
 */
@Getter
@Setter
@TableName("sms_flash_goods_rel")
@Schema(name = "SmsFlashGoodsRel", description = "用户角色关系表")
public class SmsFlashGoodsRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "秒杀ID")
    private String flashId;

    @Schema(description = "商品ID")
    private String goodsId;
}
