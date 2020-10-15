/*
Navicat MySQL Data Transfer

Source Server         : 我自己的数据库
Source Server Version : 80013
Source Host           : 192.168.1.88:3306
Source Database       : my_generator

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-03-27 14:02:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mysql_code_path_config
-- ----------------------------
DROP TABLE IF EXISTS `mysql_code_path_config`;
CREATE TABLE `mysql_code_path_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `config_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `entity_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `dao_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `mapper_xml_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `service_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `controller_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `user_id` int(10) NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of mysql_code_path_config
-- ----------------------------

-- ----------------------------
-- Table structure for mysql_connection_config
-- ----------------------------
DROP TABLE IF EXISTS `mysql_connection_config`;
CREATE TABLE `mysql_connection_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库url',
  `port` int(5) NOT NULL COMMENT '数据库端口号',
  `data_source_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '数据库名称',
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库账号',
  `pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库密码',
  `coding` int(2) NOT NULL DEFAULT '0' COMMENT '数据库编码(0:utf-8;1:gbk)',
  `is_enabled` int(2) NOT NULL DEFAULT '1' COMMENT '是否启用(0:不是;1是)',
  `delete_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（0-否；1-是）',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人Id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of mysql_connection_config
-- ----------------------------
INSERT INTO `mysql_connection_config` VALUES ('1', '192.168.1.88', '3306', 'my_generator', 'liuhanxuan', 'LHX@123', '0', '0', '\0', '1', '2019-01-07 16:43:33', '1', '2019-03-19 01:47:59');


-- ----------------------------
-- Table structure for mysql_template
-- ----------------------------
DROP TABLE IF EXISTS `mysql_template`;
CREATE TABLE `mysql_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '模版名称',
  `content` text COLLATE utf8mb4_general_ci COMMENT '内容',
  `type` int(2) DEFAULT NULL COMMENT '1-entity模版（setter，getter）；2-entity模版（lombok）；3-getSet模版；4-service模版；5-serviceImpl模版；6-baseDao模版；7-dao模版（引用baseDao）；8-dao模版；9-xml模版',
  `is_enabled` int(2) NOT NULL DEFAULT '1' COMMENT '是否启用(0:不是;1是)',
  `delete_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（0-否；1-是）',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='模版表';

-- ----------------------------
-- Records of mysql_template
-- ----------------------------
INSERT INTO `mysql_template` VALUES ('1', '实体类模版(getter,setter)', 'package {package};\r\n{importMath}\r\n{importDate}\r\n\r\n\r\n/**\r\n * 此类为[基础代码]自动生成\r\n * @Author: {author}\r\n * @Date: {Time}\r\n * \r\n */\r\npublic class {EntityName} {\r\n\r\n{attr_list}\r\n\r\n{attr_getset_list}\r\n\r\n    \r\n}', '1', '1', '\0', '1', '2017-10-26 20:25:23', '1', '2017-10-26 20:25:33');
INSERT INTO `mysql_template` VALUES ('2', '实体类模版（lombok）', 'package {package};\r\nimport lombok.Data;\r\n{importDate}\r\n{importMath}\r\n\r\n\r\n/**\r\n * 此类为[基础代码]自动生成\r\n * @Author: {author}\r\n * @Date: {Time}\r\n * \r\n */\r\n@Data\r\npublic class {EntityName} {\r\n\r\n{attr_list}\r\n\r\n\r\n    \r\n}', '2', '1', '\0', '1', '2019-01-10 11:05:20', '1', '2019-01-10 11:05:24');
INSERT INTO `mysql_template` VALUES ('3', 'getter,setter模版', '	\r\n	/**\r\n     * 获取 {comment} 的值\r\n     * @return {JavaType}\r\n     */\r\n    public {JavaType} get{AttrName}() {\r\n        return {attrName};\r\n    }\r\n    \r\n    /**\r\n     * 设置 {comment} 的值\r\n     * @param {JavaType} {attrName}\r\n     */\r\n    public void set{AttrName}({JavaType} {attrName}) {\r\n        this.{attrName} = {attrName};\r\n    }\r\n	', '3', '1', '\0', '1', '2019-01-10 11:22:24', '1', '2019-01-10 11:22:29');
INSERT INTO `mysql_template` VALUES ('4', 'service模版', 'package {ServiceSuperPackage};\r\n\r\nimport {EntityPackage}.{EntityName};\r\n\r\nimport java.util.List;\r\n\r\n\r\n/**\r\n * 此类为[基础代码]自动生成,已经拥有基本的增删改成操作 \r\n * <其他请自行扩展>\r\n * @Author: {author}\r\n * @Date: {Time}\r\n * \r\n */\r\npublic interface {ServiceName}{\r\n 	\r\n	\r\n	/**\r\n     * 新增\r\n     * @param entity \r\n     */\r\n    int insertSelective ({EntityName} entity);\r\n    \r\n    /**\r\n     * 批量新增\r\n     * @param list \r\n     */\r\n    void insertBatch(List<{EntityName}> list);\r\n    \r\n    /**\r\n     * 根据主键修改单条数据\r\n     * @param entity \r\n     */\r\n    int updateByPrimaryKey({EntityName} entity);\r\n    \r\n    /**\r\n     * 根据主键批量修改数据\r\n     * @param list \r\n     */\r\n    void updateBatch(List<{EntityName}> list);\r\n    \r\n    /**\r\n     * 根据主键查询单条数据\r\n     * @param {PrimaryColumn}\r\n     */\r\n    {EntityName} selectByPrimaryKey({PrimaryJavaType} {PrimaryColumn});\r\n    \r\n    /**\r\n     * 根据条件查询单条记录\r\n     * @param entity \r\n     */\r\n    {EntityName} selectByEntity({EntityName} entity);\r\n    \r\n    /**\r\n     * 根据条件查询多条记录\r\n     * @param entity \r\n     */\r\n    List<{EntityName}> selectByEntityList({EntityName} entity);\r\n    \r\n    /**\r\n     * 根据条件查询Id\r\n     * @param entity \r\n     */\r\n    {PrimaryJavaType} selectById({EntityName} entity);\r\n    \r\n    /**\r\n     * 根据条件查询Ids\r\n     * @param entity \r\n     */\r\n    List<{PrimaryJavaType}> selectByIds({EntityName} entity);\r\n    \r\n    /**\r\n     * 根据主键删除单条记录\r\n     * @param {PrimaryColumn}\r\n     */\r\n    void deleteByPrimaryKey({PrimaryJavaType} {PrimaryColumn});\r\n    \r\n    /**\r\n     * 根据主键删除多条记录\r\n     * @param list\r\n     */\r\n    void deleteBatch(List<{PrimaryJavaType}> list);\r\n    \r\n    /**\r\n     * 根据某些条件删除\r\n     * @param entity\r\n     */\r\n    void deleteByEntity({EntityName} entity);\r\n    \r\n   \r\n}', '4', '1', '\0', '1', '2019-01-10 11:28:11', '1', '2019-01-10 11:28:18');
INSERT INTO `mysql_template` VALUES ('5', 'serviceImpl模版', 'package {ServiceImplSuperPackage};\r\n\r\nimport {ServiceSuperPackage}.{ServiceName};\r\nimport {EntityPackage}.{EntityName};\r\nimport {DaoSuperPackage}.{DaoName};\r\n\r\nimport org.springframework.stereotype.Service;\r\nimport org.springframework.beans.factory.annotation.Autowired;\r\n\r\nimport java.util.List;\r\n\r\n\r\n/**\r\n * 此类为[基础代码]自动生成,已经拥有基本的增删改成操作 \r\n * <其他请自行扩展>\r\n * @Author: {author}\r\n * @Date: {Time}\r\n * \r\n */\r\n@Service\r\npublic class {ServiceImplName} implements {ServiceName}{\r\n \r\n      \r\n    @Autowired\r\n	private {DaoName} {DaoAttr};\r\n	\r\n	\r\n	/**\r\n     * 新增\r\n     * @param entity \r\n     */\r\n    public int insertSelective ({EntityName} entity) { \r\n        return this.{DaoAttr}.insertSelective(entity);\r\n    }	\r\n    \r\n    /**\r\n     * 批量新增\r\n     * @param list \r\n     */\r\n    public void insertBatch(List<{EntityName}> list) {       \r\n        this.{DaoAttr}.insertBatch(list); \r\n    }\r\n    \r\n    /**\r\n     * 根据主键修改单条数据\r\n     * @param entity \r\n     */\r\n    public int updateByPrimaryKey({EntityName} entity) {\r\n        return this.{DaoAttr}.updateByPrimaryKey(entity);\r\n    }\r\n    \r\n    /**\r\n     * 根据主键批量修改数据\r\n     * @param list \r\n     */\r\n    public void updateBatch(List<{EntityName}> list) {      \r\n        this.{DaoAttr}.updateBatch(list);     \r\n    }\r\n    \r\n    /**\r\n     * 根据主键查询单条数据\r\n     * @param {PrimaryColumn}\r\n     */\r\n    public {EntityName} selectByPrimaryKey({PrimaryJavaType} {PrimaryColumn}) {\r\n        return this.{DaoAttr}.selectByPrimaryKey({PrimaryColumn});\r\n    }\r\n    \r\n    /**\r\n     * 根据条件查询单条记录\r\n     * @param entity \r\n     */\r\n    public {EntityName} selectByEntity({EntityName} entity) {\r\n        return this.{DaoAttr}.selectByEntity(entity);\r\n    }\r\n      \r\n    /**\r\n     * 根据条件查询多条记录\r\n     * @param entity \r\n     */\r\n    public List<{EntityName}> selectByEntityList({EntityName} entity) {\r\n        return this.{DaoAttr}.selectByEntityList(entity);\r\n    }\r\n    \r\n    /**\r\n     * 根据条件查询Id\r\n     * @param entity \r\n     */\r\n    public {PrimaryJavaType} selectById({EntityName} entity) {\r\n        return this.{DaoAttr}.selectById(entity);\r\n    }\r\n    \r\n    /**\r\n     * 根据条件查询Ids\r\n     * @param entity \r\n     */\r\n    public List<{PrimaryJavaType}> selectByIds({EntityName} entity) {\r\n        return this.{DaoAttr}.selectByIds(entity);\r\n    }\r\n    \r\n    /**\r\n     * 根据主键删除单条记录\r\n     * @param {PrimaryColumn}\r\n     */\r\n    public void deleteByPrimaryKey({PrimaryJavaType} {PrimaryColumn}) {\r\n        this.{DaoAttr}.deleteByPrimaryKey({PrimaryColumn});\r\n    }\r\n    \r\n    /**\r\n     * 根据主键删除多条记录\r\n     * @param list\r\n     */\r\n    public void deleteBatch(List<{PrimaryJavaType}> list) {\r\n        this.{DaoAttr}.deleteBatch(list);\r\n    }\r\n    \r\n    /**\r\n     * 根据某些条件删除\r\n     * @param entity\r\n     */\r\n    public void deleteByEntity({EntityName} entity) {\r\n        this.{DaoAttr}.deleteByEntity(entity);\r\n    }\r\n    \r\n   \r\n}', '5', '1', '\0', '1', '2019-01-10 11:42:30', '1', '2019-01-10 11:42:35');
INSERT INTO `mysql_template` VALUES ('6', 'baseDao模版', 'package {DaoSuperPackage};\r\n\r\nimport java.util.List;\r\n\r\n\r\n/**\r\n * 此类为[基础代码]自动生成\r\n * <其他请自行扩展>\r\n * @Author: {author}\r\n * @Date: {Time}\r\n */\r\npublic interface BaseDao<T,K>{\r\n\r\n    \r\n    /**\r\n     * 根据主键查询单一记录\r\n     * @param key\r\n     * @return T\r\n     */\r\n    <T> T selectByPrimaryKey(K key);\r\n    \r\n    /**\r\n     * 根据条件查询多条记录\r\n     * @param t\r\n     * @return List<T>\r\n     */\r\n	List<T> selectByEntityList(T t);\r\n    \r\n    /**\r\n     * 根据条件查询单条记录\r\n     * @param t\r\n     * @return T\r\n     */\r\n    T selectByEntity(T t);\r\n    \r\n    /**\r\n     * 根据条件查询ID\r\n     * @param t\r\n     * @return K\r\n     */\r\n    K selectById(T t);\r\n  \r\n    /**\r\n     * 根据条件查询ID集合\r\n     * @param t\r\n     * @return List<K>\r\n     */\r\n    List<K> selectByIds(T t);\r\n    \r\n    /**\r\n     * 插入单条记录（全字段） \r\n     * @param t \r\n     * @return int\r\n     */\r\n    int insert(T t);\r\n    \r\n    /**\r\n     * 插入单条记录（字段为空则不添加） \r\n     * @param t \r\n     * @return int\r\n     */\r\n    int insertSelective(T t);\r\n    \r\n    /**\r\n     * 根据主键修改\r\n     * @param t \r\n     * @return int\r\n     */\r\n    int updateByPrimaryKey(T t);\r\n    \r\n    /**\r\n     * 批量插入\r\n     * @param t\r\n     * @return int\r\n     */\r\n    int insertBatch(List<T> t);\r\n    \r\n    /**\r\n     * 根据主键批量修改\r\n     * @param t\r\n     * @return int\r\n     */\r\n    int updateBatch(List<T> t);\r\n	\r\n	/**\r\n     * 根据主键删除单条记录\r\n     * @param key\r\n     *\r\n     */\r\n    void deleteByPrimaryKey(K key);\r\n    \r\n    /**\r\n     * 根据主键批量删除\r\n     * @param list\r\n     * @return int\r\n     */\r\n    int deleteBatch(List<K> list);\r\n\r\n    /**\r\n     * 根据某些条件删除\r\n     * @param t\r\n     * @return int\r\n     */\r\n    int deleteByEntity(T t);\r\n     \r\n}', '6', '1', '\0', '1', '2019-01-10 13:13:29', '1', '2019-01-10 13:13:33');
INSERT INTO `mysql_template` VALUES ('7', 'dao模版（引用baseDao）', 'package {DaoSuperPackage};\r\n\r\nimport {DaoSuperPackage}.BaseDao;\r\nimport {EntityPackage}.{EntityName};\r\n\r\n\r\n/**\r\n * 此类为[基础代码]自动生成，继承了BaseDao类，已经拥有基本的增删改成操作\r\n * <其他请自行扩展>\r\n * @Author: {author}\r\n * @Date: {Time}\r\n */\r\npublic interface {DaoName} extends BaseDao<{EntityName},{PrimaryJavaType}>{\r\n    \r\n    //自行扩展\r\n    \r\n}', '7', '1', '\0', '1', '2019-01-10 13:20:44', '1', '2019-01-10 13:20:48');
INSERT INTO `mysql_template` VALUES ('8', 'dao模版', 'package {DaoSuperPackage};\r\n\r\nimport {EntityPackage}.{EntityName};\r\n\r\nimport java.util.List;\r\n\r\n/**\r\n * 此类为[基础代码]自动生成，已经拥有基本的增删改成操作\r\n * <其他请自行扩展>\r\n * @Author: {author}\r\n * @Date: {Time}\r\n */\r\npublic interface {DaoName}{\r\n    \r\n    /**\r\n     * 根据主键查询单一记录\r\n     * @param {PrimaryColumn}\r\n     * @return {EntityName}\r\n     */\r\n    {EntityName} selectByPrimaryKey({PrimaryJavaType} {PrimaryColumn});\r\n    \r\n    /**\r\n     * 根据条件查询多条记录\r\n     * @param entity\r\n     * @return List<{EntityName}>\r\n     */\r\n    List<{EntityName}> selectByEntityList({EntityName} entity);\r\n    \r\n    /**\r\n     * 根据条件查询单条记录\r\n     * @param entity\r\n     * @return {EntityName}\r\n     */\r\n    {EntityName} selectByEntity({EntityName} entity);\r\n    \r\n    /**\r\n     * 根据条件查询ID\r\n     * @param entity\r\n     * @return {PrimaryJavaType}\r\n     */\r\n    {PrimaryJavaType} selectById({EntityName} entity);\r\n  \r\n    /**\r\n     * 根据条件查询ID集合\r\n     * @param entity\r\n     * @return List<{PrimaryJavaType}>\r\n     */\r\n    List<{PrimaryJavaType}> selectByIds({EntityName} entity);\r\n    \r\n    /**\r\n     * 插入单条记录（全字段） \r\n     * @param entity \r\n     * @return int\r\n     */\r\n    int insert({EntityName} entity);\r\n    \r\n    /**\r\n     * 插入单条记录（字段为空则不添加） \r\n     * @param entity \r\n     * @return int\r\n     */\r\n    int insertSelective({EntityName} entity);\r\n    \r\n    /**\r\n     * 根据主键修改\r\n     * @param entity \r\n     * @return int\r\n     */\r\n    int updateByPrimaryKey({EntityName} entity);\r\n    \r\n    /**\r\n     * 批量插入\r\n     * @param entitys\r\n     * @return int\r\n     */\r\n    int insertBatch(List<{EntityName}> entitys);\r\n    \r\n    /**\r\n     * 根据主键批量修改\r\n     * @param entitys\r\n     * @return int\r\n     */\r\n    int updateBatch(List<{EntityName}> entitys);\r\n    \r\n	/**\r\n     * 根据主键删除单条记录\r\n     * @param {PrimaryColumn}\r\n     *\r\n     */\r\n    void deleteByPrimaryKey({PrimaryJavaType} {PrimaryColumn});\r\n	\r\n    /**\r\n     * 根据主键批量删除\r\n     * @param list\r\n     * @return int\r\n     */\r\n    int deleteBatch(List<{PrimaryJavaType}> list);\r\n\r\n    /**\r\n     * 根据某些条件删除\r\n     * @param entity\r\n     * @return int\r\n     */\r\n    int deleteByEntity({EntityName} entity);\r\n    \r\n}', '8', '1', '\0', '1', '2019-01-10 13:33:53', '1', '2019-01-10 13:33:58');
INSERT INTO `mysql_template` VALUES ('9', 'xml模版', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n<mapper namespace=\"{DaoSuperPackage}.{DaoName}\">\r\n\r\n    <!--返回模型-->\r\n    <resultMap id=\"BaseResultMap\" type=\"{EntityPackage}.{EntityName}\"> \r\n		{FeildMapList}\r\n    </resultMap>\r\n\r\n    <sql id=\"Base_Column_List\">\r\n        {FeildJoinId}\r\n    </sql>\r\n    \r\n    <!-- 根据主键查询 -->\r\n    <select id=\"selectByPrimaryKey\" parameterType=\"java.lang.{PrimaryJavaType}\" resultMap=\"BaseResultMap\">\r\n        select \r\n        <include refid=\"Base_Column_List\" />\r\n        from {TableName}\r\n        where {PrimaryKey} = #{{PrimaryColumn},jdbcType={PrimaryJdbcType}}\r\n    </select>\r\n	\r\n    <!-- 根据实体查询单条 -->\r\n    <select id=\"selectByEntity\" parameterType=\"{EntityPackage}.{EntityName}\" resultMap=\"BaseResultMap\">\r\n        select \r\n        <include refid=\"Base_Column_List\" />\r\n        from {TableName}\r\n        <where>\r\n{FeildIfList}\r\n		</where>\r\n        limit 1\r\n    </select>\r\n    \r\n    <!-- 根据实体查询多条 -->\r\n    <select id=\"selectByEntityList\" parameterType=\"{EntityPackage}.{EntityName}\" resultMap=\"BaseResultMap\">\r\n        select \r\n        <include refid=\"Base_Column_List\" />\r\n        from {TableName}\r\n        <where>\r\n{FeildIfList}\r\n		</where>\r\n    </select>\r\n    \r\n    <!-- 根据实体查询ID -->\r\n    <select id=\"selectById\" parameterType=\"{EntityPackage}.{EntityName}\" resultType=\"java.lang.{PrimaryJavaType}\">\r\n        select \r\n        {PrimaryKey}\r\n        from {TableName}\r\n        <where>\r\n{FeildIfList}\r\n		</where>\r\n        limit 1\r\n    </select>\r\n    \r\n    <!-- 根据实体查询IDs -->\r\n    <select id=\"selectByIds\" parameterType=\"{EntityPackage}.{EntityName}\" resultType=\"java.lang.{PrimaryJavaType}\">\r\n        select \r\n        {PrimaryKey}\r\n        from {TableName}\r\n        <where>\r\n{FeildIfList}\r\n		</where>\r\n    </select>\r\n    \r\n	<!--根据ids查询-->\r\n	<select id=\"selectEntityListByPrimaryKeys\" parameterType=\"java.util.List\" resultMap=\"BaseResultMap\">\r\n        SELECT  <include refid=\"Base_Column_List\" />\r\n        FROM {TableName} \r\n		WHERE {PrimaryKey} in\r\n        <foreach close=\")\" collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\">\r\n            #{item}\r\n        </foreach>\r\n    </select>\r\n	\r\n    <!-- 插入单条记录（全字段） -->\r\n    <insert id=\"insert\" parameterType=\"{EntityPackage}.{EntityName}\" {useGeneratedKeys}>\r\n        insert into {TableName} (\r\n			{FeildJoin}\r\n        )\r\n        values (\r\n			{FeildMapJoin}\r\n        )\r\n    </insert>\r\n	\r\n    <!-- 插入单条记录（字段为空则不添加） -->\r\n    <insert id=\"insertSelective\" parameterType=\"{EntityPackage}.{EntityName}\" {useGeneratedKeys}>\r\n        insert into {TableName}\r\n        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n{FeildIfJoin}\r\n        </trim>\r\n        <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\r\n{FeildIfMapJoin}\r\n        </trim>\r\n    </insert>\r\n    \r\n    <!-- 批量插入 -->\r\n    <insert id=\"insertBatch\" parameterType=\"java.util.List\" {useGeneratedKeys}>  \r\n        insert into {TableName} (\r\n            {FeildJoin}\r\n        )\r\n        values \r\n        <foreach collection=\"list\" index=\"index\" item=\"item\" separator=\",\">  \r\n        (\r\n			{ForeachFeildMapJoin}\r\n        )\r\n        </foreach>\r\n    </insert>\r\n	 \r\n	<!-- 根据主键修改 -->\r\n    <update id=\"updateByPrimaryKey\" parameterType=\"{EntityPackage}.{EntityName}\">\r\n        update {TableName}\r\n        <set>\r\n{FeildIfSetList}\r\n        </set>\r\n        where {PrimaryKey} = #{{PrimaryColumn},jdbcType={PrimaryJdbcType}}\r\n    </update>\r\n	  \r\n    <!-- 批量修改 -->\r\n    <update id=\"updateBatch\" parameterType=\"java.util.List\">  \r\n        <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"\" close=\"\" separator=\";\">     \r\n           update {TableName}\r\n        <set>\r\n{ForeachFeildIfSetList}\r\n        </set>\r\n        where {PrimaryKey} = #{item.{PrimaryColumn},jdbcType={PrimaryJdbcType}}\r\n        </foreach>\r\n    </update>\r\n	\r\n	<!-- 根据主键删除 -->\r\n    <delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang.{PrimaryJavaType}\">\r\n        delete from {TableName} \r\n		where {PrimaryKey} = #{{PrimaryColumn},jdbcType={PrimaryJdbcType}}\r\n    </delete>\r\n	\r\n    <!-- 根据主键批量删除 -->\r\n    <delete id=\"deleteBatch\" parameterType=\"java.util.List\"> \r\n        delete from {TableName} \r\n		where {PrimaryKey} in\r\n        <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" close=\")\" separator=\",\">  \r\n            #{item}  \r\n        </foreach> \r\n    </delete>\r\n \r\n	<!-- 根据某些条件删除 -->\r\n    <delete id=\"deleteByEntity\" parameterType=\"{EntityPackage}.{EntityName}\"> \r\n        delete from {TableName}\r\n        <where>\r\n{FeildIfList}\r\n		</where>\r\n    </delete>\r\n	\r\n	\r\n	\r\n</mapper>', '9', '1', '\0', '1', '2019-01-10 14:03:42', '1', '2019-01-10 14:03:48');

-- ----------------------------
-- Table structure for navigation
-- ----------------------------
DROP TABLE IF EXISTS `navigation`;
CREATE TABLE `navigation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `icon` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图标',
  `spread` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否扩展',
  `href` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '链接',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父节点ID',
  `delete_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态 0.未删除 1.已删除',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `rank` int(11) NOT NULL DEFAULT '0' COMMENT '排序优先级：值越低优先级越高',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_title` (`title`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='后台管理系统导航栏';

-- ----------------------------
-- Records of navigation
-- ----------------------------
INSERT INTO `navigation` VALUES ('1', '代码生成管理', '&#xe6c9;', '\0', '', '0', '\0', '1', '2018-12-15 15:06:51', '1', '2018-12-15 15:06:51', '1');
INSERT INTO `navigation` VALUES ('2', '代码生成', '&#xe635;', '\0', '/api/generator/init', '1', '\0', '1', '2018-12-17 18:23:21', '1', '2018-12-17 18:23:21', '1');
INSERT INTO `navigation` VALUES ('3', '数据库配置', '&#xe656;', '\0', '/api/database/init', '1', '\0', '1', '2018-12-17 19:49:29', '1', '2018-12-17 19:49:29', '2');
INSERT INTO `navigation` VALUES ('4', '系统中心', '&#xe716;', '\0', '', '0', '\0', '1', '2018-12-17 19:55:43', '1', '2018-12-17 19:55:43', '2');
INSERT INTO `navigation` VALUES ('5', '帐号管理', '&#xe770;', '\0', '/api/account/init', '4', '\0', '1', '2018-12-17 20:00:44', '1', '2018-12-17 20:00:44', '1');
INSERT INTO `navigation` VALUES ('6', '角色管理', '&#xe612;', '\0', '/api/role/init', '4', '\0', '1', '2018-12-17 20:03:23', '1', '2018-12-17 20:03:23', '2');
INSERT INTO `navigation` VALUES ('7', '菜单管理', '&#xe672;', '\0', '/api/menu/init', '4', '\0', '1', '2019-01-29 11:08:30', '1', '2019-01-29 11:08:30', '3');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_key` varchar(50) NOT NULL COMMENT '角色key 唯一',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `delete_flag` bit(1) DEFAULT b'0' COMMENT '状态 0.未删除 1.已删除',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人Id',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_key` (`role_key`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='b_role(用户角色)';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'super_admin', '超级管理员', '超级管理员，最高权限', '\0', '1', '1', '2018-12-15 15:00:23', '2018-12-15 15:00:23');
INSERT INTO `role` VALUES ('2', 'admin', '管理员', '管理员', '\0', '1', '1', '2019-01-18 17:30:31', '2019-01-18 17:30:31');
INSERT INTO `role` VALUES ('3', 'reporter', '访问者', '拥有生成代码基本权限', '\0', '1', '1', '2019-01-18 18:21:30', '2019-01-25 17:31:08');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_key` varchar(50) NOT NULL COMMENT '角色key',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  `delete_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态 0.未删除 1.已删除',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人Id',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='b_role_menu(用户角色菜单中间表)';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', 'admin', '1', '\0', '1', '1', '2018-12-17 16:07:31', '2018-12-17 16:07:31');
INSERT INTO `role_menu` VALUES ('2', 'admin', '2', '\0', '1', '1', '2019-01-18 17:32:41', '2019-01-18 17:32:41');
INSERT INTO `role_menu` VALUES ('3', 'admin', '3', '\0', '1', '1', '2019-01-18 17:32:49', '2019-01-18 17:32:49');
INSERT INTO `role_menu` VALUES ('4', 'admin', '4', '\0', '1', '1', '2019-01-18 17:33:21', '2019-01-18 17:33:21');
INSERT INTO `role_menu` VALUES ('5', 'admin', '5', '\0', '1', '1', '2019-01-18 17:33:41', '2019-01-18 17:33:41');
INSERT INTO `role_menu` VALUES ('6', 'reporter', '1', '\0', '1', '1', '2019-01-25 15:58:22', '2019-01-25 17:31:08');
INSERT INTO `role_menu` VALUES ('7', 'reporter', '2', '\0', '1', '1', '2019-01-25 15:58:22', '2019-01-25 17:31:08');
INSERT INTO `role_menu` VALUES ('8', 'reporter', '3', '\0', '1', '1', '2019-01-25 15:58:22', '2019-01-25 17:31:08');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '帐号',
  `pwd` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `name` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `status` bit(1) DEFAULT b'0' COMMENT '0-正常；1-失效',
  `head` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `role_key` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色key',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_unique` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'lhx', 'e8b7160b37a4d95924975480f43b0f5e', '刘汉轩', '\0', null, 'super_admin', '1', '2019-01-01 00:00:00', '1', '2019-01-22 14:42:13');
INSERT INTO `user` VALUES ('2', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', '\0', null, 'admin', '1', '2019-01-22 14:39:09', '2', '2019-01-22 14:44:07');
