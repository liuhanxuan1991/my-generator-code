package com.lhx.study.mygeneratorcode.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lhx
 * @Date: 2018/12/14 13:53
 */
public class BaseConstant {

    public static final String MSG = "msg";

    public static final String PAGE_LOGIN = "login";

    public static final String PAGE_INDEX = "index";

    public static final String SESSION_KEY = "user_key";

    public static final String SYS_ROLE_KEY_SUPER_ADMIN = "super_admin";

    public static final String SYS_ROLE_KEY_ADMIN = "admin";

    public static final Integer IS_ENABLED_NO = 0;//是否启用-否

    public static final Integer IS_ENABLED_YES = 1;//是否启用-是

    public static final Integer DELETE_FLAG_NO = 0;//是否删除-否

    public static final Integer DELETE_FLAG_YES = 1;//是否删除-是


    /****************************************包名默认值********************************************/
    public static final String PACKAGE_DEFAULT_NAME_MODEL = "model";//实体类
    public static final String PACKAGE_DEFAULT_NAME_DAO = "dao";//dao类
    public static final String PACKAGE_DEFAULT_NAME_IMPL = "impl";//实现类
    /****************************************包名默认值********************************************/

    /****************************************类后缀拼接默认值********************************************/
    public static final String CLASS_LAST_JION_DEFAULT_SERVICE = "Service";
    public static final String CLASS_LAST_JION_DEFAULT_SERVICEIMPL = "ServiceImpl";
    public static final String CLASS_LAST_JION_DEFAULT_MAPPER = "Mapper";
    /****************************************类后缀拼接默认值********************************************/




    /****************************************模版类型********************************************/
    public static final Integer MYSQL_TEMPLATE_TYPE_ENTITY_GETSET = 1;//entity模版（setter，getter）
    public static final Integer MYSQL_TEMPLATE_TYPE_ENTITY_LOMBOK = 2;//entity模版（lombok）
    public static final Integer MYSQL_TEMPLATE_TYPE_GETSET = 3;//getSet模版
    public static final Integer MYSQL_TEMPLATE_TYPE_SERVICE = 4;//service模版
    public static final Integer MYSQL_TEMPLATE_TYPE_SERVICEIMPL = 5;//serviceImpl模版
    public static final Integer MYSQL_TEMPLATE_TYPE_BASEDAO = 6;//baseDao模版
    public static final Integer MYSQL_TEMPLATE_TYPE_DAO_BASEDAO = 7;//dao模版（引用baseDao）
    public static final Integer MYSQL_TEMPLATE_TYPE_DAO = 8;//dao模版
    public static final Integer MYSQL_TEMPLATE_TYPE_XML = 9;//xml模版
    /****************************************模版类型********************************************/


    /****************************************模版替换key值********************************************/

    public static final String MYSQL_TEMPLATE_KEY_AUTHOR = "author";//作者key
    public static final String MYSQL_TEMPLATE_KEY_TIME = "Time";//创建日期key

    /****************************************实体模版key值********************************************/
    public static final String MYSQL_TEMPLATE_KEY_PACKAGE = "package";//实体类包路径key
    public static final String MYSQL_TEMPLATE_KEY_IMPORTDATE = "importDate";//日期key
    public static final String MYSQL_TEMPLATE_KEY_IMPORTMATH = "importMath";//java.math.* key
    public static final String MYSQL_TEMPLATE_KEY_ENTITYNAME = "EntityName";//实体类名称key
    public static final String MYSQL_TEMPLATE_KEY_ATTR_LIST = "attr_list";//实体类属性key
    public static final String MYSQL_TEMPLATE_KEY_ATTR_GETSET_LIST = "attr_getset_list";//实体类属性getSet key
    /****************************************实体模版key值********************************************/


    /****************************************getSet模版key值********************************************/
    public static final String MYSQL_TEMPLATE_KEY_COMMENT = "comment";//getSet注释的key
    public static final String MYSQL_TEMPLATE_KEY_JAVATYPE = "JavaType";//实体类属性类型key
    public static final String MYSQL_TEMPLATE_KEY_ATTRNAME_UPPER = "AttrName";//实体类大写属性名key
    public static final String MYSQL_TEMPLATE_KEY_ATTRNAME_LOWER = "attrName";//实体类小写属性名key
    /****************************************getSet模版key值********************************************/


    /****************************************service模版key值********************************************/
    public static final String MYSQL_TEMPLATE_KEY_SERVICESUPERPACKAGE = "ServiceSuperPackage";//service接口路径key
    public static final String MYSQL_TEMPLATE_KEY_ENTITYPACKAGE = "EntityPackage";//实体类路径key
    public static final String MYSQL_TEMPLATE_KEY_SERVICENAME = "ServiceName";//service接口key
    public static final String MYSQL_TEMPLATE_KEY_PRIMARYJAVATYPE = "PrimaryJavaType";//主键java类型key
    public static final String MYSQL_TEMPLATE_KEY_PRIMARYCOLUMN = "PrimaryColumn";//主键字段名称key
    /****************************************service模版key值********************************************/


    /****************************************serviceImpl模版key值********************************************/
    public static final String MYSQL_TEMPLATE_KEY_SERVICEIMPLSUPERPACKAGE = "ServiceImplSuperPackage";//service实现类路径key
    public static final String MYSQL_TEMPLATE_KEY_DAOSUPERPACKAGE = "DaoSuperPackage";//dao接口包路径key
    public static final String MYSQL_TEMPLATE_KEY_SERVICEIMPLNAME = "ServiceImplName";//实现类名称key
    public static final String MYSQL_TEMPLATE_KEY_DAONAME = "DaoName";//dao接口名称key
    public static final String MYSQL_TEMPLATE_KEY_DAOATTR = "DaoAttr";//dao接口名称(首字母小写)key
    /****************************************serviceImpl模版key值********************************************/


    /****************************************xml模版key值********************************************/
    public static final String MYSQL_TEMPLATE_KEY_PRIMARYKEY = "PrimaryKey";//主键
    public static final String MYSQL_TEMPLATE_KEY_PRIMARYJDBCTYPE = "PrimaryJdbcType";//主键jdbc类型key
    public static final String MYSQL_TEMPLATE_KEY_FEILDMAPLIST = "FeildMapList";//resultMap集合key
    public static final String MYSQL_TEMPLATE_KEY_FEILDJOINID = "FeildJoinId";//表所有字段名key
    public static final String MYSQL_TEMPLATE_KEY_TABLENAME = "TableName";//表名
    public static final String MYSQL_TEMPLATE_KEY_FEILDIFLIST = "FeildIfList";//where中条件判断
    public static final String MYSQL_TEMPLATE_KEY_FEILDJOIN = "FeildJoin";//插入字段集合
    public static final String MYSQL_TEMPLATE_KEY_FEILDMAPJOIN = "FeildMapJoin";//插入字段map集合
    public static final String MYSQL_TEMPLATE_KEY_FEILDIFJOIN = "FeildIfJoin";//插入字段if集合
    public static final String MYSQL_TEMPLATE_KEY_FEILDIFMAPJOIN = "FeildIfMapJoin";//插入字段if，map集合
    public static final String MYSQL_TEMPLATE_KEY_FOREACHFEILDMAPJOIN = "ForeachFeildMapJoin";//批量插入value集合
    public static final String MYSQL_TEMPLATE_KEY_FEILDIFSETLIST = "FeildIfSetList";//修改set
    public static final String MYSQL_TEMPLATE_KEY_FOREACHFEILDIFSETLIST = "ForeachFeildIfSetList";//批量修改set
    public static final String MYSQL_TEMPLATE_KEY_USEGENERATEDKEYS = "useGeneratedKeys";//插入回填id
    /****************************************xml模版key值********************************************/


    /****************************************模版替换key值********************************************/


    public static final String PRIVATE = "private ";
    public static final String PUBLIC = "public ";
    public static final String OBJECT = "Object";
    public static final String STRING = "String";
    public static final String INTEGER = "Integer";
    public static final String LONG = "Long";
    public static final String FLOAT = "Float";
    public static final String DOUBLE = "Double";
    public static final String DATE = "Date";
    public static final String TIMESTAMP = "Timestamp";
    public static final String LBYTE = "byte[]";
    public static final String DECIMAL = "BigDecimal";
    public static final String BIT = "Boolean";
    public static final String PRIVATE_OBJECT = "private Object ";
    public static final String PRIVATE_VOID = "private void ";
    public static final String PRIVATE_STRING = "private String ";
    public static final String PRIVATE_INTEGER = "private Integer ";
    public static final String PRIVATE_LONG = "private Long ";
    public static final String PRIVATE_FLOAT = "private Float ";
    public static final String PRIVATE_DOUBLE = "private Double ";
    public static final String PRIVATE_BIGDECIMAL = "private BigDecimal ";
    public static final String PRIVATE_DATE = "private Date ";
    public static final String PRIVATE_TIMESTAMP = "private Timestamp ";
    public static final String PRIVATE_BIT = "private Boolean ";
    public static final String PRIVATE_LBYTE = "private byte[] ";
    public static final String PUBLIC_VOID = "public void ";
    public static final String GET = "get";
    public static final String SET = "set";
    public static final String ENTER = "\r\n";
    public static final String TAB1 = "    ";
    public static final String TAB2 = "        ";
    public static final String TAB3 = "            ";
    public static final String TAB4 = "                    ";
    public static final String TAB5 = "                        ";
    public static final String IMPORT_DATE = "import java.util.Date";
    public static final String IMPORT_BIGDECIMAL = "import java.math.BigDecimal;";
    public static final String IMPORT_TIMESTAMP = "import java.util.Timestamp";
    public static final Map<String, String> JDBC_TYPE_MAP = new HashMap();

    static {
        JDBC_TYPE_MAP.put("bigint", "BIGINT");
        JDBC_TYPE_MAP.put("BIGINT", "BIGINT");

        JDBC_TYPE_MAP.put("bit", "BIT");
        JDBC_TYPE_MAP.put("BIT", "BIT");

        JDBC_TYPE_MAP.put("blob", "BLOB");
        JDBC_TYPE_MAP.put("BLOB", "BLOB");

        JDBC_TYPE_MAP.put("char", "CHAR");
        JDBC_TYPE_MAP.put("CHAR", "CHAR");

        JDBC_TYPE_MAP.put("clob", "CLOB");
        JDBC_TYPE_MAP.put("CLOB", "CLOB");

        JDBC_TYPE_MAP.put("date", "DATE");
        JDBC_TYPE_MAP.put("DATE", "DATE");

        JDBC_TYPE_MAP.put("decimal", "DECIMAL");
        JDBC_TYPE_MAP.put("DECIMAL", "DECIMAL");

        JDBC_TYPE_MAP.put("double", "DOUBLE");
        JDBC_TYPE_MAP.put("DOUBLE", "DOUBLE");

        JDBC_TYPE_MAP.put("float", "FLOAT");
        JDBC_TYPE_MAP.put("FLOAT", "FLOAT");

        JDBC_TYPE_MAP.put("int", "INTEGER");
        JDBC_TYPE_MAP.put("INT", "INTEGER");

        JDBC_TYPE_MAP.put("integer", "INTEGER");
        JDBC_TYPE_MAP.put("INTEGER", "INTEGER");

        JDBC_TYPE_MAP.put("numeric", "NUMERIC");
        JDBC_TYPE_MAP.put("NUMERIC", "NUMERIC");

        JDBC_TYPE_MAP.put("real", "REAL");
        JDBC_TYPE_MAP.put("REAL", "REAL");

        JDBC_TYPE_MAP.put("smallint", "SMALLINT");
        JDBC_TYPE_MAP.put("SMALLINT", "SMALLINT");

        JDBC_TYPE_MAP.put("time", "TIME");
        JDBC_TYPE_MAP.put("TIME", "TIME");

        JDBC_TYPE_MAP.put("timestamp", "TIMESTAMP");
        JDBC_TYPE_MAP.put("TIMESTAMP", "TIMESTAMP");

        JDBC_TYPE_MAP.put("datetime", "TIMESTAMP");
        JDBC_TYPE_MAP.put("DATETIME", "TIMESTAMP");

        JDBC_TYPE_MAP.put("varchar", "VARCHAR");
        JDBC_TYPE_MAP.put("text", "VARCHAR");

        JDBC_TYPE_MAP.put("tinyint", "TINYINT");
        JDBC_TYPE_MAP.put("TINYINT", "TINYINT");

        JDBC_TYPE_MAP.put("mediumtext", "VARCHAR");
        JDBC_TYPE_MAP.put("MEDIUMTEXT", "VARCHAR");

//        JDBC_TYPE_MAP.put("tinyint", "INTEGER");
//        JDBC_TYPE_MAP.put("smallint", "INTEGER");



    }


}
