package com.tiny.admin.biz.sms.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiny.admin.biz.pms.entity.PmsProduct;
import com.tiny.admin.biz.sms.dto.SmsNewGoodsDto;
import com.tiny.admin.biz.sms.entity.SmsNewGoods;
import com.tiny.admin.biz.sms.mapper.SmsNewGoodsMapper;
import com.tiny.admin.biz.sms.service.ISmsNewGoodsService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 新品推荐表 前端控制器
 *
 * @author lxh
 * @since 2024-09-28
 */
@RestController
@RequestMapping("/sms/newGoods")
public class SmsNewGoodsController {
  @Resource private ISmsNewGoodsService iSmsNewGoodsService;
  @Resource private SmsNewGoodsMapper smsNewGoodsMapper;

  @GetMapping("/allGoodsIds")
  @Operation(summary = "查询所有推荐新品")
  public Result<List<String>> listAll() {
    QueryWrapper<SmsNewGoods> wrapper = new QueryWrapper<>();
    wrapper.select("goods_id");
    List<SmsNewGoods> list = iSmsNewGoodsService.list(wrapper);
    List<String> res = Collections.emptyList();
    if (!CollUtil.isEmpty(list)) {
      res = list.stream().map(SmsNewGoods::getGoodsId).toList();
    }
    return Result.success(res);
  }

  @PostMapping("/page")
  @Operation(summary = "分页查询推荐新品列表")
  public Result<IPage<SmsNewGoodsDto>> page(@RequestBody(required = false) BaseQueryParam param) {
    MPJLambdaWrapper<SmsNewGoods> wrapper = new MPJLambdaWrapper<>();
    wrapper.orderByAsc(SmsNewGoods::getSort);
    wrapper
        .selectAll(SmsNewGoods.class)
        .select(
            PmsProduct::getProductName,
            PmsProduct::getAlbum,
            PmsProduct::getMarketPrice,
            PmsProduct::getSalePrice)
        .leftJoin(PmsProduct.class, PmsProduct::getId, SmsNewGoods::getGoodsId);
    if (StringUtils.isNotBlank(param.getKeyword())) {
      wrapper.like(SmsNewGoods::getRemark, param.getKeyword());
      wrapper.or().like(PmsProduct::getProductName, param.getKeyword());
    } else {
      wrapper.eq(SmsNewGoods::getEnabled, param.getEnabled());
    }
    IPage<SmsNewGoodsDto> iPage =
        smsNewGoodsMapper.selectJoinPage(
            new Page<>(param.getPageNum(), param.getPageSize()), SmsNewGoodsDto.class, wrapper);
    return Result.success(iPage);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除推荐新品")
  public Result<Boolean> deleteById(@PathVariable("id") String id) {
    iSmsNewGoodsService.removeById(id);
    return Result.success();
  }

  @PostMapping("/save")
  @Transactional(rollbackFor = Exception.class)
  public Result<Boolean> save(
      @Valid @RequestBody Set<String> goodsIds, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return Result.failure(bindingResult.getFieldError().getDefaultMessage());
    }
    iSmsNewGoodsService.remove(new QueryWrapper<>());
    if (CollUtil.isNotEmpty(goodsIds)) {
      List<SmsNewGoods> smsHotGoodsList =
          goodsIds.stream()
              .map(
                  goodsId -> {
                    SmsNewGoods entity = new SmsNewGoods();
                    entity.setGoodsId(goodsId);
                    return entity;
                  })
              .toList();
      iSmsNewGoodsService.saveBatch(smsHotGoodsList);
    }
    return Result.success();
  }

  @PostMapping("/update")
  @Transactional(rollbackFor = Exception.class)
  public Result<Boolean> update(
      @Valid @RequestBody SmsNewGoods entity, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return Result.failure(bindingResult.getFieldError().getDefaultMessage());
    }
    entity.setCreateTime(null);
    entity.setUpdateTime(null);
    iSmsNewGoodsService.updateById(entity);
    return Result.success();
  }
}
