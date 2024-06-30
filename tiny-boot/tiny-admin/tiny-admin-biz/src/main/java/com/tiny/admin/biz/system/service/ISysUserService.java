package com.tiny.admin.biz.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiny.admin.biz.system.dto.SysUserDto;
import com.tiny.admin.biz.system.entity.SysUser;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lxh
 * @since 2024-06-07
 */
public interface ISysUserService extends IService<SysUser> {
    IPage<SysUserDto> page(@RequestBody(required = false) BaseQueryParam param);

    Boolean saveOrEdit(SysUserDto sysUserDto);
}
