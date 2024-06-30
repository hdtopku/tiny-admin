package com.tiny.admin.biz.system.po;

import com.tiny.admin.biz.system.entity.SysRole;
import com.tiny.admin.biz.system.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * Created by lxh at 2024-06-29 22:29:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserPo extends SysUser {
    private Set<SysRole> roles;
}
