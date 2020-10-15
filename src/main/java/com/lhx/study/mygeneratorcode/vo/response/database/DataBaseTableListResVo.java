package com.lhx.study.mygeneratorcode.vo.response.database;

import lombok.Data;

/**
 * @Author: lhx
 * @Date: 2019/1/7 14:55
 */
@Data
public class DataBaseTableListResVo {

    private Integer id;

    private String name;

    private Boolean open;

    private Integer parentId;

    private Boolean checked;

    private Boolean isParent;

    private Integer connectionConfigId;

}