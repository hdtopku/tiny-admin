package com.tiny.admin.biz.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tiny.admin.biz.system.dto.I18nTranslationDto;
import com.tiny.admin.biz.system.service.ISysI18nRawService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * i18n原始内容表 前端控制器
 * </p>
 *
 * @author lxh
 * @since 2024-10-10
 */
@RestController
@RequestMapping("/system/sysI18nRaw")
public class SysI18nRawController {
    @Resource
    private ISysI18nRawService iSysI18nRawService;

    @GetMapping("/list")
    public Result<Object> list() {
        return Result.success(iSysI18nRawService.getTranslation());
    }

    @PostMapping("/page")
    public Result<IPage<I18nTranslationDto>> page(@RequestBody(required = false) BaseQueryParam param) {
        IPage<I18nTranslationDto> page = iSysI18nRawService.getTranslationPage(param);
        return Result.success(page);
    }
}