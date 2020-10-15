package com.lhx.study.mygeneratorcode.dao;

import com.lhx.study.mygeneratorcode.entity.po.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 根据key获取角色名
     * @param roleKey
     * @return
     */
    String selectNameByKey(String roleKey);

    /**
     * 查询所有
     * @param flag
     * @return
     */
    List<Role> qaueryAll(@Param("flag") boolean flag);

    /**
     * 分页查询
     * @param flag
     * @param roleKey
     * @return
     */
    List<Role> queryRoleList(@Param("flag") boolean flag,@Param("roleKey")String roleKey);

    /**
     * 查询详情
     * @param flag
     * @param id
     * @return
     */
    Role selectDetailById(@Param("flag") boolean flag,@Param("id")Integer id);


}