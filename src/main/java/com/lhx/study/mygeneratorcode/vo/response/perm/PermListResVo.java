package com.lhx.study.mygeneratorcode.vo.response.perm;

import lombok.Data;

import java.util.Date;

/**
 * @Author: lhx
 * @Date: 2019/1/29 17:17
 */
@Data
public class PermListResVo {

    private Integer id;

    private String title;

    private String icon;

    private String href;

    private Integer parentId;

    private Integer rank;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

}
