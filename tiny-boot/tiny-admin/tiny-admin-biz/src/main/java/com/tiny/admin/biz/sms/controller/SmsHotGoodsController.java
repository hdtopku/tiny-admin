package com.tiny.admin.biz.sms.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.sms.entity.SmsHotGoods;
import com.tiny.admin.biz.sms.service.ISmsHotGoodsService;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 热销商品推荐表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-09-30
 */
@RestController
@RequestMapping("/sms/hotGoods")
public class SmsHotGoodsController {
    @Resource
    private ISmsHotGoodsService iSmsHotGoodsService;
    @PostMapping("/page")
    @Operation(summary = "分页查询品牌列表")
    public Result<IPage<SmsHotGoods>> page(@RequestBody(required = false) BaseQueryParam param) {
        LambdaQueryWrapper<SmsHotGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SmsHotGoods::getSort);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SmsHotGoods::getRemark, param.getKeyword());
            wrapper.or().eq(SmsHotGoods::getGoodsId, param.getKeyword());
            param.setPageNum(1);
        } else {
            wrapper.eq(SmsHotGoods::getStatus, param.getStatus());
        }
        IPage<SmsHotGoods> iPage = iSmsHotGoodsService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        return Result.success(iPage);
    }

    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> save(@Valid @RequestBody List<SmsHotGoods> smsHotGoodsList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        QueryWrapper<SmsHotGoods> queryWrapper = new QueryWrapper<>();
        Set<String> goodsIds = smsHotGoodsList.stream().map(SmsHotGoods::getGoodsId).collect(Collectors.toSet());
        queryWrapper.in("goods_id", goodsIds);
        List<SmsHotGoods> oldList = iSmsHotGoodsService.list(queryWrapper);
        if (CollUtil.isNotEmpty(oldList)) {
            Set<String> ids = oldList.stream().map(SmsHotGoods::getId).collect(Collectors.toSet());
            iSmsHotGoodsService.removeByIds(ids);
        }
        iSmsHotGoodsService.saveBatch(smsHotGoodsList);
        return Result.success();
    }
    @PostMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> update(@Valid @RequestBody SmsHotGoods smsHotGoods, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        iSmsHotGoodsService.updateById(smsHotGoods);
        return Result.success();
    }
}
