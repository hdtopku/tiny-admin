package com.tiny.admin.biz.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.sms.entity.SmsFlashGoodsRel;
import com.tiny.admin.biz.sms.entity.SmsFlashSale;
import com.tiny.admin.biz.sms.service.ISmsFlashGoodsRelService;
import com.tiny.admin.biz.sms.service.ISmsFlashSaleService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 秒杀表 前端控制器
 *
 * @author lxh
 * @since 2024-09-30
 */
@RestController
@RequestMapping("/sms/flashSale")
public class SmsFlashSaleController {
  @Resource private ISmsFlashSaleService iSmsFlashSaleService;
  @Resource private ISmsFlashGoodsRelService iSmsFlashGoodsRelService;

  @PostMapping("/saveOrUpdate")
  @Transactional(rollbackFor = Exception.class)
  public Result<Boolean> saveOrUpdate(
      @Valid @RequestBody SmsFlashSale smsFlashSale, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return Result.failure(bindingResult.getFieldError().getDefaultMessage());
    }
    iSmsFlashSaleService.saveOrUpdate(smsFlashSale);
    return Result.success();
  }

  @PostMapping("/page")
  @Operation(summary = "分页查询秒杀表")
  public Result<IPage<SmsFlashSale>> page(@RequestBody(required = false) BaseQueryParam param) {
    LambdaQueryWrapper<SmsFlashSale> wrapper = new LambdaQueryWrapper<>();
    wrapper.orderByAsc(SmsFlashSale::getSort);
    if (StringUtils.isNotBlank(param.getKeyword())) {
      wrapper
          .like(SmsFlashSale::getRemark, param.getKeyword())
          .or()
          .like(SmsFlashSale::getActivityName, param.getKeyword());
      param.setPageNum(1);
    } else {
      wrapper.eq(SmsFlashSale::getStatus, param.getStatus());
    }
    IPage<SmsFlashSale> iPage =
        iSmsFlashSaleService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
    HashMap<String, Set<String>> map = new HashMap<>();
    if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
      iPage.getRecords().forEach(smsFlashSale -> map.put(smsFlashSale.getId(), new HashSet<>()));
      List<SmsFlashGoodsRel> smsFlashGoodsRels =
          iSmsFlashGoodsRelService.list(
              new LambdaQueryWrapper<SmsFlashGoodsRel>()
                  .in(SmsFlashGoodsRel::getFlashId, map.keySet()));
      if (CollectionUtils.isNotEmpty(smsFlashGoodsRels)) {
        smsFlashGoodsRels.forEach(
            smsFlashGoodsRel ->
                map.get(smsFlashGoodsRel.getFlashId()).add(smsFlashGoodsRel.getGoodsId()));
        iPage
            .getRecords()
            .forEach(smsFlashSale -> smsFlashSale.setGoodsIds(map.get(smsFlashSale.getId())));
      }
    }
    return Result.success(iPage);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除秒杀活动")
  public Result<Boolean> delete(@PathVariable String id) {
    iSmsFlashSaleService.removeById(id);
    return Result.success();
  }
}
