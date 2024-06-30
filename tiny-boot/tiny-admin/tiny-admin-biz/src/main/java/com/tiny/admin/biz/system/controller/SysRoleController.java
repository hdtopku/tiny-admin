package com.tiny.admin.biz.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.system.entity.SysRole;
import com.tiny.admin.biz.system.service.ISysRoleService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
@RestController
@RequestMapping("/system/sysRole")
public class SysRoleController {
    private static final String CACHE_KEY = "tiny-admin:sysRole:listAll";
    @Resource
    private ISysRoleService baseService;

    @PostMapping("/page")
    public Result<IPage<SysRole>> page(@RequestBody(required = false) BaseQueryParam param) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysRole::getUpdateTime);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SysRole::getRoleName, param.getKeyword())
                    .or().like(SysRole::getDescription, param.getKeyword());
        } else {
            wrapper.eq(SysRole::getStatus, param.getStatus());
        }
        IPage<SysRole> iPage = baseService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        return Result.success(iPage);
    }

    @Operation(summary = "list查")
    @PostMapping("/list")
    @Cacheable(value = CACHE_KEY, key = "'list'")
    public Result<List<SysRole>> list(@RequestBody(required = false) SysRole entity) {
        return Result.success(baseService.list(new LambdaQueryWrapper<>(entity)));
    }

    @Operation(summary = "删")
    @PostMapping("/deleteByIds")
    @CacheEvict(value = CACHE_KEY, allEntries = true)
    public Result<String> delete(@RequestBody List<String> ids) {
        baseService.removeByIds(ids);
        return Result.success("删除成功");
    }

    @Operation(summary = "存或改")
    @PostMapping("/saveOrUpdate")
    @CacheEvict(value = CACHE_KEY, allEntries = true)
    public Result<String> save(@RequestBody SysRole entity) {
        baseService.saveOrUpdate(entity);
        return Result.success("操作成功");
    }
}
