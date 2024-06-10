package com.tiny.admin.biz.system.controller;

import com.tiny.admin.biz.system.dto.SysMenuDTO;
import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.service.ISysMenuService;
import com.tiny.core.web.BaseController;
import com.tiny.core.web.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<List<SysMenuDTO>> menuTree() {
        return Result.success(sysMenuService.menuTree());
    }
}
