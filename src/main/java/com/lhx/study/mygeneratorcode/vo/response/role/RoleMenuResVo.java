package com.lhx.study.mygeneratorcode.vo.response.role;

import lombok.Data;

import java.util.List;

/**
 * @Author: lhx
 * @Date: 2019/1/24 11:01
 */
@Data
public class RoleMenuResVo {

    private Integer id;

    private String name;

    private Boolean open;

    private Integer parentId;

    private Boolean checked;

    private Boolean isParent;

    List<RoleMenuResVo> children;

}
