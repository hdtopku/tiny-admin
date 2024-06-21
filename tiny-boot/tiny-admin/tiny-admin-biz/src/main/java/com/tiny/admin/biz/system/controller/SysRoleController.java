package com.tiny.admin.biz.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.system.entity.SysRole;
import com.tiny.admin.biz.system.service.ISysRoleService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.BaseController;
import com.tiny.core.web.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class SysRoleController extends BaseController<ISysRoleService,SysRole> {

    @PostMapping("/page")
    public Result<IPage<SysRole>> page(@RequestBody(required = false) BaseQueryParam param) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysRole::getUpdateTime);
        if(StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SysRole::getRoleName, param.getKeyword())
                    .or().like(SysRole::getDescription, param.getKeyword());
        } else {
            wrapper.eq(SysRole::getStatus, param.getStatus());
        }
        IPage<SysRole> iPage =this.baseService.page(new Page<>(param.getPageNum(), param.getPageSize()),wrapper);
        return Result.success(iPage);
    }
}
