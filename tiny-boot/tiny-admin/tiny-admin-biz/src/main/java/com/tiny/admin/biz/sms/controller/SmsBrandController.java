package com.tiny.admin.biz.sms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiny.admin.biz.pms.entity.PmsBrand;
import com.tiny.admin.biz.sms.dto.SmsBrandDto;
import com.tiny.admin.biz.sms.entity.SmsBrand;
import com.tiny.admin.biz.sms.mapper.SmsBrandMapper;
import com.tiny.admin.biz.sms.service.ISmsBrandService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    private SmsBrandMapper smsBrandMapper;

    @PostMapping("/page")
    @Operation(summary = "分页查询品牌列表")
    public Result<IPage<SmsBrandDto>> page(@RequestBody(required = false) BaseQueryParam param) {
        MPJLambdaWrapper<SmsBrand> wrapper = new MPJLambdaWrapper<>();
        wrapper.orderByAsc(SmsBrand::getSort);
        wrapper.selectAll(SmsBrand.class)
                .select(PmsBrand::getBrandName, PmsBrand::getLogo)
                .leftJoin(PmsBrand.class, PmsBrand::getId, SmsBrand::getBrandId);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SmsBrand::getRemark, param.getKeyword());
            wrapper.or().like(PmsBrand::getBrandName, param.getKeyword());
            param.setPageNum(1);
        } else {
            wrapper.eq(SmsBrand::getEnabled, param.getEnabled());
        }
        IPage<SmsBrandDto> iPage = smsBrandMapper.selectJoinPage(new Page<>(param.getPageNum(), param.getPageSize()), SmsBrandDto.class, wrapper);
        return Result.success(iPage);
    }


    @PostMapping("/saveOrUpdate")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveOrUpdate(@Valid @RequestBody SmsBrand entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.failure(bindingResult.getFieldError().getDefaultMessage());
        }
        entity.setCreateTime(null);
        entity.setUpdateTime(null);
        iSmsBrandService.saveOrUpdate(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> delete(@PathVariable("id") String id) {
        iSmsBrandService.removeById(id);
        return Result.success();
    }
}
