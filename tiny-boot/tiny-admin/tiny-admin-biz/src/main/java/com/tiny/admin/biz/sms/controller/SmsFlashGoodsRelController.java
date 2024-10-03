package com.tiny.admin.biz.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tiny.admin.biz.sms.entity.SmsFlashGoodsRel;
import com.tiny.admin.biz.sms.service.ISmsFlashGoodsRelService;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户角色关系表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-10-01
 */
@RestController
@RequestMapping("/sms/flashGoodsRel")
public class SmsFlashGoodsRelController {
    @Resource
    private ISmsFlashGoodsRelService iSmsFlashGoodsRelService;

    @PostMapping("/assignGoodsIds/{flashId}")
    @Transactional(rollbackFor = Exception.class)
    @Operation(summary = "更新闪购商品关系")
    public Result<Boolean> addFlashGoodsRel(@PathVariable("flashId") String flashId, @RequestBody Set<String> goodsIds) {
        if (CollectionUtils.isEmpty(goodsIds)) return Result.success(true);
        // 删除原有关系
        List<SmsFlashGoodsRel> oldRel = iSmsFlashGoodsRelService.list(new LambdaQueryWrapper<>(SmsFlashGoodsRel.class).in(SmsFlashGoodsRel::getGoodsId, goodsIds));
        if (!CollectionUtils.isEmpty(oldRel)) {
            iSmsFlashGoodsRelService.removeByIds(oldRel.stream().map(SmsFlashGoodsRel::getId).toList());
        }
        // 新增关系
        List<SmsFlashGoodsRel> flashGoodsRelList = new ArrayList<>();
        for (String goodsId : goodsIds) {
            SmsFlashGoodsRel flashGoodsRel = new SmsFlashGoodsRel();
            flashGoodsRel.setFlashId(flashId);
            flashGoodsRel.setGoodsId(goodsId);
            flashGoodsRelList.add(flashGoodsRel);
        }
        iSmsFlashGoodsRelService.saveOrUpdateBatch(flashGoodsRelList);
        return Result.success(true);
    }

    @PostMapping("/removeGoodsIds/{flashId}")
    @Transactional(rollbackFor = Exception.class)
    @Operation(summary = "更新闪购商品关系")
    public Result<Boolean> removeFlashGoodsRel(@PathVariable("flashId") String flashId, @RequestBody Set<String> goodsIds) {
        LambdaQueryWrapper<SmsFlashGoodsRel> query = new LambdaQueryWrapper<>(SmsFlashGoodsRel.class).eq(SmsFlashGoodsRel::getFlashId, flashId);
        if (!CollectionUtils.isEmpty(goodsIds)) {
            query.and(i -> i.in(SmsFlashGoodsRel::getGoodsId, goodsIds));
        }
        List<SmsFlashGoodsRel> list = iSmsFlashGoodsRelService.list(query);
        if (!CollectionUtils.isEmpty(list)) {
            iSmsFlashGoodsRelService.removeByIds(list.stream().map(SmsFlashGoodsRel::getId).toList());
        }
        return Result.success(true);
    }


}
