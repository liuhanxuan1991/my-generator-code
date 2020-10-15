package com.lhx.study.mygeneratorcode.service;

import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.dao.MysqlConnectionConfigMapper;
import com.lhx.study.mygeneratorcode.dao.MysqlTemplateMapper;
import com.lhx.study.mygeneratorcode.entity.bo.MysqlDbColumn;
import com.lhx.study.mygeneratorcode.entity.po.MysqlConnectionConfig;
import com.lhx.study.mygeneratorcode.entity.po.MysqlTemplate;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.utils.*;
import com.lhx.study.mygeneratorcode.vo.request.generate.GenerateReqVo;
import com.lhx.study.mygeneratorcode.vo.response.database.DataBaseTableListResVo;
import com.lhx.study.mygeneratorcode.vo.response.database.MysqlDbColumnResVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GenerateService {


    @Value("${basePath}")
    private String basePath;

    @Autowired
    private MysqlTemplateMapper mysqlTemplateDao;

    @Autowired
    private MysqlConnectionConfigMapper mysqlConnectionConfigDao;

    private String directory = "code/";


    /**
     * 根据数据量配置id查询数据库表
     * @param id
     * @param user
     * @return
     */
    public List<DataBaseTableListResVo> queryTable(Integer id, User user){
        boolean flag = false;
        if (BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(user.getRoleKey())
                || BaseConstant.SYS_ROLE_KEY_ADMIN.equals(user.getRoleKey())){
            flag = true;//管理员
        }
        int tableId = 0;
        try {
            MysqlConnectionConfig connectionConfig = mysqlConnectionConfigDao.queryDataBaseById(flag, id, user.getId());
            Connection connection = DBConnectionUtil.getConnection(connectionConfig.getUrl(), connectionConfig.getPort() + "", connectionConfig.getDataSourceName(), connectionConfig.getCoding() == 0 ? "utf-8" : "gbk", connectionConfig.getAccount(), connectionConfig.getPwd());
            List<DataBaseTableListResVo> resVoList = new ArrayList<>();
            //定义一个根节点
            DataBaseTableListResVo dataBaseTableListResVo = new DataBaseTableListResVo();
            dataBaseTableListResVo.setId(tableId);
            dataBaseTableListResVo.setName(connectionConfig.getDataSourceName()+"数据库");
            dataBaseTableListResVo.setOpen(true);
            dataBaseTableListResVo.setChecked(false);
            dataBaseTableListResVo.setIsParent(true);
            dataBaseTableListResVo.setConnectionConfigId(id);
            resVoList.add(dataBaseTableListResVo);
            //获取所有表名
            List<String> dbTables = DbHelperUtil.getDbTable(connectionConfig.getDataSourceName(), connection);
            for (String dbTable : dbTables) {
                DataBaseTableListResVo table = new DataBaseTableListResVo();
                table.setId(++tableId);
                table.setName(dbTable);
                table.setOpen(false);
                table.setChecked(false);
                table.setIsParent(false);
                table.setParentId(dataBaseTableListResVo.getId());
                table.setConnectionConfigId(id);
                resVoList.add(table);
            }
            return resVoList;
        } catch (Exception e) {
           log.error("根据数据量配置id查询数据库表异常",e);
        }
        return null;
    }


    /**
     * 根据表名获取表字段信息
     * @param id
     * @param table
     * @return
     */
    public List<MysqlDbColumnResVo> queryTableColumns(Integer id, String table,User user){
        boolean flag = false;
        if (BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(user.getRoleKey())
                || BaseConstant.SYS_ROLE_KEY_ADMIN.equals(user.getRoleKey())){
            flag = true;//管理员
        }
        try {
            MysqlConnectionConfig connectionConfig = mysqlConnectionConfigDao.queryDataBaseById(flag, id, user.getId());
            Connection connection = DBConnectionUtil.getConnection(connectionConfig.getUrl(), connectionConfig.getPort() + "", connectionConfig.getDataSourceName(), connectionConfig.getCoding() == 0 ? "utf-8" : "gbk", connectionConfig.getAccount(), connectionConfig.getPwd());
            List<MysqlDbColumn> tableColumns = DbHelperUtil.getTableColumns(connectionConfig.getDataSourceName(), table, connection);
            if (CollectionUtils.isNotEmpty(tableColumns)){
                List<MysqlDbColumnResVo> resVoList = tableColumns.stream().map(x -> {
                    MysqlDbColumnResVo resVo = new MysqlDbColumnResVo();
                    PropertiesUtil.copyProperties(x, resVo);
                    return resVo;
                }).collect(Collectors.toList());
                return resVoList;
            }
        } catch (Exception e) {
            log.error("根据表名获取表字段信息异常",e);
        }
        return null;
    }


    /**
     * 生成代码
     * @param request
     * @param user
     * @return
     */
    public String generate(GenerateReqVo request, User user){
        boolean flag = false;
        String author = user.getName();
        log.info("用户:{},生成表:{}的基础代码",author, request.getTables());
        if (BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(user.getRoleKey())
                || BaseConstant.SYS_ROLE_KEY_ADMIN.equals(user.getRoleKey())){
            flag = true;//管理员
        }
        try {
            List<Path> paths = new ArrayList<>();

            String entityPath = request.getEntityPath();//实体类路径
            String servicePath = request.getServicePath();//service接口路径
            String daoPath = request.getDaoPath();//dao接口路径

            MysqlConnectionConfig connectionConfig = mysqlConnectionConfigDao.queryDataBaseById(flag, request.getConnectionConfigId(), user.getId());
            Connection connection = DBConnectionUtil.getConnection(connectionConfig.getUrl(), connectionConfig.getPort() + "", connectionConfig.getDataSourceName(), connectionConfig.getCoding() == 0 ? "utf-8" : "gbk", connectionConfig.getAccount(), connectionConfig.getPwd());
            MysqlTemplate entity = new MysqlTemplate();
            entity.setIsEnabled(BaseConstant.IS_ENABLED_YES);
            entity.setDeleteFlag(false);
            for (String table : request.getTables()){
                //表名转成实体名
                String entityName = MySqlTemplateUtil.tableToEntity(table);
                //表字段集合
                List<MysqlDbColumn> tableColumns = DbHelperUtil.getTableColumns(connectionConfig.getDataSourceName(), table, connection);
                if (CollectionUtils.isNotEmpty(tableColumns)){

                    //获取主键
                    MysqlDbColumn primaryColumn = tableColumns.stream().filter(column -> "PRI".equalsIgnoreCase(column.getColumnKey())).findFirst().orElse(tableColumns.get(0));

                    if (request.isEntitySwitchFlag()){//生成实体
                        String entityContent = "";
                        if (request.isLombokFlag()){//使用lombok
                            entity.setType(BaseConstant.MYSQL_TEMPLATE_TYPE_ENTITY_LOMBOK);
                            MysqlTemplate lombokTemplate = mysqlTemplateDao.selectByEntity(entity);
                            //生成实体类
                            entityContent = MySqlTemplateUtil.createEntityLombok(lombokTemplate.getContent(), author, entityPath, entityName, tableColumns);
                        }else{//默认get，set
                            entity.setType(BaseConstant.MYSQL_TEMPLATE_TYPE_ENTITY_GETSET);
                            MysqlTemplate entityGetsetTemplate = mysqlTemplateDao.selectByEntity(entity);
                            entity.setType(BaseConstant.MYSQL_TEMPLATE_TYPE_GETSET);
                            MysqlTemplate getsetTemplate = mysqlTemplateDao.selectByEntity(entity);
                            //生成实体类
                            entityContent = MySqlTemplateUtil.createEntityGetSet(entityGetsetTemplate.getContent(), getsetTemplate.getContent(), author, entityPath, entityName, tableColumns);
                        }
                        //创建文件
                        paths.add(FileToolUtil.createFile(basePath + directory + entityPath,entityName+".java",entityContent));

                    }
                    if(request.isServiceSwitchFlag()){//生成service以及serviceImpl
                        entity.setType(BaseConstant.MYSQL_TEMPLATE_TYPE_SERVICE);
                        MysqlTemplate serviceTemplate = mysqlTemplateDao.selectByEntity(entity);
                        entity.setType(BaseConstant.MYSQL_TEMPLATE_TYPE_SERVICEIMPL);
                        MysqlTemplate serviceTemplateImpl = mysqlTemplateDao.selectByEntity(entity);
                        //生成service接口
                        String serviceContent = MySqlTemplateUtil.createService(serviceTemplate.getContent(), author, servicePath, entityPath, entityName, primaryColumn);
                        //创建文件
                        paths.add(FileToolUtil.createFile(basePath + directory + servicePath,entityName+"Service.java",serviceContent));

                        //生成service实现类
                        String serviceImplContent = MySqlTemplateUtil.createServiceImpl(serviceTemplateImpl.getContent(), author, servicePath, daoPath, entityPath, entityName, primaryColumn);
                        //创建文件
                        paths.add(FileToolUtil.createFile(basePath + directory + servicePath +"." +BaseConstant.PACKAGE_DEFAULT_NAME_IMPL,entityName+"ServiceImpl.java",serviceImplContent));

                    }
                    if (request.isDaoSwitchFlag()){//生成dao
                        if (request.isBaseDaoFlag()){//使用baseDao
                            entity.setType(BaseConstant.MYSQL_TEMPLATE_TYPE_BASEDAO);
                            MysqlTemplate basedaoTemplate = mysqlTemplateDao.selectByEntity(entity);
                            entity.setType(BaseConstant.MYSQL_TEMPLATE_TYPE_DAO_BASEDAO);
                            MysqlTemplate daoTemplate = mysqlTemplateDao.selectByEntity(entity);
                            //生成baseDao模版接口
                            String basedaoTemplateContent = MySqlTemplateUtil.createBasedaoTemplate(basedaoTemplate.getContent(), author, daoPath);
                            //创建文件
                            paths.add(FileToolUtil.createFile(basePath + directory + daoPath,"BaseDao.java",basedaoTemplateContent));

                            //生成baseDao,引用baseDao
                            String daoQuoteTemplateContent = MySqlTemplateUtil.createDaoQuoteTemplate(daoTemplate.getContent(), author, daoPath, entityPath, entityName, primaryColumn);
                            //创建文件
                            paths.add(FileToolUtil.createFile(basePath + directory + daoPath,entityName+"Mapper.java",daoQuoteTemplateContent));

                        }else {//不使用baseDao
                            entity.setType(BaseConstant.MYSQL_TEMPLATE_TYPE_DAO);
                            MysqlTemplate daoTemplate = mysqlTemplateDao.selectByEntity(entity);
                            //生成baseDao
                            String daoContent = MySqlTemplateUtil.createDao(daoTemplate.getContent(), author, daoPath, entityPath, entityName, primaryColumn);
                            //创建文件
                            paths.add(FileToolUtil.createFile(basePath + directory + daoPath,entityName+"Mapper.java",daoContent));

                        }
                    }
                    //生成xml
                    if (request.isMapperSwitchFlag() && request.isDaoSwitchFlag() && request.isEntitySwitchFlag()){
                        entity.setType(BaseConstant.MYSQL_TEMPLATE_TYPE_XML);
                        MysqlTemplate xmlTemplate = mysqlTemplateDao.selectByEntity(entity);
                        List<MysqlDbColumn> dbColumns = tableColumns.stream().filter(column -> !"PRI".equalsIgnoreCase(column.getColumnKey())).collect(Collectors.toList());
                        String xmlContent = MySqlTemplateUtil.createXml(xmlTemplate.getContent(), daoPath, entityPath, entityName, table, primaryColumn, dbColumns);
                        //创建文件
                        paths.add(FileToolUtil.createFile(basePath + directory + entityPath,entityName+"Mapper.xml",xmlContent));

                    }

                }
            }
            //生成zip压缩文件
            String zipName = connectionConfig.getDataSourceName()+"基础代码" + (new Date()).getTime();
            FileToolUtil.zipFiles(paths, basePath + "/zip/", zipName);
            //已生成zip文件，删除其他文件

            return zipName+".zip";
        } catch (Exception e) {
            log.error("生成代码异常",e);
        }
        return null;
    }











//    public String generateOne(Integer userId, String tableName, GenerateBo generateBo, MysqlConnectionConfig connectionConfig) {
//        Connection connection = DBConnectionUtil.getConnection(connectionConfig.getUrl(), connectionConfig.getPort() + "", connectionConfig.getDataSourceName(), connectionConfig.getCoding() == 0 ? "utf-8" : "gbk", connectionConfig.getAccount(), connectionConfig.getPwd());
//
//        try {
//            List<MysqlDbColumn> mysqlDbColumnList = DbHelperUtil.getTableColumns(connectionConfig.getDataSourceName(), tableName, connection);
//            MysqlTemplate template = new MysqlTemplate();
//            template.setIsEnabled(1);
//            template.setType(1);
//            template = (MysqlTemplate)this.mysqlTemplateDao.selectByEntity(template);
//            MysqlTemplate getSetTemplate = new MysqlTemplate();
//            getSetTemplate.setIsEnabled(1);
//            getSetTemplate.setType(6);
//            getSetTemplate = (MysqlTemplate)this.mysqlTemplateDao.selectByEntity(getSetTemplate);
//            CreAttrBo attrBo = new CreAttrBo();
//            attrBo.setTableName(tableName);
//            attrBo.setEntityPackage(generateBo.getEntityPath() + "." + generateBo.getEntityName());
//            attrBo.setDaoPackage(generateBo.getDaoPath() + "." + generateBo.getEntityName() + "Mapper");
//            this.createBaseArgument((String)null);
//            String pojoContent = this.createEntity(template.getContent(), getSetTemplate.getContent(), generateBo.getEntityPath(), generateBo.getEntityName(), mysqlDbColumnList);
//            MysqlDbColumn dbColumn = null;
//            String serviceContent = "";
//            String baseDaoContent = "";
//            String daoContent = "";
//            String mapperContent = "";
//            if (generateBo.getDaoFalg()) {
//                template = new MysqlTemplate();
//                template.setIsEnabled(1);
//                template.setType(7);
//                template = (MysqlTemplate)this.mysqlTemplateDao.selectByEntity(template);
//                baseDaoContent = this.createDao(template.getContent(), generateBo.getDaoPath());
//                template = new MysqlTemplate();
//                template.setIsEnabled(1);
//                template.setType(2);
//                template = (MysqlTemplate)this.mysqlTemplateDao.selectByEntity(template);
//                dbColumn = CreateUtil.getPrimaryColumn(mysqlDbColumnList);
//                daoContent = this.createDaoMapper(template.getContent(), generateBo.getDaoPath(), generateBo.getEntityPath(), generateBo.getEntityName(), generateBo.getEntityName() + "Mapper", null != dbColumn ? ToolUtil.getJavaType(dbColumn.getDataType()) : "");
//            }
//
//            if (generateBo.getServiceFalg()) {
//                template = new MysqlTemplate();
//                template.setIsEnabled(1);
//                template.setType(5);
//                template = (MysqlTemplate)this.mysqlTemplateDao.selectByEntity(template);
//                serviceContent = this.createService(template.getContent(), generateBo.getServicePath(), attrBo.getDaoPackage(), generateBo.getEntityName() + "Mapper", generateBo.getEntityName(), attrBo.getEntityPackage(), null != dbColumn ? ToolUtil.getJavaType(dbColumn.getDataType()) : "", null != dbColumn ? dbColumn.getColumnName() : "");
//            }
//
//            if (generateBo.getMappFalg()) {
//                template = new MysqlTemplate();
//                template.setIsEnabled(1);
//                template.setType(3);
//                template = (MysqlTemplate)this.mysqlTemplateDao.selectByEntity(template);
//                mapperContent = createMapper(template.getContent(), attrBo, mysqlDbColumnList, dbColumn);
//            }
//
//            List<Path> pathList = new ArrayList();
//            pathList.add(FileToolUtil.createFile(this.basePath + generateBo.getEntityPath(), generateBo.getEntityName() + ".java", pojoContent));
//            if (StringUtils.isNotBlank(serviceContent)) {
//                pathList.add(FileToolUtil.createFile(this.basePath + generateBo.getServicePath(), generateBo.getEntityName() + "Service.java", serviceContent));
//            }
//
//            if (StringUtils.isNotBlank(baseDaoContent)) {
//                pathList.add(FileToolUtil.createFile(this.basePath + generateBo.getDaoPath(), "BaseDao.java", baseDaoContent));
//            }
//
//            if (StringUtils.isNotBlank(daoContent)) {
//                pathList.add(FileToolUtil.createFile(this.basePath + generateBo.getDaoPath(), generateBo.getEntityName() + "Mapper.java", daoContent));
//            }
//
//            if (StringUtils.isNotBlank(mapperContent)) {
//                pathList.add(FileToolUtil.createFile(this.basePath + generateBo.getMapperPath(), generateBo.getEntityName() + "Mapper.xml", mapperContent));
//            }
//
//            String zipName = connectionConfig.getDataSourceName() + (new Date()).getTime();
//            FileToolUtil.zipFiles(pathList, this.basePath + "\\zip\\", zipName);
//            return "/zip/" + zipName + ".zip";
//        } catch (Exception var18) {
//            var18.printStackTrace();
//            return null;
//        }
//    }
//
//    public String createBaseArgument(String content) {
//        String result = "出入参对象类生成完成：";
//        return result;
//    }
//
//    public String createEntity(String content, String getSetTemplContent, String entityPath, String entityName, List<MysqlDbColumn> mysqlDbColumnList) {
//        content = ToolUtil.parseTemplate(content, "package", entityPath);
//        boolean hasDate = false;
//        boolean hasTimestamp = false;
//        content = ToolUtil.parseTemplate(content, "attr_list", CreateUtil.createAttrList(hasDate, mysqlDbColumnList));
//        content = ToolUtil.parseTemplate(content, "importDate", "import java.util.Date");
//        content = ToolUtil.parseTemplate(content, "importBigDecimal", "import java.math.BigDecimal;");
//        content = ToolUtil.parseTemplate(content, "Time", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//        content = ToolUtil.parseTemplate(content, "EntityName", entityName);
//        content = ToolUtil.parseTemplate(content, "attr_getset_list", CreateUtil.createAttrGetsetList(mysqlDbColumnList, getSetTemplContent, entityName));
//        content = content.replace("attr_tostring_list", "");
//        return content;
//    }
//
//    public String createService(String content, String servicePath, String daoPackagePath, String daoName, String entityName, String entityPackage, String PrimaryJavaType, String primaryColumn) {
//        try {
//            content = ToolUtil.parseTemplate(content, "ServiceSuperPackage", ToolUtil.getSuperPackage(servicePath));
//            content = ToolUtil.parseTemplate(content, "DaoSuperPackage", daoPackagePath);
//            content = ToolUtil.parseTemplate(content, "DaoName", daoName);
//            content = ToolUtil.parseTemplate(content, "DaoAttr", CommonUtils.replaceName(entityName) + "Dao");
//            content = ToolUtil.parseTemplate(content, "EntityPackage", entityPackage);
//            content = ToolUtil.parseTemplate(content, "EntityName", entityName);
//            content = ToolUtil.parseTemplate(content, "ServiceName", entityName + "Service");
//            content = ToolUtil.parseTemplate(content, "Time", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
//            content = ToolUtil.parseTemplate(content, "PrimaryJavaType", PrimaryJavaType);
//            content = ToolUtil.parseTemplate(content, "PrimaryColumn", primaryColumn);
//        } catch (Exception var10) {
//            ;
//        }
//
//        return content;
//    }
//
//    public String createDao(String content, String daoPath) {
//        String var3 = "BaseDao类生成完成：";
//
//        try {
//            content = ToolUtil.parseTemplate(content, "DaoSuperPackage", ToolUtil.getSuperPackage(daoPath));
//        } catch (Exception var5) {
//            var3 = "BaseDao接口生成失败：" + var5.getMessage();
//        }
//
//        return content;
//    }
//
//    public String createDaoMapper(String content, String daoPath, String entityPath, String entityName, String daoName, String primaryJavaType) {
//        String var7 = "";
//
//        try {
//            content = ToolUtil.parseTemplate(content, "DaoSuperPackage", ToolUtil.getSuperPackage(daoPath));
//            content = ToolUtil.parseTemplate(content, "EntityPackage", entityPath);
//            content = ToolUtil.parseTemplate(content, "DaoClassName", daoName);
//            content = ToolUtil.parseTemplate(content, "Time", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
//            content = ToolUtil.parseTemplate(content, "EntityName", entityName);
//            content = ToolUtil.parseTemplate(content, "PrimaryJavaType", primaryJavaType);
//        } catch (Exception var9) {
//            var7 = "类生成失败：" + var9.getMessage();
//        }
//
//        return content;
//    }
//
//    public static String createMapper(String content, CreAttrBo ca, List<MysqlDbColumn> mysqlDbColumnList, MysqlDbColumn dbColumn) {
//        String var4 = "";
//
//        try {
//            content = ToolUtil.parseTemplate(content, "MapperPackage", ca.getDaoPackage());
//            content = ToolUtil.parseTemplate(content, "EntityPackage", ca.getEntityPackage());
//            content = ToolUtil.parseTemplate(content, "PrimaryKey", null != dbColumn ? dbColumn.getColumnName() : "");
//            content = ToolUtil.parseTemplate(content, "PrimaryJdbcType", null != dbColumn ? (String) BaseConstant.JDBC_TYPE_MAP.get(dbColumn.getDataType()) : "");
//            content = ToolUtil.parseTemplate(content, "PrimaryJavaType", null != dbColumn ? ToolUtil.getJavaType(dbColumn.getDataType()) : "");
//            content = ToolUtil.parseTemplate(content, "PrimaryFeild", null != dbColumn ? CommonUtils.firstCharToLowerCase(ToolUtil.tableToEntity(dbColumn.getColumnName())) : "");
//            content = ToolUtil.parseTemplate(content, "FeildMapList", ToolUtil.getFeildMapList(mysqlDbColumnList));
//            content = ToolUtil.parseTemplate(content, "FeildJoinId", ToolUtil.getFeildJoinId(mysqlDbColumnList));
//            content = ToolUtil.parseTemplate(content, "TableName", ca.getTableName());
//            content = ToolUtil.parseTemplate(content, "FeildIfList", ToolUtil.getFeildIfList(mysqlDbColumnList));
//            content = ToolUtil.parseTemplate(content, "FeildJoin", ToolUtil.getFeildJoin(mysqlDbColumnList));
//            content = ToolUtil.parseTemplate(content, "FeildMapJoin", ToolUtil.getFeildMapJoin(mysqlDbColumnList));
//            content = ToolUtil.parseTemplate(content, "FeildIfJoin", ToolUtil.getFeildIfJoin(mysqlDbColumnList));
//            content = ToolUtil.parseTemplate(content, "FeildIfMapJoin", ToolUtil.getFeildIfMapJoin(mysqlDbColumnList));
//            content = ToolUtil.parseTemplate(content, "FeildIfSetList", ToolUtil.getFeildIfSetList(mysqlDbColumnList));
//            content = ToolUtil.parseTemplate(content, "FeildSetList", ToolUtil.getFeildSetList(mysqlDbColumnList));
//            content = ToolUtil.parseTemplate(content, "ForeachFeildMapJoin", ToolUtil.getForeachFeildMapJoin(mysqlDbColumnList));
//            content = ToolUtil.parseTemplate(content, "UpdateBatchFeildSetList", ToolUtil.getUpdateBatchFeildSetList(mysqlDbColumnList));
//        } catch (Exception var6) {
//            var4 = ".xml生成失败：" + var6.getMessage();
//        }
//
//        return content;
//    }
}
