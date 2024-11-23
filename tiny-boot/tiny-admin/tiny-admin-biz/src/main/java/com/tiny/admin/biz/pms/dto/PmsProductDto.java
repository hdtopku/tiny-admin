package com.tiny.admin.biz.pms.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Product table
 * </p>
 *
 * @author lxh
 * @since 2024-09-19
 */
@Getter
@Setter
@TableName("pms_goods")
@Schema(name = "PmsProduct", description = "Product table")
public class PmsProductDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Primary key ID")
    private String id;

    @Schema(description = "Product name")
    private String productName;

    @Schema(description = "Product description")
    private String productDesc;

    @Schema(description = "Sales volume")
    private Integer sale;

    @Schema(description = "Stock quantity")
    private Integer stock;

    @Schema(description = "Low stock warning value")
    private Integer lowStock;

    @Schema(description = "Market price")
    private BigDecimal marketPrice;

    @Schema(description = "Sale price")
    private BigDecimal salePrice;

    @Schema(description = "Status (0: Not enabled, 1: Enabled)")
    private Boolean enabled;

    @Schema(description = "Album images, including product images, limited to 10 images, separated by commas")
    private List<String> album;

    @Schema(description = "Product detail web content")
    private String detailHtml;

    @Schema(description = "Brand name")
    private String brandId;

    @Schema(description = "Sort order (the larger the value, the later it appears)")
    private Integer sort;
}
