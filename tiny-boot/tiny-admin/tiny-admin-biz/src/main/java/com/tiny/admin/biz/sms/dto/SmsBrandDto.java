package com.tiny.admin.biz.sms.dto;

import com.tiny.admin.biz.sms.entity.SmsBrand;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by lxh at 2024-10-30 18:07:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SmsBrandDto extends SmsBrand {
    private String brandName;
    private String logo;
}
