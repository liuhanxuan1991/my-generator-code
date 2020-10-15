package com.lhx.study.mygeneratorcode.vo.request.account;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author: lhx
 * @Date: 2019/1/22 15:53
 */
@Data
public class AccountAddReqVo {

    private Integer id;

    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6,message = "密码长度6-20位")
//    @Length(min = 6,max = 20,message = "密码长度6-20位")
    private String pwd;

    @NotBlank(message = "用户名不能为空")
    private String name;

    private Boolean status;

    private String head;

    @NotBlank(message = "角色不能为空")
    private String roleKey;

}
