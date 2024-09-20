package com.tiny.admin.biz.pms.controller;

import cn.hutool.core.bean.BeanUtil;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

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
                    .or().like(PmsGoods::getBrandName, param.getKeyword());
        }
        IPage<PmsGoods> iPage = iPmsGoodsService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        IPage<PmsGoodsDto> dtoPage = new Page<>();
        BeanUtil.copyProperties(iPage, dtoPage);
        dtoPage.setRecords(new ArrayList<>());
        iPage.getRecords().stream().forEach(item -> {
            PmsGoodsDto dto = BeanUtil.copyProperties(item, PmsGoodsDto.class);
            if(StringUtils.isNotBlank(item.getAlbumPics())) {
                dto.setAlbumList(Arrays.asList(item.getAlbumPics().split(",")));
            }
            dtoPage.getRecords().add(dto);
        });
        return Result.success(dtoPage);
    }
}
