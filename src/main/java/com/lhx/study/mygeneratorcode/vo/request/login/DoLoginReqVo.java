package com.lhx.study.mygeneratorcode.vo.request.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: lhx
 * @Date: 2018/12/14 13:28
 */
@Data
public class DoLoginReqVo {

    @NotBlank(message = "帐号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String pwd;

    @NotBlank(message = "验证码不能为空")
    private String code;


}

