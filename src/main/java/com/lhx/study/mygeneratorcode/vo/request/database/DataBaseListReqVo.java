package com.lhx.study.mygeneratorcode.vo.request.database;

import lombok.Data;

/**
 * @Author: lhx
 * @Date: 2018/12/18 15:57
 */
@Data
public class DataBaseListReqVo {

    private String name;//数据库名称

    private Integer status;//数据库状态

    private String startTime;//创建开始时间

    private String endTime;//创建结束时间

    private Integer page;//起始页

    private Integer limit;//每页大小

}
