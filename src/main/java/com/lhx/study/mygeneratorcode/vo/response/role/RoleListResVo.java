package com.lhx.study.mygeneratorcode.vo.response.role;

import lombok.Data;

import java.util.Date;

/**
 * @Author: lhx
 * @Date: 2019/1/23 14:28
 */
@Data
public class RoleListResVo {

    private Integer id;

    private String roleKey;

    private String roleName;

    private String remark;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

}
