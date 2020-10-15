package com.lhx.study.mygeneratorcode.service;

import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.dao.RoleMapper;
import com.lhx.study.mygeneratorcode.dao.RoleMenuMapper;
import com.lhx.study.mygeneratorcode.dao.UserMapper;
import com.lhx.study.mygeneratorcode.entity.bo.RoleMenuBo;
import com.lhx.study.mygeneratorcode.entity.po.Role;
import com.lhx.study.mygeneratorcode.entity.po.RoleMenu;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.utils.PropertiesUtil;
import com.lhx.study.mygeneratorcode.vo.response.role.RoleListResVo;
import com.lhx.study.mygeneratorcode.vo.response.role.RoleMenuResVo;
import com.lhx.study.mygeneratorcode.vo.response.role.RoleResVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: lhx
 * @Date: 2019/1/22 11:16
 */
@Service
public class RoleService {


    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;


    /**
     * 根据key获取角色名
     * @param roleKey
     * @return
     */
    public String selectNameByKey(String roleKey){
        return roleMapper.selectNameByKey(roleKey);
    }

    /**
     * 查询所有角色
     * @param user
     * @return
     */
    public List<RoleResVo> queryAll(User user){
        boolean flag = false;
        if (BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(user.getRoleKey())){
            flag = true;//管理员
        }
        List<Role> roles = roleMapper.qaueryAll(flag);
        if (CollectionUtils.isNotEmpty(roles)){
            return roles.stream().map(role -> {
                RoleResVo resVo = new RoleResVo();
                PropertiesUtil.copyProperties(role,resVo);
                return resVo;
            }).filter(role-> !BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(role.getRoleKey())).collect(Collectors.toList());
        }
        return null;
    }


    /**
     * 分页查询所有角色
     * @param user
     * @return
     */
    public List<RoleListResVo> queryRoleList(User user, String roleKey){
        boolean flag = false;
        if (BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(user.getRoleKey())){
            flag = true;//管理员
        }
        List<Role> roles = roleMapper.queryRoleList(flag,roleKey);
        if (CollectionUtils.isNotEmpty(roles)){
            return roles.stream().map(role -> {
                RoleListResVo resVo = new RoleListResVo();
                PropertiesUtil.copyProperties(role,resVo);
                resVo.setCreateUser(userMapper.selectNameById(role.getCreateBy()));
                resVo.setUpdateUser(userMapper.selectNameById(role.getUpdateBy()));
                return resVo;
            }).filter(role-> !BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(role.getRoleKey())).collect(Collectors.toList());
        }
        return null;
    }


    /**
     * 根据id查询详情
     * @param user
     * @param id
     * @return
     */
    public Role selectDetailById(User user,Integer id){
        boolean flag = false;
        if (BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(user.getRoleKey())){
            flag = true;//管理员
        }
       return roleMapper.selectDetailById(flag, id);
    }


    /**
     * 根据角色查询菜单
     * @param roleKey
     * @return
     */
    public List<RoleMenuResVo> queryMenuByRoleKey(String roleKey){
        List<RoleMenuResVo> roleMenuResVos = roleMenuMapper.queryMenuByRoleKey(roleKey);
        for (RoleMenuResVo roleMenuResVo : roleMenuResVos) {
            recursionDispose(roleMenuResVo);
        }
        return roleMenuResVos;
    }


    /**
     * 递归处理
     * @param roleMenuResVo
     */
    private void recursionDispose(RoleMenuResVo roleMenuResVo){
        roleMenuResVo.setChecked(false);
        roleMenuResVo.setOpen(true);
        List<RoleMenuResVo> children = roleMenuResVo.getChildren();
        if (CollectionUtils.isNotEmpty(children)){
            roleMenuResVo.setIsParent(true);
            for (RoleMenuResVo roleMenuRes : children) {
                recursionDispose(roleMenuRes);
            }
        }else {
            roleMenuResVo.setIsParent(false);
        }
    }


    /**
     * 根据角色查询菜单（编辑）
     * @param roleKey
     * @return
     */
    public List<RoleMenuResVo> queryForUpdate(String roleKey){
        List<RoleMenuResVo> resVoList = new ArrayList<>();
        List<RoleMenuBo> roleMenuBos = roleMenuMapper.queryForUpdate(roleKey);
        for (RoleMenuBo roleMenuBo : roleMenuBos) {
            queryForUpdateDispose(roleMenuBo,new RoleMenuResVo(),resVoList);
        }
        return resVoList;
    }

    /**
     * 递归处理角色查询菜单（编辑）
     * @param roleMenuBo
     * @param resVo
     * @param resVoList
     */
    private void queryForUpdateDispose(RoleMenuBo roleMenuBo,RoleMenuResVo resVo,List<RoleMenuResVo> resVoList){
        PropertiesUtil.copyProperties(roleMenuBo,resVo);
        resVo.setOpen(true);//全部打开
        if (StringUtils.isNotBlank(roleMenuBo.getRoleKey())){
            resVo.setChecked(true);
        }else {
            resVo.setChecked(false);
        }
        List<RoleMenuBo> childList = roleMenuBo.getChildList();
        if (CollectionUtils.isNotEmpty(childList)){
            resVo.setIsParent(true);
            List<RoleMenuResVo> list = new ArrayList<>();
            for (RoleMenuBo roleMenu : childList) {
                queryForUpdateDispose(roleMenu,new RoleMenuResVo(),list);
            }
            resVo.setChildren(list);
        }else {
            resVo.setIsParent(false);
        }
        resVoList.add(resVo);

    }


    /**
     * 修改角色权限
     * @param roleKey
     * @param ids
     * @return
     */
    @Transactional
    public Boolean updatePermission(String roleKey,List<Integer> ids,User user){
        Date date = new Date();
        //删除之前所有
        roleMenuMapper.deleteByRoleKey(roleKey);
        for (Integer id : ids) {
            roleMenuMapper.insertSelective(new RoleMenu(){{
                setMenuId(id);
                setRoleKey(roleKey);
                setDeleteFlag(false);
                setCreateBy(user.getId());
                setCreateTime(date);
                setUpdateBy(user.getId());
                setUpdateTime(date);
            }});
        }
        return true;

    }


    /**
     * 新增角色
     * @param role
     * @return
     */
    public Boolean addRole(Role role){
        return roleMapper.insertSelective(role) >0;
    }


    /**
     * 修改角色
     * @param role
     * @return
     */
    @Transactional
    public Boolean updateRole(Role role){
        Role selectRole = roleMapper.selectByPrimaryKey(role.getId());
        if (!role.getRoleKey().equals(selectRole.getRoleKey())){//rolekey有改动，修改对应信息
            //修改角色对应的账号
            List<User> users = userMapper.selectByEntityList(new User() {{
                setRoleKey(selectRole.getRoleKey());
            }});
            for (User user : users) {
                userMapper.updateByPrimaryKeySelective(new User(){{
                    setId(user.getId());
                    setRoleKey(role.getRoleKey());
                }});
            }

            //修改角色对应的权限
            List<RoleMenu> roleMenus = roleMenuMapper.selectByEntityList(new RoleMenu() {{
                setRoleKey(selectRole.getRoleKey());
            }});
            for (RoleMenu roleMenu : roleMenus) {
                roleMenuMapper.updateByPrimaryKeySelective(new RoleMenu(){{
                    setId(roleMenu.getId());
                    setRoleKey(role.getRoleKey());
                }});
            }
        }
        return roleMapper.updateByPrimaryKeySelective(role) > 0;
    }


    /**
     * 删除角色
     * @param id
     * @return
     */
    @Transactional
    public Boolean deleteRole(Integer id,Integer userId){
        Date date = new Date();
        Role role = roleMapper.selectByPrimaryKey(id);
        if (null != role){
            roleMapper.updateByPrimaryKeySelective(new Role(){{
                setId(id);
                setDeleteFlag(true);
                setUpdateBy(userId);
                setUpdateTime(date);
            }});
            roleMenuMapper.updateByRoleKey(new RoleMenu(){{
                setRoleKey(role.getRoleKey());
                setDeleteFlag(true);
                setUpdateBy(userId);
                setUpdateTime(date);
            }});
        }
        return true;
    }




}
