package com.tiny.admin.biz.sms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Flash Sale - Product Relationship Table
 * </p>
 *
 * @author lxh
 * @since 2024-11-19
 */
@Getter
@Setter
@TableName("sms_flash_goods_rel")
@Schema(name = "SmsFlashGoodsRel", description = "Flash Sale - Product Relationship Table")
public class SmsFlashGoodsRel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary Key ID")
    private String id;

    @Schema(description = "Flash Sale ID")
    private String flashId;

    @Schema(description = "Product ID")
    private String goodsId;
}
