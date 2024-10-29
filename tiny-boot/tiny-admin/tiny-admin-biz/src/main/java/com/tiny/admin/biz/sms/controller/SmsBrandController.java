package com.tiny.admin.biz.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.sms.entity.SmsBrand;
import com.tiny.admin.biz.sms.service.ISmsBrandService;
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
 * 品牌推荐表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-09-24
 */
@RestController
@RequestMapping("/sms/brand")
public class SmsBrandController {
    @Resource
    private ISmsBrandService iSmsBrandService;

    @PostMapping("/page")
    @Operation(summary = "分页查询品牌列表")
    public Result<IPage<SmsBrand>> page(@RequestBody(required = false) BaseQueryParam param) {
        LambdaQueryWrapper<SmsBrand> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SmsBrand::getSort);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SmsBrand::getRemark, param.getKeyword());
            param.setPageNum(1);
        } else {
            wrapper.eq(SmsBrand::getStatus, param.getStatus());
        }
        IPage<SmsBrand> iPage = iSmsBrandService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        return Result.success(iPage);
    }


    @PostMapping("/saveOrUpdate")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveOrUpdate(@Valid @RequestBody SmsBrand smsBrand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        iSmsBrandService.saveOrUpdate(smsBrand);
        return Result.success();
    }
}
