package com.lhx.study.mygeneratorcode.entity.bo;

import lombok.Data;

import java.util.List;

/**
 * @Author: lhx
 * @Date: 2019/1/25 10:47
 */
@Data
public class RoleMenuBo {

    private Integer id;

    private String name;

    private Integer parentId;

    private String roleKey;

    private List<RoleMenuBo> childList;

}
