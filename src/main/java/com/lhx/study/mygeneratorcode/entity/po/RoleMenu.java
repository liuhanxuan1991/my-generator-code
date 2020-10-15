package com.lhx.study.mygeneratorcode.entity.po;

import lombok.Data;
import java.util.Date;

/**
 * 角色菜单基础类
 */
@Data
public class RoleMenu {

    private Integer id;

    private String roleKey;

    private Integer menuId;

    private Boolean deleteFlag;

    private Integer createBy;

    private Integer updateBy;

    private Date createTime;

    private Date updateTime;

}