package com.tiny.admin.biz.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.system.dto.SysMenuTree;
import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.service.ISysMenuService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.BaseController;
import com.tiny.core.web.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class SysMenuController extends BaseController<ISysMenuService, SysMenu> {
    @Resource
    private ISysMenuService sysMenuService;

    @GetMapping("/tree")
    public Result<List<SysMenuTree>> menuTree() {
        return Result.success(sysMenuService.menuTree());
    }


    @PostMapping("/page")
    public Result<IPage<SysMenu>> page(@RequestBody(required = false) BaseQueryParam param) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysMenu::getSort);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SysMenu::getName, param.getKeyword())
                    .or().like(SysMenu::getUrl, param.getKeyword());
        }
        IPage<SysMenu> iPage = this.baseService.page(new Page<>(param.getPageNum(), param.getPageSize()), wrapper);
        return Result.success(iPage);
    }
}
