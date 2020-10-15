package com.lhx.study.mygeneratorcode.entity.po;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 菜单基础类
 */
@Data
public class Navigation{

    private Integer id;

    private String title;

    private String icon;

    private Boolean spread;

    private String href;

    private Integer parentId;

    private Boolean deleteFlag;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private Integer rank;

    private List<Navigation> children;


}