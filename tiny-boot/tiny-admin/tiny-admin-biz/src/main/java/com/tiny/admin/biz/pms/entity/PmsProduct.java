package com.tiny.admin.biz.pms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Product Table
 * </p>
 *
 * @author lxh
 * @since 2024-11-16
 */
@Getter
@Setter
@TableName("pms_product")
@Schema(name = "PmsProduct", description = "Product Table")
public class PmsProduct implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary Key ID")
    private String id;

    @Schema(description = "Product Name")
    private String productName;

    @Schema(description = "Product Description")
    private String productDesc;

    @Schema(description = "Product Image Album (up to 10 images, separated by commas)")
    private String album;

    @Schema(description = "Sorting Order (Higher values rank later)")
    private Integer sort;

    @Schema(description = "Sales Volume")
    private Integer sale;

    @Schema(description = "Stock Quantity")
    private Integer stock;

    @Schema(description = "Stock Alert Threshold")
    private Integer lowStock;

    @Schema(description = "Market Price")
    private BigDecimal marketPrice;

    @Schema(description = "Sale Price")
    private BigDecimal salePrice;

    @Schema(description = "Enabled Status (0: Disabled, 1: Enabled)")
    private Boolean enabled;

    @Schema(description = "Product Detail HTML Content")
    private String detailHtml;

    @Schema(description = "Brand ID")
    private String brandId;

    @Schema(description = "Creation Time")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "Last Update Time")
    private LocalDateTime updateTime;
}
