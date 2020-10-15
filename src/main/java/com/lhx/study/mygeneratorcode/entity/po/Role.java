package com.lhx.study.mygeneratorcode.entity.po;

import lombok.Data;

import java.util.Date;

/**
 * 角色基础类
 */
@Data
public class Role {


    private Integer id;

    private String roleKey;

    private String roleName;

    private String remark;

    private Boolean deleteFlag;

    private Integer createBy;

    private Integer updateBy;

    private Date createTime;

    private Date updateTime;

}