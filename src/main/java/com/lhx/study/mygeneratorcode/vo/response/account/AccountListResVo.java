package com.lhx.study.mygeneratorcode.vo.response.account;

import lombok.Data;

import java.util.Date;

/**
 * @Author: lhx
 * @Date: 2019/1/21 11:51
 */
@Data
public class AccountListResVo {

    private Integer id;//用户id

    private String account;//账号

    private String name;//用户名

    private String pwd;//密码

    private Boolean status;//状态

    private String head;//头像

    private String roleKey;//角色key

    private String roleName;//角色名

    private String createUser;//创建人

    private Date createTime;//创建时间

    private String updateUser;//修改人

    private Date updateTime;//修改时间

}
