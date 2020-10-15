package com.lhx.study.mygeneratorcode.vo.request.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author: lhx
 * @Date: 2019/1/18 14:08
 */
@Data
public class ModifyPassReqVo {

    @NotBlank(message = "旧密码不能为空")
    @Length(min = 6,max = 20,message = "请输入6-20位旧密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Length(min = 6,max = 20,message = "请输入6-20位新密码")
    private String newPassword;

    @NotBlank(message = "确认新密码不能为空")
    @Length(min = 6,max = 20,message = "请输入6-20位确认新密码")
    private String rePassword;

}
