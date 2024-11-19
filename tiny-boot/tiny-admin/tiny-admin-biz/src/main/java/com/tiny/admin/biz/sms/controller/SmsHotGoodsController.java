package com.tiny.admin.biz.sms.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiny.admin.biz.pms.entity.PmsProduct;
import com.tiny.admin.biz.sms.dto.SmsNewGoodsDto;
import com.tiny.admin.biz.sms.entity.SmsHotGoods;
import com.tiny.admin.biz.sms.entity.SmsNewGoods;
import com.tiny.admin.biz.sms.mapper.SmsHotGoodsMapper;
import com.tiny.admin.biz.sms.service.ISmsHotGoodsService;
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
 * 热销商品推荐表 前端控制器
 *
 * @author lxh
 * @since 2024-09-30
 */
@RestController
@RequestMapping("/sms/hotGoods")
public class SmsHotGoodsController {
  @Resource private ISmsHotGoodsService iSmsHotGoodsService;
  @Resource private SmsHotGoodsMapper smsHotGoodsMapper;

  @GetMapping("/allGoodsIds")
  @Operation(summary = "查询所有推荐新品")
  public Result<List<String>> listAll() {
    QueryWrapper<SmsHotGoods> wrapper = new QueryWrapper<>();
    wrapper.select("goods_id");
    List<SmsHotGoods> list = iSmsHotGoodsService.list(wrapper);
    List<String> res = Collections.emptyList();
    if (!CollUtil.isEmpty(list)) {
      res = list.stream().map(SmsHotGoods::getGoodsId).toList();
    }
    return Result.success(res);
  }

  @PostMapping("/page")
  @Operation(summary = "分页查询热销商品列表")
  public Result<IPage<SmsNewGoodsDto>> page(@RequestBody(required = false) BaseQueryParam param) {
    MPJLambdaWrapper<SmsHotGoods> wrapper = new MPJLambdaWrapper<>();
    wrapper.orderByAsc(SmsHotGoods::getSort);
    wrapper
        .selectAll(SmsHotGoods.class)
        .select(
            PmsProduct::getProductName,
            PmsProduct::getAlbum,
            PmsProduct::getMarketPrice,
            PmsProduct::getSalePrice)
        .leftJoin(PmsProduct.class, PmsProduct::getId, SmsHotGoods::getGoodsId);
    if (StringUtils.isNotBlank(param.getKeyword())) {
      wrapper.like(SmsHotGoods::getRemark, param.getKeyword());
      wrapper.or().like(PmsProduct::getProductName, param.getKeyword());
    } else {
      wrapper.eq(SmsNewGoods::getEnabled, param.getEnabled());
    }
    IPage<SmsNewGoodsDto> iPage =
        smsHotGoodsMapper.selectJoinPage(
            new Page<>(param.getPageNum(), param.getPageSize()), SmsNewGoodsDto.class, wrapper);
    return Result.success(iPage);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除热销商品")
  public Result<Boolean> deleteById(@PathVariable("id") String id) {
    iSmsHotGoodsService.removeById(id);
    return Result.success();
  }

  @PostMapping("/save")
  @Transactional(rollbackFor = Exception.class)
  public Result<Boolean> save(@Valid @RequestBody Set<String> goodsIds, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return Result.failure(bindingResult.getFieldError().getDefaultMessage());
    }
    iSmsHotGoodsService.remove(new QueryWrapper<>());
    if (CollUtil.isNotEmpty(goodsIds)) {
      List<SmsHotGoods> smsHotGoodsList =
              goodsIds.stream()
                      .map(
                              goodsId -> {
                                SmsHotGoods entity = new SmsHotGoods();
                                entity.setGoodsId(goodsId);
                                return entity;
                              })
                      .toList();
      iSmsHotGoodsService.saveBatch(smsHotGoodsList);
    }
    return Result.success();
  }

  @PostMapping("/update")
  @Transactional(rollbackFor = Exception.class)
  public Result<Boolean> update(
      @Valid @RequestBody SmsHotGoods entity, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return Result.failure(bindingResult.getFieldError().getDefaultMessage());
    }
    entity.setCreateTime(null);
    entity.setUpdateTime(null);
    iSmsHotGoodsService.updateById(entity);
    return Result.success();
  }
}
