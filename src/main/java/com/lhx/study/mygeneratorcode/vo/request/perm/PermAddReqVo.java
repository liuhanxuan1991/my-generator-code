package com.lhx.study.mygeneratorcode.vo.request.perm;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: lhx
 * @Date: 2019/1/30 13:41
 */
@Data
public class PermAddReqVo {


    private Integer id;

    @NotBlank(message = "名称不能为空")
    private String title;

    @NotBlank(message = "图标不能为空")
    private String icon;

    private String href;

    @NotNull(message = "父节点ID不能为空")
    private Integer parentId;

    @NotNull(message = "排序不能为空")
    private Integer rank;


}
