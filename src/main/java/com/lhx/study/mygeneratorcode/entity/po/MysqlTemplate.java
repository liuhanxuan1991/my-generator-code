package com.lhx.study.mygeneratorcode.entity.po;

import lombok.Data;
import java.util.Date;

/**
 * 模版基础类
 */
@Data
public class MysqlTemplate {

    private Integer id;

    private String name;

    private String content;

    private Integer type;

    private Integer isEnabled;

    private Boolean deleteFlag;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

}
