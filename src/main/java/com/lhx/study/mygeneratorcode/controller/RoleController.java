package com.lhx.study.mygeneratorcode.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.constant.StatusEnum;
import com.lhx.study.mygeneratorcode.entity.po.Role;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.service.RoleService;
import com.lhx.study.mygeneratorcode.service.UserService;
import com.lhx.study.mygeneratorcode.utils.PropertiesUtil;
import com.lhx.study.mygeneratorcode.vo.ReturnResult;
import com.lhx.study.mygeneratorcode.vo.response.role.RoleDetailResVo;
import com.lhx.study.mygeneratorcode.vo.response.role.RoleListResVo;
import com.lhx.study.mygeneratorcode.vo.response.role.RoleMenuResVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 角色管理
 * @Author: lhx
 * @Date: 2019/1/22 19:31
 */
@Slf4j
@Controller
@RequestMapping("/api/role/")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    /**
     * 初始化页面
     * @return
     */
    @GetMapping("init")
    public ModelAndView init(HttpSession session){
        ModelAndView mav = new ModelAndView("/role/list");
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        mav.addObject("account", user);
        mav.addObject("roleList",roleService.queryAll(user));
        return mav;
    }


    /**
     * 角色分页查询
     * @param roleKey
     * @param session
     * @return
     */
    @PostMapping("roleList")
    @ResponseBody
    public ReturnResult<List<RoleListResVo>> roleList(@RequestParam(value = "roleKey",required = false)String roleKey,
                                                      @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                      @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                                      HttpSession session){
        ReturnResult<List<RoleListResVo>> result = new ReturnResult<>();
        Page<List<RoleListResVo>> startPage = PageHelper.startPage(page, limit);
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        List<RoleListResVo> resultList = roleService.queryRoleList(user,roleKey);
        result.setCount(startPage.getTotal());
        result.setData(resultList);
        return result;
    }


    /**
     * 根据id查询
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("queryDetailById")
    public ModelAndView queryDetailById(@RequestParam("id")Integer id,
                                        @RequestParam(value = "type",required = false,defaultValue = "1")Integer type,
                                        HttpSession session){
        ModelAndView mav = new ModelAndView("/role/detail");
        if(2==type){
            mav.setViewName("/role/update");
        }
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        Role role = roleService.selectDetailById(user, id);

        RoleDetailResVo detail = new RoleDetailResVo();
        PropertiesUtil.copyProperties(role, detail);
        detail.setCreateUser(userService.selectNameById(role.getCreateBy()));
        detail.setUpdateUser(userService.selectNameById(role.getUpdateBy()));
        mav.addObject("detail",detail);
        return mav;
    }

    /**
     * 新增页跳转
     * @return
     */
    @RequestMapping("addInit")
    public ModelAndView addInit(HttpSession session){
        ModelAndView mav = new ModelAndView("/role/add");
        return mav;
    }


    /**
     * 新增角色
     * @param roleKey
     * @param roleName
     * @param remark
     * @param session
     * @return
     */
    @RequestMapping("addRole")
    @ResponseBody
    public ReturnResult addRole(@RequestParam("roleKey")String roleKey,
                                @RequestParam("roleName")String roleName,
                                @RequestParam(value = "remark",required = false)String remark,
                                HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        Date date = new Date();
        try {
            if (roleService.addRole(new Role(){{
                setRoleKey(roleKey);
                setRoleName(roleName);
                setRemark(remark);
                setDeleteFlag(false);
                setCreateBy(user.getId());
                setCreateTime(date);
                setUpdateBy(user.getId());
                setUpdateTime(date);
            }})){
                return returnResult;
            }
        } catch (DataIntegrityViolationException e) {
            log.error("新增角色异常",e);
            returnResult.setCode(StatusEnum.OP_ERROR.getCode());
            returnResult.setMsg("角色key已存在");
            return returnResult;
        }
        return new ReturnResult(StatusEnum.OP_ERROR);
    }


    /**
     * 修改角色信息
     * @param id
     * @param roleKey
     * @param roleName
     * @param remark
     * @param session
     * @return
     */
    @RequestMapping("updateRole")
    @ResponseBody
    public ReturnResult updateRole(@RequestParam("id")Integer id,
                                   @RequestParam("roleKey")String roleKey,
                                   @RequestParam("roleName")String roleName,
                                   @RequestParam(value = "remark",required = false)String remark,
                                   HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        Date date = new Date();
        try {
            if (roleService.updateRole(new Role(){{
                setId(id);
                setRoleKey(roleKey);
                setRoleName(roleName);
                setRemark(remark);
                setUpdateBy(user.getId());
                setUpdateTime(date);
            }})){
                return returnResult;
            }
        } catch (DataIntegrityViolationException e) {
            log.error("修改角色异常",e);
            returnResult.setCode(StatusEnum.OP_ERROR.getCode());
            returnResult.setMsg("角色key已存在");
            return returnResult;
        }
        return new ReturnResult(StatusEnum.OP_ERROR);
    }


    /**
     * 删除角色
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("deleteRole")
    @ResponseBody
    public ReturnResult deleteRole(@RequestParam("id")Integer id, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        if (roleService.deleteRole(id,user.getId())){
            return returnResult;
        }
        return new ReturnResult(StatusEnum.OP_ERROR);
    }


    /**
     * 查询角色拥有的权限
     * @param roleKey
     * @return
     */
    @RequestMapping("queryPermission")
    @ResponseBody
    public List<RoleMenuResVo> queryPermission(@RequestParam("roleKey")String roleKey){
        return roleService.queryMenuByRoleKey(roleKey);
    }


    /**
     * 查询角色拥有的权限（编辑查询）
     * @param roleKey
     * @return
     */
    @RequestMapping("queryForUpdate")
    @ResponseBody
    public List<RoleMenuResVo> queryForUpdate(@RequestParam("roleKey")String roleKey){
        return roleService.queryForUpdate(roleKey);
    }


    /**
     * 修改权限
     * @param roleKey
     * @param ids
     * @param session
     * @return
     */
    @RequestMapping("updatePermission")
    @ResponseBody
    public ReturnResult updatePermission(@RequestParam("roleKey")String roleKey,@RequestParam("ids")List<Integer> ids,HttpSession session){
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        if(roleService.updatePermission(roleKey,ids,user)){
            return new ReturnResult();
        }
        return new ReturnResult(StatusEnum.OP_ERROR);
    }









}
