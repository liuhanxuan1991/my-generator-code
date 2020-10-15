package com.lhx.study.mygeneratorcode.entity.po;

import lombok.Data;

import java.util.Date;

/**
 * 用户基础类
 */
@Data
public class User {

    private Integer id;

    private String account;

    private String pwd;

    private String name;

    private Boolean status;

    private String head;

    private String roleKey;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

}