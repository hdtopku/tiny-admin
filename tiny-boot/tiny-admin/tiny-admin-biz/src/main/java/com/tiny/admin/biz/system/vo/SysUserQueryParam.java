package com.tiny.admin.biz.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by lxh at 2024-06-15 08:50:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserQueryParam extends BaseQueryParam {
    private Boolean status;
}
