package com.lhx.study.mygeneratorcode.entity.bo;

import lombok.Data;

@Data
public class MysqlDbColumn {
    private String tableSchema;
    private String tableName;
    private String columnName;
    private String columnType;
    private String dataType;
    private Object columnDefault;
    private Long characterOctetLength;
    private Long characterMaximumLength;
    private Integer ordinalPosition;
    private String isNullable;
    private String columnKey;
    private String extra;
    private String columnComment;
    private String privileges;
    private Long numericPrecision;
    private Long numericScale;
    private String characterSetName;
    private String collationName;

    public boolean isPrimaryKey() {
        return this.columnKey != null && this.columnKey.equalsIgnoreCase("PRI");
    }
}
