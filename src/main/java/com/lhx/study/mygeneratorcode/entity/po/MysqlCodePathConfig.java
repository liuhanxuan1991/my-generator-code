package com.lhx.study.mygeneratorcode.entity.po;

import lombok.Data;

import java.util.Date;
@Data
public class MysqlCodePathConfig {
    private Integer id;
    private String configName;
    private String entityPath;
    private String daoPath;
    private String mapperXmlPath;
    private String servicePath;
    private String controllerPath;
    private Integer userId;
    private Date createTime;
    private Date updateTime;

}
