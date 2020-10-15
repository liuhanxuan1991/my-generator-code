package com.lhx.study.mygeneratorcode.utils;

import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.entity.bo.MysqlDbColumn;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.*;

import static com.lhx.study.mygeneratorcode.utils.ToolUtil.columnToFeild;

/**
 * mysql模版工具类
 * @Author: lhx
 * @Date: 2019/1/10 14:47
 */
public class MySqlTemplateUtil {


    /**
     * 表名转成实体类名
     * 首字母大写，驼峰式
     * @param table
     * @return
     */
    public static String tableToEntity(String table) {
        StringBuffer sb = new StringBuffer();
        table = table.toLowerCase();//全部转换小写
        if (StringUtils.isNotBlank(table)) {//不为空
            if (table.contains("_")) {//包含下划线
                String[] strs = table.split("_");
                for (String str : strs) {
                    if (StringUtils.isNotBlank(str)) {
                        sb.append(CommonUtils.firstCharToUpperCase(str));

                    }
                }
            } else {
                sb.append(CommonUtils.firstCharToUpperCase(table));
            }
        }

        return sb.toString();
    }


    /**
     * 创建lombok实体类
     * @param content 模版
     * @param author 作者
     * @param entityPath 实体类路径
     * @param entityName 实体类名称
     * @param mysqlDbColumnList 字段属性集合
     * @return
     */
    public static String createEntityLombok(String content, String author, String entityPath, String entityName, List<MysqlDbColumn> mysqlDbColumnList) {
        //替换包路径
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_PACKAGE, entityPath);
        //替换作者
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_AUTHOR, author);
        //替换生成日期
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_TIME, DateUtil.format(new Date(), DateUtil.DATE_FORMAT));
        //替换实体类名称
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_ENTITYNAME, entityName);
        //获取实体类属性
        Map<String, Object> attrList = createAttrList(mysqlDbColumnList);
        //导入日期标识
        Boolean dateFlag = (Boolean)attrList.get("dateFlag");
        if (dateFlag) {
            content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_IMPORTDATE, "import java.util.Date;");
        }else {
            content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_IMPORTDATE, "");
        }
        //导入java.math.*标识
        Boolean mathFlag = (Boolean)attrList.get("mathFlag");
        if (mathFlag) {
            content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_IMPORTMATH, "import java.math.BigDecimal;");
        }else {
            content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_IMPORTMATH, "");
        }
        //替换实体类属性
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_ATTR_LIST, (String)attrList.get("sb"));
        return content;
    }


    /**
     * 创建getSet实体类
     * @param content 实体类模版
     * @param getSetContent getset模版
     * @param author 作者
     * @param entityPath 实体类路径
     * @param entityName 实体类名称
     * @param mysqlDbColumnList 字段属性集合
     * @return
     */
    public static String createEntityGetSet(String content, String getSetContent, String author, String entityPath, String entityName, List<MysqlDbColumn> mysqlDbColumnList){
        content = createEntityLombok(content, author, entityPath, entityName, mysqlDbColumnList);
        String attrGetsetList = createAttrGetsetList(getSetContent, mysqlDbColumnList);
        content = parseTemplate(content,BaseConstant.MYSQL_TEMPLATE_KEY_ATTR_GETSET_LIST,attrGetsetList);
        return content;
    }


    /**
     * 创建service接口
     * @param content 模版内容
     * @param author 作者
     * @param servicePath service接口路径
     * @param entityPath 实体类路径
     * @param entityName 实体类名称
     * @param primaryColumn 主键信息
     * @return
     */
    public static String createService(String content, String author, String servicePath, String entityPath, String entityName, MysqlDbColumn primaryColumn){
        //替换包路径
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_SERVICESUPERPACKAGE, servicePath);
        //替换实体类包路径
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_ENTITYPACKAGE, StringUtils.isNotBlank(entityPath) ? entityPath : replaceLast(servicePath,BaseConstant.PACKAGE_DEFAULT_NAME_MODEL));
        //替换作者
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_AUTHOR, author);
        //替换生成日期
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_TIME, DateUtil.format(new Date(), DateUtil.DATE_FORMAT));
        //替换service类名称
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_SERVICENAME, entityName + BaseConstant.CLASS_LAST_JION_DEFAULT_SERVICE);
        //替换实体类名称
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_ENTITYNAME, entityName);
        //替换主键类型
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_PRIMARYJAVATYPE, getJavaType(primaryColumn).trim());
        //替换主键名
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_PRIMARYCOLUMN, CommonUtils.firstCharToLowerCase(tableToEntity(primaryColumn.getColumnName())));


        return content;
    }


    /**
     * 创建service实现类
     * @param content 模版内容
     * @param author 作者
     * @param servicePath service接口路径
     * @param daoPath dao接口路径
     * @param entityPath 实体类路径
     * @param entityName 实体类名称
     * @param primaryColumn 主键信息
     * @return
     */
    public static String createServiceImpl(String content, String author, String servicePath, String daoPath, String entityPath, String entityName, MysqlDbColumn primaryColumn){
        content = createService(content, author, servicePath, entityPath, entityName, primaryColumn);
        //替换包路径
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_SERVICEIMPLSUPERPACKAGE, servicePath +"." +BaseConstant.PACKAGE_DEFAULT_NAME_IMPL);
        //替换dao包路径
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_DAOSUPERPACKAGE, StringUtils.isNotBlank(daoPath) ? daoPath : replaceLast(servicePath,BaseConstant.PACKAGE_DEFAULT_NAME_DAO));
        //替换实现类名称
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_SERVICEIMPLNAME, entityName + BaseConstant.CLASS_LAST_JION_DEFAULT_SERVICEIMPL);
        //替换dao接口名称
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_DAONAME, entityName + BaseConstant.CLASS_LAST_JION_DEFAULT_MAPPER);
        //替换dao接口名称(首字母小写)
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_DAOATTR, CommonUtils.firstCharToLowerCase(entityName + BaseConstant.CLASS_LAST_JION_DEFAULT_MAPPER));

        return content;
    }


    /**
     * 创建baseDao模版
     * @param content
     * @param author
     * @param daoPath
     * @return
     */
    public static String createBasedaoTemplate(String content, String author,String daoPath){
        //替换包路径
        content =  parseTemplate(content,BaseConstant.MYSQL_TEMPLATE_KEY_DAOSUPERPACKAGE,daoPath);
        //替换作者
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_AUTHOR, author);
        //替换生成日期
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_TIME, DateUtil.format(new Date(), DateUtil.DATE_FORMAT));

        return content;

    }


    /**
     * 创建dao接口（引用base）
     * @param content
     * @param author
     * @param daoPath
     * @param entityPath
     * @param entityName
     * @param primaryColumn
     * @return
     */
    public static String createDaoQuoteTemplate(String content, String author, String daoPath, String entityPath, String entityName,MysqlDbColumn primaryColumn){
        //替换包路径
        content = parseTemplate(content,BaseConstant.MYSQL_TEMPLATE_KEY_DAOSUPERPACKAGE,daoPath);
        //替换实体类包路径
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_ENTITYPACKAGE, StringUtils.isNotBlank(entityPath) ? entityPath : replaceLast(daoPath,BaseConstant.PACKAGE_DEFAULT_NAME_MODEL));
        //替换作者
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_AUTHOR, author);
        //替换生成日期
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_TIME, DateUtil.format(new Date(), DateUtil.DATE_FORMAT));
        //替换实体类名称
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_ENTITYNAME, entityName);
        //替换dao接口名称
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_DAONAME, entityName + BaseConstant.CLASS_LAST_JION_DEFAULT_MAPPER);
        //替换主键类型
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_PRIMARYJAVATYPE, getJavaType(primaryColumn).trim());

        return content;

    }


    /**
     * 创建dao接口
     * @param content
     * @param author
     * @param daoPath
     * @param entityPath
     * @param entityName
     * @param primaryColumn
     * @return
     */
    public static String createDao(String content, String author, String daoPath, String entityPath, String entityName, MysqlDbColumn primaryColumn){
        content = createDaoQuoteTemplate(content,author,daoPath,entityPath,entityName,primaryColumn);
        //替换主键名
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_PRIMARYCOLUMN, CommonUtils.firstCharToLowerCase(tableToEntity(primaryColumn.getColumnName())));
        return content;
    }


    /**
     * 创建xml
     * @param content 模版
     * @param daoPath dao路径
     * @param entityPath 实体类路径
     * @param entityName 实体类名称
     * @param primaryColumn 主键信息
     * @param columns 表字段集合信息（不包含主键）
     * @return
     */
    public static String createXml(String content, String daoPath, String entityPath, String entityName, String tableName, MysqlDbColumn primaryColumn, List<MysqlDbColumn> columns){

        if ("auto_increment".equalsIgnoreCase(primaryColumn.getExtra())){
            content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_USEGENERATEDKEYS,"useGeneratedKeys=\"true\" keyProperty=\"{PrimaryColumn}\"");
        }else {
            content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_USEGENERATEDKEYS,"");
        }

        //替换dao包路径
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_DAOSUPERPACKAGE, daoPath);
        //替换dao接口名称
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_DAONAME, entityName + BaseConstant.CLASS_LAST_JION_DEFAULT_MAPPER);
        //替换实体类包路径
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_ENTITYPACKAGE, entityPath);
        //替换实体类名称
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_ENTITYNAME, entityName);
        //替换主键名
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_PRIMARYKEY,primaryColumn.getColumnName());
        //替换主键类型
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_PRIMARYJAVATYPE, getJavaType(primaryColumn).trim());
        //替换主键jdbc类型key
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_PRIMARYJDBCTYPE,StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()):getJdbcType(primaryColumn));
        //替换主键名
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_PRIMARYCOLUMN,CommonUtils.firstCharToLowerCase(tableToEntity(primaryColumn.getColumnName())));
        //替换resultMap
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_FEILDMAPLIST,getFeildMapList(primaryColumn,columns));
        //替换表所有字段名
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_FEILDJOINID,getFeildJoinId(primaryColumn,columns));
        //替换表名
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_TABLENAME,tableName);
        //替换where中的条件
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_FEILDIFLIST,getWhereFeildIfList(primaryColumn,columns));
        //替换插入字段集合
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_FEILDJOIN,getFeildJoin(primaryColumn,columns));
        //替换插入字段map集合
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_FEILDMAPJOIN,getFeildMapJoin(primaryColumn,columns));
        //替换插入字段if集合
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_FEILDIFJOIN,getFeildIfJoin(primaryColumn,columns));
        //替换插入字段if，map集合
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_FEILDIFMAPJOIN,getFeildIfMapJoin(primaryColumn,columns));
        //替换批量插入字段if，map集合
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_FOREACHFEILDMAPJOIN,getForeachFeildMapJoin(primaryColumn,columns));
        //替换修改set
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_FEILDIFSETLIST,getFeildIfSetList(columns));
        //替换批量修改set
        content = parseTemplate(content, BaseConstant.MYSQL_TEMPLATE_KEY_FOREACHFEILDIFSETLIST,getForeachFeildIfSetList(columns));

        return content;
    }







    /**
     * 替换模版值
     * @param content
     * @param key
     * @param value
     * @return
     */
    public static String parseTemplate(String content, String key, String value) {
        try {
            if (content != null) {
                content = content.replaceAll("\\{" + key + "\\}", value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }




    /**
     * 创建实体类的属性
     * @param columnList
     * @return
     */
    public static Map<String,Object> createAttrList(List<MysqlDbColumn> columnList) {
        Map<String,Object> map = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        if (CollectionUtils.isNotEmpty(columnList)) {
            boolean dateFlag = false;
            boolean mathFlag = false;
            for(MysqlDbColumn column: columnList) {
                //判断表里是否有日期
                if ("date".equalsIgnoreCase(column.getDataType())
                        || "time".equalsIgnoreCase(column.getDataType())
                        || "datetime".equalsIgnoreCase(column.getDataType())
                        || "timestamp".equalsIgnoreCase(column.getDataType())
                        || "year".equalsIgnoreCase(column.getDataType())) {
                    dateFlag = true;
                }
                if ("decimal".equalsIgnoreCase(column.getDataType())
                        || "bigint".equalsIgnoreCase(column.getDataType())){
                    mathFlag = true;
                }
                sb.append("    ")
                        .append("/** ")
                        .append(StringUtils.isBlank(column.getColumnComment()) ? column.getColumnName() : column.getColumnComment())
                        .append(" */")
                        .append("\r\n")
                        .append("    ")
                        .append(getAttrDeclare(getVarJavaType(column), CommonUtils.firstCharToLowerCase(tableToEntity(column.getColumnName())), column.getColumnDefault()))
                        .append("\r\n")
                        .append("\r\n");
            }
            map.put("dateFlag",dateFlag);
            map.put("mathFlag",mathFlag);
            map.put("sb",sb.toString());
        }

        return map;
    }


    /**
     * 获取实体类属性的定义类型
     * @param column
     * @return
     */
    public static String getVarJavaType(MysqlDbColumn column) {
        return "private "+getJavaType(column);
    }


    /**
     * 获取实体类属性的类型
     * @param column
     * @return
     */
    public static String getJavaType(MysqlDbColumn column) {
        String dataType = column.getDataType();//字段类型
        Long a = 1l;
        Long numericPrecision = column.getNumericPrecision();//字段精度
        String javaType = "Object ";
        if ("tinyint".equalsIgnoreCase(dataType)){
            if (a.equals(numericPrecision)){//为1，boolean型
                javaType = "Boolean ";
            }else {
                javaType = "Integer ";
            }
        }else if ("bit".equalsIgnoreCase(dataType)){
            if (a.equals(numericPrecision)){//为1，boolean型
                javaType = "Boolean ";
            }else {
                javaType = "Byte[] ";
            }
        }else if ("smallint".equalsIgnoreCase(dataType)
                || "mediumint".equalsIgnoreCase(dataType)
                || "int".equalsIgnoreCase(dataType)){
            javaType = "Integer ";
        }else if ("varchar".equalsIgnoreCase(dataType)
                || "char".equalsIgnoreCase(dataType)
                || "text".equalsIgnoreCase(dataType)
                || "tinytext".equalsIgnoreCase(dataType)
                || "mediumtext".equalsIgnoreCase(dataType)
                || "longtext".equalsIgnoreCase(dataType)
                || "enum".equalsIgnoreCase(dataType)
                || "set".equalsIgnoreCase(dataType)) {
            javaType = "String ";
        }else if ("integer".equalsIgnoreCase(dataType)
                || "bigint".equalsIgnoreCase(dataType)) {
            javaType = "Long ";
        }else if ("float".equalsIgnoreCase(dataType)) {
            javaType = "Float ";
        }else if ("double".equalsIgnoreCase(dataType)) {
            javaType = "Double ";
        }else if ("decimal".equalsIgnoreCase(dataType)) {
            javaType = "BigDecimal ";
        }else if ("date".equalsIgnoreCase(dataType)
                || "datetime".equalsIgnoreCase(dataType)
                || "timestamp".equalsIgnoreCase(dataType)
                || "time".equalsIgnoreCase(dataType)
                || "year".equalsIgnoreCase(dataType)) {
            javaType = "Date ";
        }else if ("binary".equalsIgnoreCase(dataType)
                || "varbinary".equalsIgnoreCase(dataType)
                || "tinyblob".equalsIgnoreCase(dataType)
                || "blob".equalsIgnoreCase(dataType)
                || "mediumblob".equalsIgnoreCase(dataType)
                || "longblob".equalsIgnoreCase(dataType)) {
            javaType = "Byte[] ";
        }
        return javaType;
    }


    /**
     * 获取xml字段属性的类型
     * @param column
     * @return
     */
    public static String getJdbcType(MysqlDbColumn column) {
        String dataType = column.getDataType();//字段类型
        Long a = 1l;
        Long numericPrecision = column.getNumericPrecision();//字段精度
        String jdbcType = "";
        if ("tinyint".equalsIgnoreCase(dataType)){
            if (a.equals(numericPrecision)){//为1，boolean型
                jdbcType = "BIT";
            }else {
                jdbcType = "INTEGER";
            }
        }else if ("bit".equalsIgnoreCase(dataType)){
                jdbcType = "BIT";
        }else if ("smallint".equalsIgnoreCase(dataType)
                || "mediumint".equalsIgnoreCase(dataType)
                || "int".equalsIgnoreCase(dataType)
                || "integer".equalsIgnoreCase(dataType)){
            jdbcType = "INTEGER";
        }else if ("varchar".equalsIgnoreCase(dataType)
                || "char".equalsIgnoreCase(dataType)
                || "text".equalsIgnoreCase(dataType)
                || "tinytext".equalsIgnoreCase(dataType)
                || "mediumtext".equalsIgnoreCase(dataType)
                || "longtext".equalsIgnoreCase(dataType)
                || "enum".equalsIgnoreCase(dataType)
                || "set".equalsIgnoreCase(dataType)) {
            jdbcType = "VARCHAR";
        }else if ("bigint".equalsIgnoreCase(dataType)) {
            jdbcType = "BIGINT";
        }else if ("float".equalsIgnoreCase(dataType)) {
            jdbcType = "FLOAT";
        }else if ("double".equalsIgnoreCase(dataType)) {
            jdbcType = "DOUBLE";
        }else if ("decimal".equalsIgnoreCase(dataType)) {
            jdbcType = "DECIMAL";
        }else if ("date".equalsIgnoreCase(dataType)
                || "datetime".equalsIgnoreCase(dataType)
                || "timestamp".equalsIgnoreCase(dataType)
                || "time".equalsIgnoreCase(dataType)
                || "year".equalsIgnoreCase(dataType)) {
            jdbcType = "TIMESTAMP";
        }else if ("binary".equalsIgnoreCase(dataType)) {
            jdbcType = "BINARY";
        }else if ("varbinary".equalsIgnoreCase(dataType)){
            jdbcType = "VARBINARY";
        }else if ("tinyblob".equalsIgnoreCase(dataType)) {
            jdbcType = "TINYBLOB";
        }else if ("blob".equalsIgnoreCase(dataType)) {
            jdbcType = "BLOB";
        }else if ("mediumblob".equalsIgnoreCase(dataType)) {
            jdbcType = "MEDIUMBLOB";
        }else if ("longblob".equalsIgnoreCase(dataType)) {
            jdbcType = "LONGVARBINARY";
        }
        return jdbcType;
    }


    /**
     * 拼接实体类的属性
     * @param javaType
     * @param attr
     * @param value
     * @return
     */
    public static String getAttrDeclare(String javaType, String attr, Object value) {
        String declare = javaType + attr;
//        if (javaType.contains("String")) {
//            if (value != null) {
//                declare = declare + " = \"" + value.toString() + "\"";
//            }
//        } else if (javaType.indexOf("Integer") != -1) {
//            if (value != null) {
//                declare = declare + " = " + value.toString();
//            }
//        }
        return declare + ";";
    }


    /**
     * 创建实体类的getter，setter方法
     * @param getSetContent
     * @param columnList
     * @return
     */
    public static String createAttrGetsetList(String getSetContent,List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer();
        if (CollectionUtils.isNotEmpty(columnList)) {
            for(MysqlDbColumn column : columnList) {
                String columnName = tableToEntity(column.getColumnName());//字段名
                String templateContent = ToolUtil.parseTemplate(getSetContent, BaseConstant.MYSQL_TEMPLATE_KEY_COMMENT, StringUtils.isBlank(column.getColumnComment()) ? columnName : column.getColumnComment());
                templateContent = ToolUtil.parseTemplate(templateContent, BaseConstant.MYSQL_TEMPLATE_KEY_JAVATYPE, getJavaType(column).trim());
                templateContent = ToolUtil.parseTemplate(templateContent, BaseConstant.MYSQL_TEMPLATE_KEY_ATTRNAME_UPPER, CommonUtils.firstCharToUpperCase(columnName));
                templateContent = ToolUtil.parseTemplate(templateContent, BaseConstant.MYSQL_TEMPLATE_KEY_ATTRNAME_LOWER, CommonUtils.firstCharToLowerCase(columnName));
                sb.append(templateContent).append("\r\n");
            }
        }
        return sb.toString();
    }


    /**
     * 替换包路径
     * 比如 path=abc.def vaule=123
     * 替换后为abc.123
     * @param path
     * @param vaule
     * @return
     */
    public static String replaceLast(String path,String vaule){
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(path)){
            int i = path.lastIndexOf(".");
            if (-1 != i){
                String substring = path.substring(0,i+1);
                sb.append(substring);
            }
            sb.append(vaule);
        }
        return sb.toString();
    }


    /**
     * 获取resultMap
     * @param primaryColumn
     * @param columnList
     * @return
     */
    public static String getFeildMapList(MysqlDbColumn primaryColumn,List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer();
        if (null != primaryColumn && "PRI".equalsIgnoreCase(primaryColumn.getColumnKey())){
            String idTmp = "<id column=\"{0}\" jdbcType=\"{1}\" property=\"{2}\" />";
            sb.append(MessageFormat.format(idTmp, primaryColumn.getColumnName(),StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()):getJdbcType(primaryColumn),CommonUtils.firstCharToLowerCase(tableToEntity(primaryColumn.getColumnName()))))
                    .append("\r\n");
        }
        String maps = "<result column=\"{0}\" jdbcType=\"{1}\" property=\"{2}\" />";
        for (MysqlDbColumn mysqlDbColumn : columnList) {
            sb.append("\t\t").
                    append(MessageFormat.format(maps,mysqlDbColumn.getColumnName(),StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()):getJdbcType(mysqlDbColumn),CommonUtils.firstCharToLowerCase(tableToEntity(mysqlDbColumn.getColumnName())))).
                    append("\r\n");
        }
        return sb.toString();
    }


    /**
     * 获取表所有字段，用逗号分隔
     * @param primaryColumn
     * @param columnList
     * @return
     */
    public static String getFeildJoinId(MysqlDbColumn primaryColumn,List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer();
        if (null != primaryColumn && "PRI".equalsIgnoreCase(primaryColumn.getColumnKey())){
            sb.append(primaryColumn.getColumnName());
            sb.append(", ");
        }
        for (int i=0;i<columnList.size();i++){
            sb.append(columnList.get(i).getColumnName());
            if (i != columnList.size()-1){
                sb.append(", ");
            }
            if (i!=0 && i%5==0){//5个一行
                sb.append("\r\n\t\t");
            }
        }
        return sb.toString();
    }


    /**
     * 获取where条件集合
     * @param primaryColumn
     * @param columnList
     * @return
     */
    public static String getWhereFeildIfList(MysqlDbColumn primaryColumn,List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer("\t\t\t");
        String tmp = "\t\t\t<if test=\"%s != null\" >\r\n\t\t\t\tand %s = #{%s,jdbcType=%s}\r\n\t\t\t</if>\r\n";
        if (null != primaryColumn && "PRI".equalsIgnoreCase(primaryColumn.getColumnKey())){
            String s = CommonUtils.firstCharToLowerCase(tableToEntity(primaryColumn.getColumnName()));
            sb.append(String.format(tmp,s,primaryColumn.getColumnName(),s,StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()):getJdbcType(primaryColumn)).trim());
            sb.append("\r\n");
        }
        for (MysqlDbColumn mysqlDbColumn : columnList) {
            String s = CommonUtils.firstCharToLowerCase(tableToEntity(mysqlDbColumn.getColumnName()));
            sb.append(String.format(tmp,s,mysqlDbColumn.getColumnName(),s,StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()):getJdbcType(mysqlDbColumn)));
        }
        return sb.toString();
    }


    /**
     * 获取插入字段
     * @param primaryColumn
     * @param columnList
     * @return
     */
    public static String getFeildJoin(MysqlDbColumn primaryColumn,List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer();
        if (null != primaryColumn && "PRI".equalsIgnoreCase(primaryColumn.getColumnKey())){
            if (!"auto_increment".equalsIgnoreCase(primaryColumn.getExtra())){
                sb.append(primaryColumn.getColumnName());
                sb.append(", ");
            }
        }
        for (int i=0;i<columnList.size();i++){
            sb.append(columnList.get(i).getColumnName());
            if (i != columnList.size()-1){
                sb.append(", ");
            }
            if (i!=0 && i%4==0){//4个一行
                sb.append("\r\n\t\t\t");
            }
        }

        return sb.toString();
    }


    /**
     * 获取插入单条记录map集合
     * @param primaryColumn
     * @param columnList
     * @return
     */
    public static String getFeildMapJoin(MysqlDbColumn primaryColumn,List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer();
        String tmp = "#{%s,jdbcType=%s}";
        if (null != primaryColumn && "PRI".equalsIgnoreCase(primaryColumn.getColumnKey())){
            if (!"auto_increment".equalsIgnoreCase(primaryColumn.getExtra())){
                String s = CommonUtils.firstCharToLowerCase(tableToEntity(primaryColumn.getColumnName()));
                sb.append(String.format(tmp,s,StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()):getJdbcType(primaryColumn)));
                sb.append(", \r\n");
            }
        }
        for (int i=0;i<columnList.size();i++){
            MysqlDbColumn mysqlDbColumn = columnList.get(i);
            String s = CommonUtils.firstCharToLowerCase(tableToEntity(mysqlDbColumn.getColumnName()));
            sb.append(String.format(tmp,s,StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()):getJdbcType(mysqlDbColumn)));
            if (i != columnList.size()-1){
                sb.append(", \r\n\t\t\t");
            }
        }

        return sb.toString();
    }


    /**
     * 获取插入单条条件
     * @param primaryColumn
     * @param columnList
     * @return
     */
    public static String getFeildIfJoin(MysqlDbColumn primaryColumn,List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer();
        String tmp = "\t\t\t<if test=\"%s != null\" >\r\n\t\t\t\t%s,\r\n\t\t\t</if>\r\n";
        if (null != primaryColumn && "PRI".equalsIgnoreCase(primaryColumn.getColumnKey())){
            if (!"auto_increment".equalsIgnoreCase(primaryColumn.getExtra())){
                String s = CommonUtils.firstCharToLowerCase(tableToEntity(primaryColumn.getColumnName()));
                sb.append(String.format(tmp,s,primaryColumn.getColumnName()).trim());
            }
        }
        for (MysqlDbColumn mysqlDbColumn : columnList) {
            String s = CommonUtils.firstCharToLowerCase(tableToEntity(mysqlDbColumn.getColumnName()));
            sb.append(String.format(tmp,s,mysqlDbColumn.getColumnName()));
        }
        return sb.toString();
    }



    /**
     * 获取插入单条value
     * @param primaryColumn
     * @param columnList
     * @return
     */
    public static String getFeildIfMapJoin(MysqlDbColumn primaryColumn,List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer();
        String tmp = "\t\t\t<if test=\"%s != null\" >\r\n\t\t\t\t#{%s,jdbcType=%s},\r\n\t\t\t</if>\r\n";
        if (null != primaryColumn && "PRI".equalsIgnoreCase(primaryColumn.getColumnKey())){
            if (!"auto_increment".equalsIgnoreCase(primaryColumn.getExtra())){
                String s = CommonUtils.firstCharToLowerCase(tableToEntity(primaryColumn.getColumnName()));
                sb.append(String.format(tmp,s,s,StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()):getJdbcType(primaryColumn)));
            }
        }
        for (MysqlDbColumn mysqlDbColumn : columnList) {
            String s = CommonUtils.firstCharToLowerCase(tableToEntity(mysqlDbColumn.getColumnName()));
            sb.append(String.format(tmp,s,s,StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()):getJdbcType(mysqlDbColumn)));
        }

        return sb.toString();
    }


    /**
     * 获取批量插入value集合
     * @param primaryColumn
     * @param columnList
     * @return
     */
    public static String getForeachFeildMapJoin(MysqlDbColumn primaryColumn,List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer();
        String tmp = "#{item.%s,jdbcType=%s}";
        if (null != primaryColumn && "PRI".equalsIgnoreCase(primaryColumn.getColumnKey())){
            if (!"auto_increment".equalsIgnoreCase(primaryColumn.getExtra())){
                String s = CommonUtils.firstCharToLowerCase(tableToEntity(primaryColumn.getColumnName()));
                sb.append(String.format(tmp,s,StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(primaryColumn.getDataType()):getJdbcType(primaryColumn)));
                sb.append(", \r\n");
            }
        }
        for (int i=0;i<columnList.size();i++){
            MysqlDbColumn mysqlDbColumn = columnList.get(i);
            String s = CommonUtils.firstCharToLowerCase(tableToEntity(mysqlDbColumn.getColumnName()));
            sb.append(String.format(tmp,s,StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()):getJdbcType(mysqlDbColumn)));
            if (i != columnList.size()-1){
                sb.append(", \r\n\t\t\t");
            }
        }

        return sb.toString();
    }


    /**
     * 获取修改的set条件
     * @param columnList
     * @return
     */
    public static String getFeildIfSetList(List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer();
        String tmp = "\t\t\t<if test=\"%s != null\" >\r\n\t\t\t\t%s = #{%s,jdbcType=%s},\r\n\t\t\t</if>\r\n";
        for (MysqlDbColumn mysqlDbColumn : columnList) {
            String s = CommonUtils.firstCharToLowerCase(tableToEntity(mysqlDbColumn.getColumnName()));
            sb.append(String.format(tmp,s,mysqlDbColumn.getColumnName(),s,StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()):getJdbcType(mysqlDbColumn)));
        }
        return sb.toString();
    }

    /**
     * 获取批量修改的set条件
     * @param columnList
     * @return
     */
    public static String getForeachFeildIfSetList(List<MysqlDbColumn> columnList) {
        StringBuffer sb = new StringBuffer();
        String tmp = "\t\t\t<if test=\"item.%s != null\" >\r\n\t\t\t\t%s = #{item.%s,jdbcType=%s},\r\n\t\t\t</if>\r\n";
        for (MysqlDbColumn mysqlDbColumn : columnList) {
            String s = CommonUtils.firstCharToLowerCase(tableToEntity(mysqlDbColumn.getColumnName()));
            sb.append(String.format(tmp,s,mysqlDbColumn.getColumnName(),s,StringUtils.isNotBlank(BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()))?BaseConstant.JDBC_TYPE_MAP.get(mysqlDbColumn.getDataType()):getJdbcType(mysqlDbColumn)));
        }
        return sb.toString();
    }


}
