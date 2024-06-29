package com.tiny.admin.biz.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiny.admin.biz.system.dto.SysUserDto;
import com.tiny.admin.biz.system.entity.SysRole;
import com.tiny.admin.biz.system.entity.SysUser;
import com.tiny.admin.biz.system.entity.SysUserRoleRel;
import com.tiny.admin.biz.system.mapper.SysUserMapper;
import com.tiny.admin.biz.system.po.SysUserVo;
import com.tiny.admin.biz.system.service.ISysUserRoleRelService;
import com.tiny.admin.biz.system.service.ISysUserService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.BaseController;
import com.tiny.core.web.Result;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Resource
    private ISysUserRoleRelService iSysUserRoleRelService;
    @Resource
    private SysUserMapper sysUserMapper;

    @PostMapping("/page")
    public Result<IPage<SysUserDto>> page(@RequestBody(required = false) BaseQueryParam param) {
        MPJLambdaWrapper<SysUser> wrapper = new MPJLambdaWrapper<>();
        wrapper.orderByDesc(SysUser::getUpdateTime);
        if(StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(SysUser::getUsername, param.getKeyword())
                    .or().like(SysUser::getNickname, param.getKeyword())
                    .or().like(SysUser::getEmail, param.getKeyword())
                    .or().like(SysUser::getPhone, param.getKeyword());
        } else {
            wrapper.eq(SysUser::getStatus, param.getStatus() == null? 1 : param.getStatus());
        }
        wrapper.selectCollection(SysRole.class, SysUserVo::getRoles)
                .leftJoin(SysUserRoleRel.class, SysUserRoleRel::getUserId, SysUser::getId)
                .leftJoin(SysRole.class, SysRole::getId, SysUserRoleRel::getRoleId);
        IPage<SysUserVo> iPage =sysUserMapper.selectJoinPage(new Page<>(param.getPageNum(), param.getPageSize()),
                SysUserVo.class, wrapper);
        Page<SysUserDto> res = new Page<>();
        BeanUtil.copyProperties(iPage, res);
        res.setRecords(iPage.getRecords().stream().map(item->{
            SysUserDto dto = BeanUtil.copyProperties(item, SysUserDto.class);
            dto.setKey(item.getId());
            if(CollectionUtils.isNotEmpty(item.getRoles())) {
                dto.setRoleNames(new HashSet<>(item.getRoles().stream().map(SysRole::getRoleName).toList()));
            }
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
    @PostMapping("/assignRoles/{userId}")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> assignRoles(@PathVariable("userId") String userId, @RequestBody Set<String> roleIds) {
        iSysUserRoleRelService.remove(new LambdaQueryWrapper<SysUserRoleRel>().eq(SysUserRoleRel::getUserId, userId));
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<SysUserRoleRel> relList = roleIds.stream().map(roleId -> {
                SysUserRoleRel rel = new SysUserRoleRel();
                rel.setUserId(userId);
                rel.setRoleId(roleId);
                return rel;
            }).toList();
            iSysUserRoleRelService.saveBatch(relList);
        }
        return Result.success(true);
    }
}
