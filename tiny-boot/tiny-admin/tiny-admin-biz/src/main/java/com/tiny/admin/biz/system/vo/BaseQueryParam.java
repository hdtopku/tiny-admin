package com.tiny.admin.biz.system.vo;

import lombok.Data;

/**
 * Created by lxh at 2024-06-14 19:27:21
 */
@Data
public class BaseQueryParam {
    private String keyword;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
