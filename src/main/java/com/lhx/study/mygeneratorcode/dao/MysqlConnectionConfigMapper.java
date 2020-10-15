package com.lhx.study.mygeneratorcode.dao;


import com.lhx.study.mygeneratorcode.dao.base.BaseDao;
import com.lhx.study.mygeneratorcode.entity.po.MysqlConnectionConfig;
import com.lhx.study.mygeneratorcode.vo.response.database.DataBasesResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MysqlConnectionConfigMapper extends BaseDao<MysqlConnectionConfig, Integer> {

    /**
     * 分页查询数据库配置
     * @param flag 是否是管理员
     * @param userId 用户id
     * @param name 数据库名称
     * @param status 状态
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<MysqlConnectionConfig> dataBaseListPage(@Param("flag") boolean flag,@Param("userId")Integer userId,@Param("name")String name,@Param("status")Integer status,
                                             @Param("startTime")String startTime,@Param("endTime")String endTime);


    /**
     * 根据用户id获取可用配置
     * @param flag
     * @param userId
     * @return
     */
    List<DataBasesResVo> dataBases(@Param("flag") boolean flag,@Param("userId")Integer userId);


    /**
     * 根据id查询数据库配置详情
     * @param flag 是否是管理员
     * @param id 配置id
     * @param userId 用户id
     * @return
     */
    MysqlConnectionConfig queryDataBaseById(@Param("flag") boolean flag,@Param("id")Integer id,@Param("userId")Integer userId);


}
