package com.lhx.study.mygeneratorcode.dao;

import com.lhx.study.mygeneratorcode.entity.po.Navigation;
import com.lhx.study.mygeneratorcode.vo.response.perm.PermListResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NavigationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Navigation record);

    int insertSelective(Navigation record);

    Navigation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Navigation record);

    int updateByPrimaryKey(Navigation record);

    /**
     * 根据角色获取菜单
     * @param flag 是否是admin角色
     * @param roleKey 角色名
     * @return
     */
    List<Navigation> selectByRole(@Param("flag") boolean flag,@Param("roleKey") String roleKey);

    /**
     * 查询所有
     * @return
     */
    List<Navigation> selectAll();


    /**
     * 分页查询
     * @param id
     * @return
     */
    List<Navigation> queryNavList(@Param("id") Integer id);





}