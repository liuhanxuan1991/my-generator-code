package com.lhx.study.mygeneratorcode.utils;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

/**
 * 属性转换工具
 * @Author: lhx
 * @Date: 2018/11/2 10:16
 * @Version 1.0
 */
public class PropertiesUtil {

    /**
     * bean属性copy(默认同属性名不同类型不会转换)
     * @param source 来源
     * @param target 目标
     */
    public static void copyProperties(Object source, Object target) {
        copyPropertiesBase(source, target, false, null);
    }


    /**
     * bean属性copy(使用转换器)
     * @param source 来源
     * @param target 目标
     * @param converter 转换器
     */
    public static void copyPropertiesConverter(Object source, Object target, Converter converter) {
        if (null == converter)
            copyProperties(source, target);
        else
            copyPropertiesBase(source, target, true, converter);
    }


    /**
     *
     * @param source 来源
     * @param target 目标
     * @param useConverter 是否使用转换器
     * @param converter 转换器
     */
    public static void copyPropertiesBase(Object source, Object target, boolean useConverter, Converter converter) {
        if(null != source){
            BeanCopier beanCopier = BeanCopier.create(source.getClass(), target.getClass(), useConverter);
            beanCopier.copy(source, target, converter);
        }
    }
}