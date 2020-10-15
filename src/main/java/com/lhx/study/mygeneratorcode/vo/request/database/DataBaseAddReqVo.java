package com.lhx.study.mygeneratorcode.vo.request.database;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增数据库配置
 * @Author: lhx
 * @Date: 2018/12/22 14:27
 */
@Data
public class DataBaseAddReqVo {

    private Integer id;

    @NotBlank(message = "数据库名称不能为空")
    private String dataSourceName;

    @NotBlank(message = "数据库url不能为空")
    private String url;

    @NotNull(message = "数据库端口不能为空")
    private Integer port;

    @NotBlank(message = "数据库账号不能为空")
    private String account;

    @NotBlank(message = "数据库密码不能为空")
    private String pwd;

    @NotNull(message = "编码不能为空")
    private Integer coding;

    @NotNull(message = "状态不能为空")
    private Integer isEnabled;



}
