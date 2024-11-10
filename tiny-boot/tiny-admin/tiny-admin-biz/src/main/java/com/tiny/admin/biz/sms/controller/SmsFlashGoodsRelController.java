package com.tiny.admin.biz.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tiny.admin.biz.sms.entity.SmsFlashGoodsRel;
import com.tiny.admin.biz.sms.service.ISmsFlashGoodsRelService;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 用户角色关系表 前端控制器
 *
 * @author lxh
 * @since 2024-10-01
 */
@RestController
@RequestMapping("/sms/flashGoodsRel")
public class SmsFlashGoodsRelController {
  @Resource private ISmsFlashGoodsRelService iSmsFlashGoodsRelService;

  @GetMapping("/getGoodsIds/{id}")
  @Operation(summary = "获取闪购商品id")
  public Result<List<String>> getFlashGoodsIds(@PathVariable("id") String flashId) {
    List<SmsFlashGoodsRel> list =
        iSmsFlashGoodsRelService.list(
            new LambdaQueryWrapper<>(SmsFlashGoodsRel.class)
                .eq(SmsFlashGoodsRel::getFlashId, flashId));
    if (CollectionUtils.isEmpty(list)) return Result.success(new ArrayList<>());
    List<String> goodsIds = list.stream().map(SmsFlashGoodsRel::getGoodsId).toList();
    return Result.success(goodsIds);
  }

  @PostMapping("/assignGoodsIds/{flashId}")
  @Transactional(rollbackFor = Exception.class)
  @Operation(summary = "更新闪购商品关系")
  public Result<Boolean> addFlashGoodsRel(
      @PathVariable("flashId") String flashId, @RequestBody Set<String> goodsIds) {
    SmsFlashGoodsRel oldFlashGoodsRel = new SmsFlashGoodsRel();
    oldFlashGoodsRel.setFlashId(flashId);
    iSmsFlashGoodsRelService.remove(new LambdaQueryWrapper<>(oldFlashGoodsRel));
    if (!CollectionUtils.isEmpty(goodsIds)) {
      // 新增关系
      List<SmsFlashGoodsRel> flashGoodsRelList = new ArrayList<>();
      for (String goodsId : goodsIds) {
        SmsFlashGoodsRel flashGoodsRel = new SmsFlashGoodsRel();
        flashGoodsRel.setFlashId(flashId);
        flashGoodsRel.setGoodsId(goodsId);
        flashGoodsRelList.add(flashGoodsRel);
      }
      iSmsFlashGoodsRelService.saveOrUpdateBatch(flashGoodsRelList);
    }
    return Result.success(true);
  }

}
