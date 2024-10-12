package com.tiny.admin.biz.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiny.admin.biz.system.dto.I18nTranslationDto;
import com.tiny.admin.biz.system.entity.SysI18nRaw;
import com.tiny.admin.biz.system.entity.SysI18nTranslation;
import com.tiny.admin.biz.system.mapper.SysI18nRawMapper;
import com.tiny.admin.biz.system.service.ISysI18nRawService;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * i18n原始内容表 服务实现类
 * </p>
 *
 * @author lxh
 * @since 2024-10-10
 */
@Service
public class SysI18nRawServiceImpl extends ServiceImpl<SysI18nRawMapper, SysI18nRaw> implements ISysI18nRawService {
    @Resource
    private SysI18nRawMapper sysI18nRawMapper;

    @Override
    public Map<String, Map<String, String>> getTranslation() {
        MPJLambdaWrapper<SysI18nRaw> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(SysI18nRaw.class).leftJoin(
                SysI18nTranslation.class, SysI18nTranslation::getRawId, SysI18nRaw::getId)
                .selectCollection(SysI18nTranslation.class, I18nTranslationDto::getTranslationList, a->a);
        List<I18nTranslationDto> dtoList = sysI18nRawMapper.selectJoinList(I18nTranslationDto.class, wrapper);
        Map<String, Map<String, String>> res =new HashMap<>();
        if(!CollectionUtils.isEmpty(dtoList)) {
            dtoList.forEach(dto -> {
                        res.putIfAbsent(dto.getRawContent(), new HashMap<>());
                        if (!CollectionUtils.isEmpty(dto.getTranslationList())) {
                            dto.getTranslationList().forEach(translation -> res.get(dto.getRawContent())
                                    .put(translation.getLanguageCode(), translation.getTranslatedContent()));
                        }
                    }
            );
        }
        return res;
    }

    @Override
    public IPage<I18nTranslationDto> getTranslationPage(BaseQueryParam param) {
        MPJLambdaWrapper<SysI18nRaw> sysI18nRawIdWrapper = new MPJLambdaWrapper<>();
        sysI18nRawIdWrapper.select(SysI18nRaw::getId).distinct();
        if(StringUtils.hasText(param.getKeyword())) {
            sysI18nRawIdWrapper.like(SysI18nRaw::getRawContent, param.getKeyword());
        }
        Page<SysI18nRaw> page1 = new Page<>(param.getPageNum(), param.getPageSize());
        Page<SysI18nRaw> sysI18nRawPage = sysI18nRawMapper.selectPage(page1, sysI18nRawIdWrapper);

        MPJLambdaWrapper<SysI18nRaw> resultWrapper = new MPJLambdaWrapper<>();
        resultWrapper.selectAll(SysI18nRaw.class).leftJoin(
                        SysI18nTranslation.class, SysI18nTranslation::getRawId, SysI18nRaw::getId)
                .selectCollection(SysI18nTranslation.class, I18nTranslationDto::getTranslationList, a -> a)
                .in(SysI18nRaw::getId, sysI18nRawPage.getRecords().stream().map(SysI18nRaw::getId).toList());
        List<I18nTranslationDto> records = sysI18nRawMapper.selectJoinList(I18nTranslationDto.class, resultWrapper);
        IPage<I18nTranslationDto> res = new Page<>(page1.getCurrent(), page1.getSize(), page1.getTotal());
        res.setRecords(records);
        return res;
    }


}
