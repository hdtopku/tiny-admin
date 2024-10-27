package com.tiny.admin.biz.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.pms.entity.PmsBrand;
import com.tiny.admin.biz.pms.service.IPmsBrandService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-09-14
 */
@RestController
@RequestMapping("/pms/brand")
public class PmsBrandController {
    @Resource
    private IPmsBrandService iPmsBrandService;

    @PostMapping("/page")
    @Operation(summary = "分页查询品牌列表")
    public Result<IPage<PmsBrand>> page(@RequestBody(required = false) BaseQueryParam param) {
        LambdaQueryWrapper<PmsBrand> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(PmsBrand::getSort);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(PmsBrand::getBrandName, param.getKeyword())
                    .or().like(PmsBrand::getBrandDesc, param.getKeyword())
                    .or().like(PmsBrand::getBrandStory, param.getKeyword());
            param.setPageNum(1);
        }
        IPage<PmsBrand> iPage = iPmsBrandService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        return Result.success(iPage);
    }
    @GetMapping("/all")
    @Operation(summary = "查询所有品牌列表")
    public Result<List<PmsBrand>> all() {
        LambdaQueryWrapper<PmsBrand> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(PmsBrand::getSort);
        wrapper.and(w -> w.eq(PmsBrand::getStatus, 1));
        return Result.success(iPmsBrandService.list(wrapper));
    }

    @PostMapping("/saveOrUpdate")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveOrUpdate(@Valid @RequestBody PmsBrand pmsBrand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        iPmsBrandService.saveOrUpdate(pmsBrand);
        return Result.success();
    }
    @GetMapping("/delete/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> delete(@PathVariable Long id) {
        if (id == null) {
            return Result.failure("id不能为空");
        }
        iPmsBrandService.removeById(id);
        return Result.success();
    }
}
