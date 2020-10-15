package com.lhx.study.mygeneratorcode.vo.request.account;

import lombok.Data;

/**
 * @Author: lhx
 * @Date: 2019/1/21 10:33
 */
@Data
public class AccountListReqVo {

    private String account;//账号或用户名

    private Boolean status;//状态

    private String startTime;//创建开始时间

    private String endTime;//创建结束时间

    private Integer page;//起始页

    private Integer limit;//每页大小

    private Boolean flag;//是否是管理员
}
