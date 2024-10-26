package com.tiny.admin.biz.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tiny.admin.biz.system.dto.SysUserDto;
import com.tiny.admin.biz.system.entity.SysUser;
import com.tiny.admin.biz.system.service.ISysUserService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.BaseController;
import com.tiny.core.web.Result;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
    @Resource
    private ISysUserService iSysUserService;

    @PostMapping("/page")
    public Result<IPage<SysUserDto>> page(@RequestBody(required = false) BaseQueryParam param) {
        return Result.success(iSysUserService.page(param));
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

//    @PostMapping("/assignRoles/{userId}")
//    @Transactional(rollbackFor = Exception.class)
//    public Result<Boolean> assignRoles(@PathVariable("userId") String userId, @RequestBody Set<String> roleIds) {
//        iSysUserRoleRelService.remove(new LambdaQueryWrapper<SysUserRoleRel>().eq(SysUserRoleRel::getUserId, userId));
//        if (CollectionUtils.isNotEmpty(roleIds)) {
//            List<SysUserRoleRel> relList = roleIds.stream().map(roleId -> {
//                SysUserRoleRel rel = new SysUserRoleRel();
//                rel.setUserId(userId);
//                rel.setRoleId(roleId);
//                return rel;
//            }).toList();
//            iSysUserRoleRelService.saveBatch(relList);
//        }
//        return Result.success(true);
//    }

    @PostMapping("/saveOrUpdate")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> saveOrEdit(@RequestBody SysUserDto sysUserDto) {
        iSysUserService.saveOrEdit(sysUserDto);
        return Result.success();
    }
}
