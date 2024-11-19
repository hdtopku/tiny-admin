package com.tiny.admin.biz.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiny.admin.biz.system.dto.SysUserDto;
import com.tiny.admin.biz.system.entity.SysRole;
import com.tiny.admin.biz.system.entity.SysUser;
import com.tiny.admin.biz.system.entity.SysUserRoleRel;
import com.tiny.admin.biz.system.mapper.SysUserMapper;
import com.tiny.admin.biz.system.po.SysUserPo;
import com.tiny.admin.biz.system.service.ISysRoleService;
import com.tiny.admin.biz.system.service.ISysUserRoleRelService;
import com.tiny.admin.biz.system.service.ISysUserService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private ISysUserRoleRelService iSysUserRoleRelService;
    @Resource
    private ISysRoleService iSysRoleService;

    @Override
    public IPage<SysUserDto> page(BaseQueryParam param) {
        MPJLambdaWrapper<SysUser> idWrapper = new MPJLambdaWrapper<>();
        idWrapper.select(SysUser::getId);
        idWrapper.orderByDesc(SysUser::getUpdateTime);
        if(StringUtils.isNotBlank(param.getKeyword())) {
            idWrapper.like(SysUser::getUsername, param.getKeyword())
                    .or().like(SysUser::getNickname, param.getKeyword())
                    .or().like(SysUser::getEmail, param.getKeyword())
                    .or().like(SysUser::getPhone, param.getKeyword());
            param.setPageNum(1);
        } else {
            idWrapper.eq(SysUser::getEnabled, param.getEnabled() == null? 1 : param.getEnabled());
        }
        Page<SysUser> page = this.page(new Page<>(param.getPageNum(), param.getPageSize()), idWrapper);
        if(CollectionUtils.isEmpty(page.getRecords())) {
            return new Page<>();
        }
        MPJLambdaWrapper<SysUser> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(SysUser.class)
                .selectCollection(SysRole.class, SysUserPo::getRoles)
                .leftJoin(SysUserRoleRel.class, SysUserRoleRel::getUserId, SysUser::getId)
                .leftJoin(SysRole.class, SysRole::getId, SysUserRoleRel::getRoleId)
                .in(SysUser::getId, page.getRecords().stream().map(SysUser::getId).toList());
        List<SysUserPo> sysUserPoList =sysUserMapper.selectJoinList(SysUserPo.class, wrapper);
        Page<SysUserDto> res = new Page<>();
        BeanUtil.copyProperties(page, res);
        if(!CollectionUtils.isEmpty(sysUserPoList)) {
            res.setRecords(sysUserPoList.stream().map(item -> {
                SysUserDto dto = BeanUtil.copyProperties(item, SysUserDto.class);
                dto.setKey(item.getId());
                if (CollectionUtils.isNotEmpty(item.getRoles())) {
                    dto.setRoleNames(new HashSet<>(item.getRoles().stream().map(SysRole::getRoleName).toList()));
                }
                return dto;
            }).toList());
        }
        return res;
    }

    @Override
    @Transactional
    public void saveOrEdit(SysUserDto sysUserDto) {
        SysUser sysUser = BeanUtil.copyProperties(sysUserDto, SysUser.class);
        List<SysUserRoleRel> sysUserRoleRels = iSysUserRoleRelService.list(new MPJLambdaWrapper<SysUserRoleRel>().eq(SysUserRoleRel::getUserId, sysUserDto.getKey()));
        Set<String> oldRoleIds = Optional.ofNullable(sysUserRoleRels).orElse(new ArrayList<>())
                .stream().map(SysUserRoleRel::getRoleId).collect(Collectors.toSet());
        List<SysRole> roles = Optional.ofNullable(iSysRoleService.list()).orElse(new ArrayList<>());
        Set<String> newRoleIds = Optional.ofNullable(sysUserDto.getRoleNames()).orElse(new HashSet<>()).stream().map(item -> {
            SysRole role = roles.stream().filter(r -> r.getRoleName().equals(item)).findFirst().orElseThrow(() -> new RuntimeException("角色："+item+ "不存在"));
            return role.getId();
        }).collect(Collectors.toSet());
        this.saveOrUpdate(sysUser);
        if(!oldRoleIds.equals(newRoleIds)) {
            updateUserRoleRel(sysUser.getId(), newRoleIds);
        }
    }
    private void updateUserRoleRel(String userId, Set<String> newRoleIds) {
        iSysUserRoleRelService.remove(new LambdaQueryWrapper<SysUserRoleRel>().eq(SysUserRoleRel::getUserId, userId));
        if(CollectionUtils.isNotEmpty(newRoleIds)) {
            List<SysUserRoleRel> list = newRoleIds.stream().map(item -> {
                SysUserRoleRel sysUserRoleRel = new SysUserRoleRel();
                sysUserRoleRel.setUserId(userId);
                sysUserRoleRel.setRoleId(item);
                return sysUserRoleRel;
            }).toList();
            iSysUserRoleRelService.saveBatch(list);
        }
    }

}
