package com.tiny.admin.biz.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.system.dto.SysMenuTree;
import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.service.ISysMenuService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
@RestController
@RequestMapping(value = "system/sysMenu")
public class SysMenuController {
    @Resource
    private ISysMenuService sysMenuService;

    @PostMapping("/tree")
    public Result<List<SysMenuTree>> menuTree(@RequestBody(required = false) BaseQueryParam param) {
        return Result.success(sysMenuService.menuTree(Objects.isNull(param) ? null : param.getKeyword()));
    }

    @Operation(summary = "删")
    @PostMapping("/deleteByIds")
    public Result<String> delete(@RequestBody List<String> ids) {
        sysMenuService.removeByIds(ids);
        return Result.success("删除成功");
    }

    @Operation(summary = "存或改")
    @PostMapping("/saveOrUpdate")
    public Result<String> saveOrUpdate(@RequestBody SysMenu entity) {
        if(StringUtils.isNotBlank(entity.getUrl())) {
            entity.setUrl(entity.getUrl().trim().toLowerCase());
            if (!entity.getUrl().startsWith("/")) {
                entity.setUrl("/" + entity.getUrl());
            }
        }
        entity.setCreateTime(null);
        entity.setUpdateTime(null);
        sysMenuService.saveOrUpdate(entity);
        return Result.success("操作成功");
    }

    @PostMapping("/page")
    public Result<IPage<SysMenu>> page(@RequestBody(required = false) BaseQueryParam param) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysMenu::getSort);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SysMenu::getName, param.getKeyword())
                    .or().like(SysMenu::getUrl, param.getKeyword());
            param.setPageNum(1);
        } else {
            wrapper.eq(SysMenu::getEnabled, param.getEnabled());
        }
        IPage<SysMenu> iPage = sysMenuService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        return Result.success(iPage);
    }
}
