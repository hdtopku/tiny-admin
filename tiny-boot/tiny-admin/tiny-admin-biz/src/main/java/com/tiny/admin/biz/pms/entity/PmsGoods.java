package com.tiny.admin.biz.pms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author lxh
 * @since 2024-09-19
 */
@Getter
@Setter
@TableName("pms_goods")
@Schema(name = "PmsGoods", description = "商品表")
public class PmsGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    private String id;

    @Schema(description = "商品名称")
    private String goodsName;

    @Schema(description = "商品介绍")
    private String goodsDesc;

    @Schema(description = "销量")
    private Integer sale;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "库存预警值")
    private Integer lowStock;

    @Schema(description = "市场价")
    private BigDecimal marketPrice;

    @Schema(description = "促销价")
    private BigDecimal promotionPrice;

    @Schema(description = "状态(0:未启用,1:启用)")
    private Boolean status;

    @Schema(description = "画册图片，连产品图片限制为5张，以逗号分割")
    private String albumPics;

    @Schema(description = "产品详情网页内容")
    private String detailHtml;

    @Schema(description = "品牌名称")
    private String brandName;

    @Schema(description = "排序(值越大越靠后)")
    private Integer sort;
}
