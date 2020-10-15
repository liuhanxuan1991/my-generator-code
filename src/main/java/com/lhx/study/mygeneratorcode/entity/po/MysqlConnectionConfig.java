package com.lhx.study.mygeneratorcode.entity.po;


import lombok.Data;
import java.util.Date;


/**
 * 数据库连接配置基础类
 */
@Data
public class MysqlConnectionConfig {

    private Integer id;

    private String url;

    private Integer port;

    private String dataSourceName;

    private String account;

    private String pwd;

    private Integer coding;

    private Integer isEnabled;

    private Integer deleteFlag;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

}
