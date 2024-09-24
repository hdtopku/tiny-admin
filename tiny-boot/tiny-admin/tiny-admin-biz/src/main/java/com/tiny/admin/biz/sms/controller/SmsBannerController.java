package com.tiny.admin.biz.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.sms.entity.SmsBanner;
import com.tiny.admin.biz.sms.service.ISmsBannerService;
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
 * 轮播表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-09-23
 */
@RestController
@RequestMapping("/sms/banner")
public class SmsBannerController {
    @Resource
    private ISmsBannerService iSmsBannerService;
    @PostMapping("/page")
    @Operation(summary = "分页查询轮播卡片列表")
    public Result<IPage<SmsBanner>> page(@RequestBody(required = false) BaseQueryParam param) {
        LambdaQueryWrapper<SmsBanner> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SmsBanner::getSort);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SmsBanner::getBannerName, param.getKeyword())
                    .or().like(SmsBanner::getRemark, param.getKeyword());
        }
        IPage<SmsBanner> iPage = iSmsBannerService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        return Result.success(iPage);
    }


    @PostMapping("/saveOrUpdate")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveOrUpdate(@Valid @RequestBody SmsBanner smsBanner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        iSmsBannerService.saveOrUpdate(smsBanner);
        return Result.success();
    }
}
