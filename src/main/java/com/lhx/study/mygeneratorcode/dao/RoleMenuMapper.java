package com.lhx.study.mygeneratorcode.dao;

import com.lhx.study.mygeneratorcode.entity.bo.RoleMenuBo;
import com.lhx.study.mygeneratorcode.entity.po.RoleMenu;
import com.lhx.study.mygeneratorcode.vo.response.role.RoleMenuResVo;

import java.util.List;

public interface RoleMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);

    List<RoleMenu> selectByEntityList(RoleMenu record);

    /**
     * 根据角色查询菜单
     * @return
     */
    List<RoleMenuResVo> queryMenuByRoleKey(String roleKey);

    /**
     * 根据角色查询菜单(编辑)
     * @return
     */
    List<RoleMenuBo> queryForUpdate(String roleKey);


    /**
     * 根据角色删除
     * @param roleKey
     * @return
     */
    int deleteByRoleKey(String roleKey);

    /**
     * 根据角色修改
     * @param record
     * @return
     */
    int updateByRoleKey(RoleMenu record);

    /**
     * 根据菜单id修改
     * @param record
     * @return
     */
    int updateByMenuId(RoleMenu record);

}