package com.tiny.core.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by lxh at 2024-06-07 16:11:03
 */
@SuppressWarnings("all")
public class ApprenticeUtil {

    private static final Pattern humpPattern = Pattern.compile("[A-Z]");
    private static final Pattern linePattern = Pattern.compile("_(\\w)");

    private ApprenticeUtil() {

    }

    /**
     * 驼峰转下划线
     *
     * @Param: [str]
     * @return: java.lang.String
     * @Author: MaSiyi
     * @Date: 2021/11/26
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @Param: [str]
     * @return: java.lang.String
     * @Author: MaSiyi
     * @Date: 2021/11/26
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获取QueryWrapper
     *
     * @Param: [entity]
     * @return: com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<E>
     * @Author: MaSiyi
     * @Date: 2021/11/26
     */
    public static <E> QueryWrapper<E> getQueryWrapper(E entity) {
        QueryWrapper<E> eQueryWrapper = new QueryWrapper<>();
        if (entity == null) {
            return eQueryWrapper;
        }
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            //忽略final字段
            if (Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object obj = field.get(entity);
                if (!ObjectUtils.isEmpty(obj)) {
                    String name = ApprenticeUtil.humpToLine(field.getName());
                    eQueryWrapper.eq(name, obj);
                }
            } catch (IllegalAccessException e) {
                return eQueryWrapper;
            }
        }
        return eQueryWrapper;

    }

    /**
     * 反射获取字段值
     *
     * @Param: [entity, value] value 值为 "id" "name" 等
     * @return: java.lang.Object
     * @Author: MaSiyi
     * @Date: 2021/11/26
     */
    public static <E> Object getValueForClass(E entity, String value) {

        Field id = null;
        PropertyDescriptor pd = null;
        try {
            id = entity.getClass().getDeclaredField(value);
            pd = new PropertyDescriptor(id.getName(), entity.getClass());
        } catch (NoSuchFieldException | IntrospectionException e) {
            e.printStackTrace();
        }
        //获取get方法
        Method getMethod = Objects.requireNonNull(pd).getReadMethod();
        return ReflectionUtils.invokeMethod(getMethod, entity);
    }
}
