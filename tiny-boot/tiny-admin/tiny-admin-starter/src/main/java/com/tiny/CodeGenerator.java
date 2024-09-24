package com.tiny;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by lxh at 2024-06-06 20:12:08
 */
public class CodeGenerator {
    private static final String url = "jdbc:mysql://121.41.170.120:3306/tiny-admin?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "wz95ctxb3hvxezu57ko";
    private static final String outputDir = "/Users/lxh/Desktop/";

    public static void main(String[] args) {
        FastAutoGenerator.create(url, username, password)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.enableSpringdoc().outputDir(outputDir).author("lxh"))
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent("com.tiny.admin.biz")
                        .moduleName("sms"))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
//                .strategyConfig((scanner, builder) -> builder.addInclude(getTables("all"))
                        .entityBuilder()
                        .enableLombok()
                        .enableFileOverride()
                        .addTableFills(
                                new Column("create_time", FieldFill.INSERT)
                        )
                        .controllerBuilder().enableRestStyle()
                        .build())
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
