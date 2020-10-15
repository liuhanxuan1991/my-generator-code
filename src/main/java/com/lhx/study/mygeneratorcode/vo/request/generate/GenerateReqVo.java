package com.lhx.study.mygeneratorcode.vo.request.generate;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 生成代码请求
 * @Author: lhx
 * @Date: 2019/1/9 14:39
 */
@Data
public class GenerateReqVo {

    @NotNull(message = "未获取到数据库配置")
    private Integer connectionConfigId;//数据库配置id

    @NotEmpty(message = "未获取到选择的表")
    private List<String> tables;//表名集合

    private boolean baseDaoFlag;//生成baseDao标识

    private boolean lombokFlag;//使用lombok标识

    private boolean entitySwitchFlag;//生成实体标识

    private boolean serviceSwitchFlag;//生成service标识

    private boolean daoSwitchFlag;//生成dao标识

    private boolean mapperSwitchFlag;//生成mapper标识

    private String entityPath;//实体类路径

    private String servicePath;//service路径

    private String daoPath;//dao路径

//    private String mapperPath;//mapper路径


}
