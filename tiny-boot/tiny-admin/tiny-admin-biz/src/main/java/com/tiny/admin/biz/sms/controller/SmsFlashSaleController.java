package com.tiny.admin.biz.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.sms.entity.SmsFlashSale;
import com.tiny.admin.biz.sms.service.ISmsFlashSaleService;
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

/**
 * <p>
 * 秒杀表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-09-30
 */
@RestController
@RequestMapping("/sms/flashSale")
public class SmsFlashSaleController {
    @Resource
    private ISmsFlashSaleService iSmsFlashSaleService;

    @PostMapping("/saveOrUpdate")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveOrUpdate(@Valid @RequestBody SmsFlashSale smsFlashSale, BindingResult bindingResult) {
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
            wrapper.like(SmsFlashSale::getRemark, param.getKeyword())
                    .or().like(SmsFlashSale::getActivityName, param.getKeyword());
        }
        IPage<SmsFlashSale> iPage = iSmsFlashSaleService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        return Result.success(iPage);
    }
}