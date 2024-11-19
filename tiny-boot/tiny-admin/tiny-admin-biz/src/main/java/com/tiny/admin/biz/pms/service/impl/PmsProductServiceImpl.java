package com.tiny.admin.biz.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiny.admin.biz.pms.dto.PmsProductDto;
import com.tiny.admin.biz.pms.entity.PmsProduct;
import com.tiny.admin.biz.pms.mapper.PmsProductMapper;
import com.tiny.admin.biz.pms.service.IPmsProductService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements IPmsProductService {
    
    /**
     * Paginate the list of products based on the given parameters.
     *
     * @param param The query parameters for pagination and filtering.
     * @return A page of products as DTOs.
     */
    @Override
    public IPage<PmsProductDto> pageList(BaseQueryParam param) {
        LambdaQueryWrapper<PmsProduct> wrapper = buildQueryWrapper(param);
        IPage<PmsProduct> iPage = this.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);

        return convertToDtoPage(iPage);
    }

    /**
     * Build query wrapper with filters based on the given parameters.
     *
     * @param param The query parameters.
     * @return The query wrapper for filtering.
     */
    private LambdaQueryWrapper<PmsProduct> buildQueryWrapper(BaseQueryParam param) {
        LambdaQueryWrapper<PmsProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(PmsProduct::getSort);

        if (StringUtils.hasText(param.getKeyword())) {
            wrapper.like(PmsProduct::getProductName, param.getKeyword())
                    .or().like(PmsProduct::getProductDesc, param.getKeyword())
                    .or().eq(PmsProduct::getId, param.getKeyword());
            param.setPageNum(1); // Reset page number if a keyword is provided
        } else {
            wrapper.eq(PmsProduct::getEnabled, param.getEnabled());
        }

        return wrapper;
    }

    /**
     * Convert the entity page to DTO page and handle album pictures.
     *
     * @param iPage The original entity page.
     * @return The DTO page with the records mapped.
     */
    private IPage<PmsProductDto> convertToDtoPage(IPage<PmsProduct> iPage) {
        IPage<PmsProductDto> dtoPage = new Page<>();
        BeanUtil.copyProperties(iPage, dtoPage);
        List<PmsProductDto> dtoList = iPage.getRecords().stream()
                .map(this::convertToDto).toList();
        dtoPage.setRecords(dtoList);

        return dtoPage;
    }

    /**
     * Convert a single PmsProduct entity to PmsProductDto.
     *
     * @param item The entity to convert.
     * @return The corresponding DTO.
     */
    private PmsProductDto convertToDto(PmsProduct item) {
        PmsProductDto dto = BeanUtil.copyProperties(item, PmsProductDto.class);
        if (StringUtils.hasText(item.getAlbum())) {
            dto.setAlbum(Arrays.asList(item.getAlbum().split(",")));
        }
        return dto;
    }

    /**
     * Save or update the product based on the provided DTO.
     *
     * @param pmsProductDto The product DTO.
     * @return A result indicating success or failure.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveOrUpdateProduct(PmsProductDto pmsProductDto) {
        PmsProduct pmsProduct = BeanUtil.copyProperties(pmsProductDto, PmsProduct.class);

        // Handle album pictures
        if (CollUtil.isNotEmpty(pmsProductDto.getAlbum())) {
            pmsProduct.setAlbum(String.join(",", pmsProductDto.getAlbum()));
        }

        boolean isSuccess = super.saveOrUpdate(pmsProduct); // Using super to avoid conflict with MyBatis Plus method
        return isSuccess ? Result.success() : Result.failure("Failed to save or update product.");
    }

    /**
     * Retrieve a list of products by their IDs.
     *
     * @param ids The set of product IDs to retrieve.
     * @return A list of product DTOs.
     */
    @Override
    public Result<ArrayList<PmsProductDto>> getProductsByIds(Set<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Result.success(new ArrayList<>());
        }

        List<PmsProduct> pmsProductList = this.listByIds(ids);
        List<PmsProductDto> resultList = pmsProductList.stream()
                .map(this::convertToDto)
                .toList();

        return Result.success(new ArrayList<>(resultList));
    }
}
