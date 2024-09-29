package com.tiny.admin.biz.sms.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.sms.entity.SmsNewGoods;
import com.tiny.admin.biz.sms.service.ISmsNewGoodsService;
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
 * 品牌推荐表 前端控制器
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
    @PostMapping("/page")
    @Operation(summary = "分页查询品牌列表")
    public Result<IPage<SmsNewGoods>> page(@RequestBody(required = false) BaseQueryParam param) {
        LambdaQueryWrapper<SmsNewGoods> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SmsNewGoods::getSort);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SmsNewGoods::getRemark, param.getKeyword());
            wrapper.or().eq(SmsNewGoods::getGoodsId, param.getKeyword());
        }
        IPage<SmsNewGoods> iPage = iSmsNewGoodsService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        return Result.success(iPage);
    }

    @PostMapping("/saveOrUpdate")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveOrUpdate(@Valid @RequestBody List<SmsNewGoods> smsNewGoodsList, BindingResult bindingResult) {
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
}
