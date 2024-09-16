package com.tiny.admin.biz.pms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author lxh
 * @since 2024-09-14
 */
@Getter
@Setter
@TableName("pms_brand")
@Schema(name = "PmsBrand", description = "品牌表")
public class PmsBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    private String id;

    @Schema(description = "品牌名称")
    @NotNull(message = "品牌名称不能为空！")
    @Length(min = 1, max = 50, message = "品牌名称长度不能超过50")
    private String brandName;

    @Schema(description = "品牌介绍")
    @NotNull(message = "品牌介绍不能为空")
    @Length(min = 1, max = 200, message = "品牌介绍长度不能超过200")
    private String brandDesc;

    @Schema(description = "品牌logo")
    private String logo;

    @Schema(description = "排序(数值越大越靠后)")
    private Integer sort;

    @Schema(description = "状态(0:未启用,1:启用)")
    private Boolean status;

    @Schema(description = "品牌故事")
    private String brandStory;
}
