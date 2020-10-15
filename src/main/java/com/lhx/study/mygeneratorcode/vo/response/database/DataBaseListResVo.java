package com.lhx.study.mygeneratorcode.vo.response.database;

import lombok.Data;

import java.util.Date;

/**
 * @Author: lhx
 * @Date: 2018/12/18 14:46
 */
@Data
public class DataBaseListResVo {

    private Integer id;//id

    private String dataSourceName;//数据库名称

    private String url;//数据库url

    private Integer port;//数据库端口号

    private String account;//数据库帐号

    private String pwd;//数据库密码

    private Integer coding;//数据库编码

    private Integer isEnabled;//是否启用

    private String createUser;//创建人

    private Date createTime;//创建时间

    private String updateUser;//修改人

    private Date updateTime;//修改时间

}
