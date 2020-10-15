package com.lhx.study.mygeneratorcode.utils;

import com.lhx.study.mygeneratorcode.entity.bo.MysqlDbColumn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreateUtil {
    public CreateUtil() {
    }

    public static String createAttrList(boolean hasDate, List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer();
        if (columnList != null) {
            MysqlDbColumn column = null;
            int i = 0;

            for(int k = columnList.size(); i < k; ++i) {
                column = (MysqlDbColumn)columnList.get(i);
                if (column != null && !MapConstantUtil.commonMysqlDbColumnMap.containsKey(column.getColumnName())) {
                    if (column.getDataType().equalsIgnoreCase("date")) {
                        hasDate = true;
                    }

                    if (column.getDataType().equalsIgnoreCase("timestamp") || column.getDataType().equalsIgnoreCase("time")) {
                        hasDate = true;
                    }

                    sb.append("    ").append("/** ").append(CommonUtils.isBlank(column.getColumnComment()) ? column.getColumnName() : column.getColumnComment()).append(" */").append("\r\n").append("    ").append(ToolUtil.getAttrDeclare(ToolUtil.getVarJavaType(column.getDataType()), ToolUtil.columnToFeild(column.getColumnName()), column.getColumnDefault())).append("\r\n").append("\r\n");
                }
            }
        }

        return sb.toString();
    }

    public static String createAttrGetsetList(List<MysqlDbColumn> columnList, String content, String entityName) {
        StringBuffer sb = new StringBuffer();
        if (columnList != null) {
            MysqlDbColumn column = null;
            String attr = null;
            int i = 0;

            for(int k = columnList.size(); i < k; ++i) {
                column = (MysqlDbColumn)columnList.get(i);
                if (column != null) {
                    attr = tableToEntity(column.getColumnName());
                    if (!MapConstantUtil.commonMysqlDbColumnMap.containsKey(CommonUtils.replaceName(attr))) {
                        String templateContent = ToolUtil.parseTemplate(content, "EntityName", entityName);
                        templateContent = ToolUtil.parseTemplate(templateContent, "AttrName", CommonUtils.firstCharToUpperCase(attr));
                        templateContent = ToolUtil.parseTemplate(templateContent, "attrName", CommonUtils.firstCharToLowerCase(attr));
                        templateContent = ToolUtil.parseTemplate(templateContent, "comment", CommonUtils.isBlank(column.getColumnComment()) ? column.getColumnName() : column.getColumnComment());
                        templateContent = ToolUtil.parseTemplate(templateContent, "JavaType", ToolUtil.getJavaType(column.getDataType()));
                        sb.append(templateContent).append("\r\n");
                    }
                }
            }
        }

        return sb.toString();
    }

    public static String tableToEntity(String columnName) {
        String entity = "";
        if (columnName != null && columnName.length() > 1) {
            if (columnName.indexOf("_") != -1) {
                String[] strs = columnName.split("_");

                for(int i = 0; i < strs.length; ++i) {
                    if (strs[i] != null && strs[i].length() > 0) {
                        entity = entity + CommonUtils.firstCharToUpperCase(strs[i]);
                    }
                }
            } else {
                entity = CommonUtils.firstCharToUpperCase(columnName);
            }

            if (entity.matches("[A-Z]+")) {
                entity = CommonUtils.firstCharToUpperCase(entity.toLowerCase());
            }
        }

        return entity;
    }

    public static String entityToJson(List<MysqlDbColumn> columnList) {
        String toString = "";
        StringBuffer sb = new StringBuffer();
        sb.append("\"{");
        columnList.forEach((x) -> {
            String columnName = tableToEntity(x.getColumnName());
            String tou = columnName.substring(0, 1);
            String wei = columnName.substring(1);
            columnName = tou.toLowerCase() + wei;
            sb.append("\r\n\"" + columnName + "\":" + columnName + ",");
        });
        sb.substring(0, sb.length() - 1);
        String str = sb.substring(0, sb.length() - 1);
        str = str + "\r\n}\"";
        return str;
    }

    public static MysqlDbColumn getPrimaryColumn(List<MysqlDbColumn> list) {
        Iterator var1 = list.iterator();

        MysqlDbColumn dbColumn;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            dbColumn = (MysqlDbColumn)var1.next();
        } while(!dbColumn.isPrimaryKey());

        return dbColumn;
    }

    public static void main(String[] args) {
        List<String> stringList = new ArrayList();
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        stringList.add("cc");
        stringList.add("xx");
        stringList.forEach((x) -> {
            String columnName = tableToEntity(x);
            String tou = columnName.substring(0, 1);
            String wei = columnName.substring(1);
            columnName = tou.toLowerCase() + wei;
            sb.append("\r\n\"" + columnName + "\":" + columnName + ",");
        });
        String str = sb.substring(0, sb.length() - 1);
        str = str + "\r\n}";
        System.out.println(str);
    }
}
