package com.lhx.study.mygeneratorcode.vo.response.database;

import lombok.Data;

/**
 * @Author: lhx
 * @Date: 2019/1/7 18:55
 */
@Data
public class MysqlDbColumnResVo {

    private String tableSchema;//表架构
    private String tableName;//表名
    private String columnName;//列名
    private String columnType;//字段类型
    private String dataType;//数据类型
    private Object columnDefault;//默认值
    private Long characterOctetLength;//八位字节长度
    private Long characterMaximumLength;//最大长度
    private Integer ordinalPosition;//列位置
    private String isNullable;//是否允许为空
    private String columnKey;//字段主键
    private String extra;//额外信息
    private String columnComment;//注释
    private String privileges;//权限
    private Long numericPrecision;//精度
    private Long numericScale;//小数位
    private String characterSetName;//字符集
    private String collationName;//排序规则

}
