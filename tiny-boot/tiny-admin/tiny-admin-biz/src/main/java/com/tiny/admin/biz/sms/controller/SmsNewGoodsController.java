package com.tiny.admin.biz.sms.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiny.admin.biz.pms.entity.PmsGoods;
import com.tiny.admin.biz.sms.dto.SmsNewGoodsDto;
import com.tiny.admin.biz.sms.entity.SmsNewGoods;
import com.tiny.admin.biz.sms.mapper.SmsNewGoodsMapper;
import com.tiny.admin.biz.sms.service.ISmsNewGoodsService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 新品推荐表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-09-28
 */
@RestController
@RequestMapping("/sms/newGoods")
public class SmsNewGoodsController {
    @Resource
    private ISmsNewGoodsService iSmsNewGoodsService;
    @Resource
    private SmsNewGoodsMapper smsNewGoodsMapper;

    @PostMapping("/page")
    @Operation(summary = "分页查询推荐新品列表")
    public Result<IPage<SmsNewGoodsDto>> page(@RequestBody(required = false) BaseQueryParam param) {
        MPJLambdaWrapper<SmsNewGoods> wrapper = new MPJLambdaWrapper<>();
        wrapper.orderByAsc(SmsNewGoods::getSort);
        wrapper.selectAll(SmsNewGoods.class)
                .select(PmsGoods::getGoodsName, PmsGoods::getAlbumPics, PmsGoods::getMarketPrice, PmsGoods::getPromotionPrice)
                .leftJoin(PmsGoods.class, PmsGoods::getId, SmsNewGoods::getGoodsId);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SmsNewGoods::getRemark, param.getKeyword());
            wrapper.or().like(PmsGoods::getGoodsName, param.getKeyword());
        } else {
            wrapper.eq(SmsNewGoods::getStatus, param.getStatus());
        }
        IPage<SmsNewGoodsDto> iPage = smsNewGoodsMapper.selectJoinPage(new Page<>(param.getPageNum(), param.getPageSize()), SmsNewGoodsDto.class, wrapper);
        return Result.success(iPage);
    }

    @PostMapping("/saveOrUpdate")
    @Operation(summary = "保存或修改推荐新品")
    public Result<Boolean> saveOrUpdate(@Valid @RequestBody SmsNewGoods entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        entity.setCreateTime(null);
        entity.setUpdateTime(null);
        iSmsNewGoodsService.saveOrUpdate(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除推荐新品")
    public Result<Boolean> deleteById(@PathVariable("id") String id) {
        iSmsNewGoodsService.removeById(id);
        return Result.success();
    }

    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> save(@Valid @RequestBody List<SmsNewGoods> smsNewGoodsList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        QueryWrapper<SmsNewGoods> queryWrapper = new QueryWrapper<>();
        Set<String> goodsIds = smsNewGoodsList.stream().map(SmsNewGoods::getGoodsId).collect(Collectors.toSet());
        queryWrapper.in("goods_id", goodsIds);
        List<SmsNewGoods> oldList = iSmsNewGoodsService.list(queryWrapper);
        if (CollUtil.isNotEmpty(oldList)) {
            Set<String> ids = oldList.stream().map(SmsNewGoods::getId).collect(Collectors.toSet());
            iSmsNewGoodsService.removeByIds(ids);
        }
        iSmsNewGoodsService.saveBatch(smsNewGoodsList);
        return Result.success();
    }

    @PostMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> update(@Valid @RequestBody SmsNewGoods smsNewGoods, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        iSmsNewGoodsService.updateById(smsNewGoods);
        return Result.success();
    }
}
