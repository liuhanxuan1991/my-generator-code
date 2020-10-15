package com.lhx.study.mygeneratorcode.service;

import com.alibaba.fastjson.JSON;
import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.dao.NavigationMapper;
import com.lhx.study.mygeneratorcode.dao.RoleMenuMapper;
import com.lhx.study.mygeneratorcode.dao.UserMapper;
import com.lhx.study.mygeneratorcode.entity.po.Navigation;
import com.lhx.study.mygeneratorcode.entity.po.RoleMenu;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.utils.PropertiesUtil;
import com.lhx.study.mygeneratorcode.vo.response.perm.PermListResVo;
import com.lhx.study.mygeneratorcode.vo.response.perm.PermResVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: lhx
 * @Date: 2018/12/15 15:36
 */
@Slf4j
@Service
public class NavigationService {

    @Autowired
    private NavigationMapper navigationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;


    /**
     * 获取用户菜单
     * @param user
     * @return
     */
    public String getMenu(User user){
        try {
            boolean flag = false;
            if (BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(user.getRoleKey())
                   /* || BaseConstant.SYS_ROLE_KEY_ADMIN.equals(user.getRoleKey())*/){
                flag = true;//管理员
            }
            //根据用户信息获取菜单
            List<Navigation> navigations = navigationMapper.selectByRole(flag, user.getRoleKey());
            return JSON.toJSONString(navigations);
        } catch (Exception e) {
            log.error("获取用户菜单异常",e);
        }

        return "[]";

    }


    /**
     * 查询所有
     * @return
     */
    public List<PermResVo> queryAll(){
        List<Navigation> navigations = navigationMapper.selectAll();
        if (CollectionUtils.isNotEmpty(navigations)){
            return navigations.stream().map(navigation -> {
                PermResVo permResVo = new PermResVo();
                permResVo.setId(navigation.getId());
                permResVo.setTitle(navigation.getTitle());
                return permResVo;
            }).collect(Collectors.toList());
        }
        return null;
    }


    /**
     * 分页查询
     * @param id
     * @return
     */
    public List<PermListResVo> queryNavList(Integer id){
        List<PermListResVo> resVoList = new ArrayList<>();
        List<Navigation> navigations = navigationMapper.queryNavList(id);
        for (Navigation navigation : navigations) {
            PermListResVo resVo = new PermListResVo();
            PropertiesUtil.copyProperties(navigation,resVo);
            resVo.setCreateUser(userMapper.selectNameById(navigation.getCreateBy()));
            resVo.setUpdateUser(userMapper.selectNameById(navigation.getUpdateBy()));
            resVoList.add(resVo);
        }
        return resVoList;
    }


    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public Navigation selectDetailById(Integer id){
        return navigationMapper.selectByPrimaryKey(id);
    }


    /**
     * 新增菜单
     * @param navigation
     * @return
     */
    public Boolean addPerm(Navigation navigation){
        return navigationMapper.insertSelective(navigation)>0;
    }


    /**
     * 根据id修改
     * @param navigation
     * @return
     */
    public Boolean updateByPrimaryKeySelective(Navigation navigation){
        return navigationMapper.updateByPrimaryKeySelective(navigation)>0;
    }


    /**
     * 删除菜单
     * @param id
     * @param userId
     * @return
     */
    @Transactional
    public Boolean deletePerm(Integer id,Integer userId){
        Date date = new Date();
        Navigation navigation = navigationMapper.selectByPrimaryKey(id);
        if (null != navigation){
            navigationMapper.updateByPrimaryKeySelective(new Navigation(){{
                setDeleteFlag(true);
                setUpdateBy(userId);
                setUpdateTime(date);
                setId(id);
            }});
            roleMenuMapper.updateByMenuId(new RoleMenu(){{
                setMenuId(id);
                setDeleteFlag(true);
                setUpdateBy(userId);
                setUpdateTime(date);
            }});
        }
        return true;
    }

}
