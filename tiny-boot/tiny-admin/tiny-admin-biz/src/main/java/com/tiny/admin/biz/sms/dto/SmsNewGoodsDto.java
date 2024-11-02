package com.tiny.admin.biz.sms.dto;

import com.tiny.admin.biz.sms.entity.SmsNewGoods;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/** Created by lxh at 2024-11-02 11:43:40 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SmsNewGoodsDto extends SmsNewGoods {
    private String goodsName;
    private String albumPics;
    private BigDecimal marketPrice;
    private BigDecimal promotionPrice;
}
