//package com.tiny.admin.biz.system.controller;
//
//import com.tiny.admin.biz.system.entity.SysI18nTranslation;
//import com.tiny.admin.biz.system.service.ISysI18nTranslationService;
//import com.tiny.admin.biz.utils.LanguageUtil;
//import com.tiny.core.web.Result;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
///**
// * <p>
// * i18n翻译表 前端控制器
// * </p>
// *
// * @author lxh
// * @since 2024-10-10
// */
//@RestController
//@RequestMapping("/system/sysI18nTranslation")
//public class SysI18nTranslationController {
//    @Resource
//    private LanguageUtil languageUtil;
//
//    @Resource
//    private ISysI18nTranslationService iSysI18nTranslationService;
//    @PostMapping("/saveOrUpdate")
//    public Result<String> saveOrUpdate(@RequestBody SysI18nTranslation translation) {
//        iSysI18nTranslationService.saveOrUpdate(translation);
//        languageUtil.init();
//        return Result.success("操作成功");
//    }
//
//    @DeleteMapping("/{id}")
//    public Result<String> deleteById(@PathVariable("id") String id) {
//        iSysI18nTranslationService.removeById(id);
//        languageUtil.init();
//        return Result.success("操作成功");
//    }
//}
