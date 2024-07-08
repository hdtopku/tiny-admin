package com.tiny.admin.biz.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiny.admin.biz.system.dto.SysRoleDto;
import com.tiny.admin.biz.system.entity.SysMenu;
import com.tiny.admin.biz.system.entity.SysRole;
import com.tiny.admin.biz.system.entity.SysRoleMenuRel;
import com.tiny.admin.biz.system.mapper.SysRoleMapper;
import com.tiny.admin.biz.system.service.ISysRoleMenuRelService;
import com.tiny.admin.biz.system.service.ISysRoleService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private ISysRoleMenuRelService iSysRoleMenuRelService;

    @PostMapping("/page")
    public Result<IPage<SysRoleDto>> page(@RequestBody(required = false) BaseQueryParam param) {
        MPJLambdaWrapper<SysRole> wrapper1 = new MPJLambdaWrapper<>();
        wrapper1.select(SysRole::getId).distinct();
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper1.like(SysRole::getRoleName, param.getKeyword())
                    .or().like(SysRole::getDescription, param.getKeyword());
        } else {
            wrapper1.eq(SysRole::getStatus, param.getStatus());
        }
        Page<SysRole> page1 = new Page<>(param.getPageNum(), param.getPageSize());
        IPage<SysRole> iPage1 = sysRoleMapper.selectPage(page1, wrapper1);

        MPJLambdaWrapper<SysRole> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(SysRole.class)
                .leftJoin(SysRoleMenuRel.class, SysRoleMenuRel::getRoleId, SysRole::getId)
                .selectCollection(SysMenu.class, SysRoleDto::getMenus, map->map)
                .leftJoin(SysMenu.class, SysMenu::getId, SysRoleMenuRel::getMenuId)
                .in(SysRole::getId, iPage1.getRecords().stream().map(SysRole::getId).toList())
                ;
        wrapper.orderByDesc(SysRole::getUpdateTime);
        List<SysRoleDto> records = sysRoleMapper.selectJoinList(SysRoleDto.class, wrapper);
        IPage<SysRoleDto> res = new Page<>(iPage1.getCurrent(), iPage1.getSize(), iPage1.getTotal());
        res.setRecords(records);
        return Result.success(res);
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

    @Operation(summary = "分配菜单权限")
    @PostMapping("/assignMenu/{roleId}")
    @CacheEvict(value = CACHE_KEY, allEntries = true)
    @Transactional
    public Result<String> assignMenu(@RequestBody Set<String> menuIds, @PathVariable("roleId") String roleId) {
        iSysRoleMenuRelService.remove(new LambdaQueryWrapper<SysRoleMenuRel>().eq(SysRoleMenuRel::getRoleId, roleId));
        List<SysRoleMenuRel> roleMenuRelList = Optional.ofNullable(menuIds).orElse(new HashSet<>())
                .stream().map(menuId -> {
                    SysRoleMenuRel rel = new SysRoleMenuRel();
                    rel.setRoleId(roleId);
                    rel.setMenuId(menuId);
                    return rel;
                }).toList();
        if(CollectionUtils.isNotEmpty(roleMenuRelList)) iSysRoleMenuRelService.saveBatch(roleMenuRelList);
        return Result.success("操作成功");
    }
}
