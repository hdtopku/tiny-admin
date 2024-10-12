package com.tiny.admin.biz.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiny.admin.biz.system.dto.I18nTranslationDto;
import com.tiny.admin.biz.system.entity.SysI18nRaw;
import com.tiny.admin.biz.system.vo.BaseQueryParam;

import java.util.Map;

/**
 * <p>
 * i18n原始内容表 服务类
 * </p>
 *
 * @author lxh
 * @since 2024-10-10
 */
public interface ISysI18nRawService extends IService<SysI18nRaw> {

    Map<String, Map<String, String>> getTranslation();

    IPage<I18nTranslationDto> getTranslationPage(BaseQueryParam param);
}
