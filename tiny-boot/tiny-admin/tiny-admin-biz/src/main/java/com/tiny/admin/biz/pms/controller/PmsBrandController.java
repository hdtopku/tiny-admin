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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
        }
        IPage<PmsBrand> iPage = iPmsBrandService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        return Result.success(iPage);
    }
}
