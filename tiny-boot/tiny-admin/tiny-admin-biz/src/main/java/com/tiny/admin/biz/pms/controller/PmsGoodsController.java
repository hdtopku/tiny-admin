package com.tiny.admin.biz.pms.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.pms.dto.PmsGoodsDto;
import com.tiny.admin.biz.pms.entity.PmsGoods;
import com.tiny.admin.biz.pms.service.IPmsGoodsService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-09-19
 */
@RestController
@RequestMapping("/pms/goods")
public class PmsGoodsController {
    @Resource
    private IPmsGoodsService iPmsGoodsService;

    @PostMapping("/page")
    @Operation(summary = "分页查询品牌列表")
    public Result<IPage<PmsGoodsDto>> page(@RequestBody(required = false) BaseQueryParam param) {
        LambdaQueryWrapper<PmsGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(PmsGoods::getSort);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(PmsGoods::getGoodsName, param.getKeyword())
                    .or().like(PmsGoods::getGoodsDesc, param.getKeyword())
                    .or().like(PmsGoods::getBrandName, param.getKeyword())
                    .or().eq(PmsGoods::getId, param.getKeyword());
            param.setPageNum(1);
        } else {
            wrapper.eq(PmsGoods::getStatus, param.getStatus());
        }
        IPage<PmsGoods> iPage = iPmsGoodsService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        IPage<PmsGoodsDto> dtoPage = new Page<>();
        BeanUtil.copyProperties(iPage, dtoPage);
        dtoPage.setRecords(new ArrayList<>());
        iPage.getRecords().stream().forEach(item -> {
            PmsGoodsDto dto = BeanUtil.copyProperties(item, PmsGoodsDto.class);
            if (StringUtils.isNotBlank(item.getAlbumPics())) {
                dto.setAlbumList(Arrays.asList(item.getAlbumPics().split(",")));
            }
            dtoPage.getRecords().add(dto);
        });
        return Result.success(dtoPage);
    }

    @PostMapping("/saveOrUpdate")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveOrUpdate(@Valid @RequestBody PmsGoodsDto pmsGoodsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        PmsGoods pmsGoods = BeanUtil.copyProperties(pmsGoodsDto, PmsGoods.class);
        if (CollUtil.isNotEmpty(pmsGoodsDto.getAlbumList())) {
            pmsGoods.setAlbumPics(String.join(",", pmsGoodsDto.getAlbumList()));
        }
        iPmsGoodsService.saveOrUpdate(pmsGoods);
        return Result.success();
    }

    @PostMapping("/listByIds")
    public Result<ArrayList<PmsGoodsDto>> listByIds(@RequestBody Set<String> ids) {
        ArrayList<PmsGoodsDto> result = new ArrayList<>();
        if (CollUtil.isEmpty(ids)) return Result.success(result);
        List<PmsGoods> pmsGoods = iPmsGoodsService.listByIds(ids);
        if (!CollUtil.isEmpty(pmsGoods)) {
            pmsGoods.forEach(item -> {
                PmsGoodsDto dto = BeanUtil.copyProperties(item, PmsGoodsDto.class);
                if (StringUtils.isNotBlank(item.getAlbumPics())) {
                    dto.setAlbumList(Arrays.asList(item.getAlbumPics().split(",")));
                }
                result.add(dto);
            });
        }
        return Result.success(result);
    }
}
