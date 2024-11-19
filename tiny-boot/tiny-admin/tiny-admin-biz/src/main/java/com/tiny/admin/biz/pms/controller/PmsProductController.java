package com.tiny.admin.biz.pms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tiny.admin.biz.pms.dto.PmsProductDto;
import com.tiny.admin.biz.pms.service.IPmsProductService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Set;

/**
 * <p>
 * Product Controller to manage product-related operations.
 * </p>
 *
 * @since 2024-11-16
 */
@RestController
@RequestMapping("/pms/product")
public class PmsProductController {

    @Resource
    private IPmsProductService pmsProductService;

    /**
     * Paginate and query the list of products based on query parameters.
     *
     * @param param The query parameters for pagination and filtering.
     * @return A paginated list of products.
     */
    @PostMapping("/page")
    @Operation(summary = "Paginated query of product list")
    public Result<IPage<PmsProductDto>> page(@RequestBody(required = false) BaseQueryParam param) {
        return Result.success(pmsProductService.pageList(param));
    }

    /**
     * Save or update a product based on the provided product DTO.
     *
     * @param pmsProductDto The product DTO to save or update.
     * @param bindingResult Validation results.
     * @return A result indicating success or failure.
     */
    @PostMapping("/saveOrUpdate")
    @Operation(summary = "Save or update product")
    public Result<Boolean> saveOrUpdate(@Valid @RequestBody PmsProductDto pmsProductDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        return pmsProductService.saveOrUpdateProduct(pmsProductDto);
    }

    /**
     * Retrieve a list of products by their IDs.
     *
     * @param ids A set of product IDs to retrieve.
     * @return A list of product DTOs.
     */
    @PostMapping("/listByIds")
    @Operation(summary = "Retrieve products by IDs")
    public Result<ArrayList<PmsProductDto>> listByIds(@RequestBody Set<String> ids) {
        return pmsProductService.getProductsByIds(ids);
    }
}
