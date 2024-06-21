package com.tiny.admin.biz.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tiny.admin.biz.system.dto.SysUserDto;
import com.tiny.admin.biz.system.entity.SysUser;
import com.tiny.admin.biz.system.service.ISysUserService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.BaseController;
import com.tiny.core.web.Result;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
@RestController
@RequestMapping("/system/sysUser")
public class SysUserController  extends BaseController<ISysUserService, SysUser> {

    @PostMapping("/page")
    public Result<IPage<SysUserDto>> page(@RequestBody(required = false) BaseQueryParam param) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysUser::getUpdateTime);
        if(StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SysUser::getUsername, param.getKeyword())
                    .or().like(SysUser::getNickname, param.getKeyword())
                    .or().like(SysUser::getEmail, param.getKeyword())
                    .or().like(SysUser::getPhone, param.getKeyword());
        } else {
            wrapper.eq(SysUser::getStatus, param.getStatus());
        }
        IPage<SysUser> iPage =this.baseService.page(new Page<>(param.getPageNum(), param.getPageSize()),wrapper);
        Page<SysUserDto> res = new Page<>();
        BeanUtil.copyProperties(iPage, res);
        res.setRecords(iPage.getRecords().stream().map(item->{
            SysUserDto dto = BeanUtil.copyProperties(item, SysUserDto.class);
            dto.setKey(item.getId());
            return dto;
        }).toList());
        return Result.success(res);
    }
    @PostMapping("/updatePassword/{id}")
    public Result<Boolean> updatePassword(@PathVariable("id") String id, @RequestBody String password) {
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        SysUser newUser = new SysUser();
        newUser.setId(id);
        newUser.setPassword(encodedPassword);
        boolean res = this.baseService.updateById(newUser);
        return Result.success(res);
    }
}
