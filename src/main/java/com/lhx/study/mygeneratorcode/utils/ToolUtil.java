package com.lhx.study.mygeneratorcode.utils;

import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.entity.bo.MysqlDbColumn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class ToolUtil {

    public static String tableToEntity(String table) {
        String entity = "";
        if (table != null && table.length() > 1) {
            if (table.indexOf("_") != -1) {
                String[] strs = table.split("_");

                for(int i = 0; i < strs.length; ++i) {
                    if (strs[i] != null && strs[i].length() > 0) {
                        entity = entity + CommonUtils.firstCharToUpperCase(strs[i]);
                    }
                }
            } else {
                entity = CommonUtils.firstCharToUpperCase(table);
            }

            if (entity.matches("[A-Z]+")) {
                entity = CommonUtils.firstCharToUpperCase(entity.toLowerCase());
            }
        }

        return entity;
    }

    public static String primaryFeild(String primaryKey) {
        String entity = "";
        if (primaryKey != null && primaryKey.length() > 1) {
            if (primaryKey.indexOf("_") != -1) {
                String[] strs = primaryKey.split("_");

                for(int i = 0; i < strs.length; ++i) {
                    if (strs[i] != null && strs[i].length() > 0) {
                        entity = entity + CommonUtils.firstCharToUpperCase(strs[i]);
                    }
                }
            } else {
                entity = primaryKey;
            }

            if (entity.matches("[A-Z]+")) {
                entity = CommonUtils.firstCharToUpperCase(entity.toLowerCase());
            }
        }

        return entity;
    }

    public static void main(String[] args) {
        primaryFeild("id_as");
        System.out.println(CommonUtils.firstCharToLowerCase(tableToEntity("idas")));
        System.out.println("ABB".matches("[A-Z]+"));
    }

    public static String entityToTable(String entity) {
        String table = null;
        if (entity != null && entity.length() > 1) {
            table = entity.replaceAll("[A-Z]", "_$0").toLowerCase();
        }

        return table;
    }

    public static String feildToColumn(String feild) {
        return entityToTable(feild);
    }

    public static String columnToFeild(String column) {
        return CommonUtils.firstCharToLowerCase(tableToEntity(column));
    }

    public static String packageToDir(String packages) {
        String dir = packages;
        if (packages != null && packages.length() > 1) {
            dir = packages.replaceAll("[.]", "/");
        }

        return dir;
    }

    public static String getSuperPackage(String packages) {
        String pack = packages;
        if (packages != null && packages.indexOf(".") != -1) {
            pack = packages.substring(0, packages.lastIndexOf("."));
        }

        return pack;
    }

    public static String getRealPackage(String packages) {
        String pack = packages;
        if (packages != null && packages.indexOf(".") != -1 && packages.startsWith("main.java.")) {
            pack = packages.substring(10);
        }

        return pack;
    }

    public static String parseTemplate(String content, String key, String value) {
        try {
            if (content != null) {
                content = content.replaceAll("\\{" + key + "\\}", value);
            }
        } catch (Exception var4) {
            ;
        }

        return content;
    }

    public static void writeContentToFile(String file, String content) throws Exception {
        FileOutputStream fos = new FileOutputStream(new File(file));
        fos.write(content.getBytes("UTF-8"));
        fos.flush();
        fos.close();
    }

    public static String getJavaType(String jdbcType) {
        String javaType = "Object";
        if (!jdbcType.equalsIgnoreCase("varchar") && !jdbcType.equalsIgnoreCase("char") && !jdbcType.equalsIgnoreCase("text") && !jdbcType.equalsIgnoreCase("mediumtext")) {
            if (!jdbcType.equalsIgnoreCase("int") && !jdbcType.equalsIgnoreCase("integer") && !jdbcType.equalsIgnoreCase("tinyint")) {
                if (!jdbcType.equalsIgnoreCase("long") && !jdbcType.equalsIgnoreCase("bigint")) {
                    if (!jdbcType.equalsIgnoreCase("float") && !jdbcType.equalsIgnoreCase("number")) {
                        if (jdbcType.equalsIgnoreCase("double")) {
                            javaType = "Double";
                        } else if (jdbcType.equalsIgnoreCase("decimal")) {
                            javaType = "BigDecimal";
                        } else if (jdbcType.equalsIgnoreCase("date")) {
                            javaType = "Date";
                        } else if (!jdbcType.equalsIgnoreCase("timestamp") && !jdbcType.equalsIgnoreCase("time")) {
                            if (jdbcType.equalsIgnoreCase("blob")) {
                                javaType = "byte[]";
                            } else if (jdbcType.equalsIgnoreCase("bit")) {
                                javaType = "Boolean";
                            }
                        } else {
                            javaType = "Date";
                        }
                    } else {
                        javaType = "Float";
                    }
                } else {
                    javaType = "Long";
                }
            } else {
                javaType = "Integer";
            }
        } else {
            javaType = "String";
        }

        return javaType;
    }

    public static String getVarJavaType(String jdbcType) {
        String javaType = "private Object ";
        if (!jdbcType.equalsIgnoreCase("varchar") && !jdbcType.equalsIgnoreCase("char") && !jdbcType.equalsIgnoreCase("text")) {
            if (!jdbcType.equalsIgnoreCase("int") && !jdbcType.equalsIgnoreCase("integer") && !jdbcType.equalsIgnoreCase("tinyint")) {
                if (!jdbcType.equalsIgnoreCase("long") && !jdbcType.equalsIgnoreCase("bigint")) {
                    if (!jdbcType.equalsIgnoreCase("float") && !jdbcType.equalsIgnoreCase("number")) {
                        if (jdbcType.equalsIgnoreCase("double")) {
                            javaType = "private Double ";
                        } else if (jdbcType.equalsIgnoreCase("decimal")) {
                            javaType = "private BigDecimal ";
                        } else if (jdbcType.equalsIgnoreCase("date")) {
                            javaType = "private Date ";
                        } else if (!jdbcType.equalsIgnoreCase("timestamp") && !jdbcType.equalsIgnoreCase("time")) {
                            if (jdbcType.equalsIgnoreCase("blob")) {
                                javaType = "private byte[] ";
                            } else if (jdbcType.equalsIgnoreCase("text")) {
                                javaType = "private String ";
                            } else if (jdbcType.equalsIgnoreCase("mediumtext")) {
                                javaType = "private String ";
                            } else if (jdbcType.equalsIgnoreCase("bit")) {
                                javaType = "private Boolean ";
                            }
                        } else {
                            javaType = "private Date ";
                        }
                    } else {
                        javaType = "private Float ";
                    }
                } else {
                    javaType = "private Long ";
                }
            } else {
                javaType = "private Integer ";
            }
        } else {
            javaType = "private String ";
        }

        return javaType;
    }

    public static String getAttrDeclare(String javaType, String attr, Object value) {
        String declare = "";
        if (javaType.indexOf(" String") != -1) {
            declare = javaType + attr;
            if (value != null) {
                declare = declare + " = \"" + value.toString() + "\"";
            }
        } else if (javaType.indexOf(" Integer") != -1) {
            declare = javaType + attr;
            if (value != null) {
                declare = declare + " = " + value.toString();
            }
        } else if (javaType.indexOf(" Long") != -1) {
            declare = javaType + attr;
        } else if (javaType.indexOf(" Float") != -1) {
            declare = javaType + attr;
        } else if (javaType.indexOf(" Double") != -1) {
            declare = javaType + attr;
        } else if (javaType.indexOf(" Date") != -1) {
            declare = javaType + attr;
        } else if (javaType.indexOf(" Timestamp") != -1) {
            declare = javaType + attr;
        } else {
            declare = javaType + attr;
        }

        return declare + ";";
    }

    public static String getJarDir(String url) {
        if (url != null && url.startsWith("file")) {
            url = url.substring(6);
            url = url.substring(0, url.indexOf("!"));
            File jar = new File(url);
            url = jar.getParentFile().getPath();
        } else {
            url = url.substring(0, url.lastIndexOf("/"));
        }

        return url;
    }

    public static String StringDecode(String param) {
        if (param != null && !param.trim().equals("")) {
            try {
                param = URLDecoder.decode(param, "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
            }
        }

        return param;
    }

    public static String getFeildMapList(List<MysqlDbColumn> columnList) {
        StringBuffer feildMapList = new StringBuffer();

        try {
            MysqlDbColumn mdc = null;
            String maps = "<result column=\"{0}\" jdbcType=\"{1}\" property=\"{2}\" />";
            int i = 0;

            for(int k = columnList.size(); i < k; ++i) {
                mdc = (MysqlDbColumn)columnList.get(i);
                if (!mdc.isPrimaryKey()) {
                    feildMapList.append("        ").append(CommonUtils.format(maps, new String[]{mdc.getColumnName(), (String)BaseConstant.JDBC_TYPE_MAP.get(mdc.getDataType()), columnToFeild(mdc.getColumnName())})).append("\r\n");
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return feildMapList.toString();
    }

    public static String getFeildJoinId(List<MysqlDbColumn> columnList) {
        StringBuffer feildJoinId = new StringBuffer();
        MysqlDbColumn mdc = null;
        int i = 0;

        for(int k = columnList.size(); i < k; ++i) {
            mdc = (MysqlDbColumn)columnList.get(i);
            feildJoinId.append(mdc.getColumnName());
            if (i < k - 1) {
                feildJoinId.append(", ");
            }
        }

        feildJoinId.append("\r\n");
        return feildJoinId.toString();
    }

    public static String getFeildIfList(List<MysqlDbColumn> columnList) {
        StringBuffer feildIfList = new StringBuffer();
        MysqlDbColumn mdc = null;
        StringBuffer feild = new StringBuffer("        ");
        feild.append("<if test=\"{0} != null\" >\r\n").append("            ").append("and {1} = #{{2},jdbcType={3}}\r\n").append("        ").append("</if>\r\n");
        int i = 0;

        for(int k = columnList.size(); i < k; ++i) {
            mdc = (MysqlDbColumn)columnList.get(i);
            feildIfList.append(CommonUtils.format(feild.toString(), new String[]{columnToFeild(mdc.getColumnName()), mdc.getColumnName(), columnToFeild(mdc.getColumnName()), (String)BaseConstant.JDBC_TYPE_MAP.get(mdc.getDataType())}));
        }

        return feildIfList.toString();
    }

    public static String getFeildJoin(List<MysqlDbColumn> columnList) {
        StringBuffer feildJoin = new StringBuffer("            ");
        MysqlDbColumn mdc = null;
        int i = 0;

        for(int k = columnList.size(); i < k; ++i) {
            mdc = (MysqlDbColumn)columnList.get(i);
            if (mdc.isPrimaryKey()) {
                if (mdc.getExtra() == null || !mdc.getExtra().equalsIgnoreCase("auto_increment")) {
                    feildJoin.append(mdc.getColumnName()).append(", ");
                }
            } else {
                feildJoin.append(mdc.getColumnName()).append(", ");
            }
        }

        feildJoin.append("\r\n");
        return feildJoin.toString().replaceFirst(", $", "");
    }

    public static String getFeildMapJoin(List<MysqlDbColumn> columnList) {
        StringBuffer feildMapJoin = new StringBuffer("            ");
        MysqlDbColumn mdc = null;
        String map = "#{{0},jdbcType={1}}, ";
        int i = 0;

        for(int k = columnList.size(); i < k; ++i) {
            mdc = (MysqlDbColumn)columnList.get(i);
            if (mdc.isPrimaryKey()) {
                if (mdc.getExtra() == null || !mdc.getExtra().equalsIgnoreCase("auto_increment")) {
                    feildMapJoin.append(CommonUtils.format(map, new String[]{columnToFeild(mdc.getColumnName()), (String)BaseConstant.JDBC_TYPE_MAP.get(mdc.getDataType())}));
                }
            } else {
                feildMapJoin.append(CommonUtils.format(map, new String[]{columnToFeild(mdc.getColumnName()), (String)BaseConstant.JDBC_TYPE_MAP.get(mdc.getDataType())}));
            }
        }

        return feildMapJoin.toString().replaceFirst(", $", "");
    }

    public static String getFeildIfJoin(List<MysqlDbColumn> columnList) {
        StringBuffer feildIfList = new StringBuffer();
        MysqlDbColumn mdc = null;
        StringBuffer feild = new StringBuffer("        ");
        feild.append("<if test=\"{0} != null\">\r\n").append("            ").append("{1},\r\n").append("        ").append("</if>\r\n");
        int i = 0;

        for(int k = columnList.size(); i < k; ++i) {
            mdc = (MysqlDbColumn)columnList.get(i);
            if (mdc.isPrimaryKey()) {
                if (mdc.getExtra() == null || !mdc.getExtra().equalsIgnoreCase("auto_increment")) {
                    feildIfList.append("            ").append(columnToFeild(mdc.getColumnName())).append(",\r\n");
                }
            } else {
                feildIfList.append(CommonUtils.format(feild.toString(), new String[]{columnToFeild(mdc.getColumnName()), mdc.getColumnName()}));
            }
        }

        return feildIfList.toString();
    }

    public static String getFeildIfMapJoin(List<MysqlDbColumn> columnList) {
        StringBuffer feildIfMapJoin = new StringBuffer();
        MysqlDbColumn mdc = null;
        StringBuffer feild = new StringBuffer("        ");
        feild.append("<if test=\"{0} != null\">\r\n").append("            ").append("#{{0},jdbcType={1}},\r\n").append("        ").append("</if>\r\n");
        String pmap = "#{{0},jdbcType={1}},\r\n";
        int i = 0;

        for(int k = columnList.size(); i < k; ++i) {
            mdc = (MysqlDbColumn)columnList.get(i);
            if (mdc.isPrimaryKey()) {
                if (mdc.getExtra() == null || !mdc.getExtra().equalsIgnoreCase("auto_increment")) {
                    feildIfMapJoin.append(CommonUtils.format(pmap, new String[]{columnToFeild(mdc.getColumnName()), (String)BaseConstant.JDBC_TYPE_MAP.get(mdc.getDataType())}));
                }
            } else {
                feildIfMapJoin.append(CommonUtils.format(feild.toString(), new String[]{columnToFeild(mdc.getColumnName()), (String)BaseConstant.JDBC_TYPE_MAP.get(mdc.getDataType())}));
            }
        }

        return feildIfMapJoin.toString();
    }

    public static String getFeildIfSetList(List<MysqlDbColumn> columnList) {
        StringBuffer feildIfSetList = new StringBuffer();
        MysqlDbColumn mdc = null;
        StringBuffer feild = new StringBuffer("        ");
        feild.append("<if test=\"{0} != null\">\r\n").append("            ").append("{1} = #{{0},jdbcType={2}},\r\n").append("        ").append("</if>\r\n");
        int i = 0;

        for(int k = columnList.size(); i < k; ++i) {
            mdc = (MysqlDbColumn)columnList.get(i);
            if (!mdc.isPrimaryKey()) {
                feildIfSetList.append(CommonUtils.format(feild.toString(), new String[]{columnToFeild(mdc.getColumnName()), mdc.getColumnName(), (String)BaseConstant.JDBC_TYPE_MAP.get(mdc.getDataType())}));
            }
        }

        return feildIfSetList.toString();
    }

    public static String getFeildSetList(List<MysqlDbColumn> columnList) {
        StringBuffer feildSetList = new StringBuffer();
        MysqlDbColumn mdc = null;
        String map = "{0} = #{{1},jdbcType={2}}, \r\n";
        int i = 0;

        for(int k = columnList.size(); i < k; ++i) {
            mdc = (MysqlDbColumn)columnList.get(i);
            if (!mdc.isPrimaryKey()) {
                feildSetList.append("        ").append(CommonUtils.format(map, new String[]{mdc.getColumnName(), columnToFeild(mdc.getColumnName()), (String)BaseConstant.JDBC_TYPE_MAP.get(mdc.getDataType())}));
            }
        }

        return feildSetList.toString().replaceFirst(", $", "");
    }

    public static String getForeachFeildMapJoin(List<MysqlDbColumn> columnList) {
        StringBuffer feildSetList = new StringBuffer();
        MysqlDbColumn mdc = null;
        String map = "#{item.{0},jdbcType={1}}, \r\n";
        int i = 0;

        for(int k = columnList.size(); i < k; ++i) {
            mdc = (MysqlDbColumn)columnList.get(i);
            if (mdc.isPrimaryKey()) {
                if (mdc.getExtra() == null || !mdc.getExtra().equalsIgnoreCase("auto_increment")) {
                    feildSetList.append("            ").append(CommonUtils.format(map, new String[]{columnToFeild(mdc.getColumnName()), (String)BaseConstant.JDBC_TYPE_MAP.get(mdc.getDataType())}));
                }
            } else {
                feildSetList.append("        ").append(CommonUtils.format(map, new String[]{columnToFeild(mdc.getColumnName()), (String)BaseConstant.JDBC_TYPE_MAP.get(mdc.getDataType())}));
            }
        }

        return feildSetList.toString().replaceFirst(", $", "");
    }

    public static String getUpdateBatchFeildSetList(List<MysqlDbColumn> columnList) {
        StringBuffer feildSetList = new StringBuffer();
        MysqlDbColumn mdc = null;
        String map = "{0} = #{item.{1},jdbcType={2}}, \r\n";
        int i = 0;

        for(int k = columnList.size(); i < k; ++i) {
            mdc = (MysqlDbColumn)columnList.get(i);
            if (!mdc.isPrimaryKey()) {
                feildSetList.append("        ").append(CommonUtils.format(map, new String[]{mdc.getColumnName(), columnToFeild(mdc.getColumnName()), (String)BaseConstant.JDBC_TYPE_MAP.get(mdc.getDataType())}));
            }
        }

        return feildSetList.toString().replaceFirst(", $", "");
    }
}
