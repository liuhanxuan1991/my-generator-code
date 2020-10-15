package com.lhx.study.mygeneratorcode.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.constant.StatusEnum;
import com.lhx.study.mygeneratorcode.entity.po.Navigation;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.service.NavigationService;
import com.lhx.study.mygeneratorcode.service.UserService;
import com.lhx.study.mygeneratorcode.utils.PropertiesUtil;
import com.lhx.study.mygeneratorcode.utils.RequestParamValidUtil;
import com.lhx.study.mygeneratorcode.vo.ReturnResult;
import com.lhx.study.mygeneratorcode.vo.request.perm.PermAddReqVo;
import com.lhx.study.mygeneratorcode.vo.response.perm.PermListResVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 菜单管理
 * @Author: lhx
 * @Date: 2019/1/29 11:21
 */
@Slf4j
@Controller
@RequestMapping("/api/menu/")
public class PermissionController {

    @Autowired
    private NavigationService navigationService;

    @Autowired
    private UserService userService;


    /**
     * 初始化页面
     * @return
     */
    @GetMapping("init")
    public ModelAndView init(HttpSession session){
        ModelAndView mav = new ModelAndView("/perm/list");
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        mav.addObject("account", user);
        mav.addObject("menuList",navigationService.queryAll());
        return mav;
    }


    /**
     * 菜单分页查询
     * @param id
     * @param session
     * @return
     */
    @PostMapping("menuList")
    @ResponseBody
    public ReturnResult<List<PermListResVo>> roleList(@RequestParam(value = "id",required = false)Integer id,
                                                      @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                      @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                                      HttpSession session){
        ReturnResult<List<PermListResVo>> result = new ReturnResult<>();
        Page<List<PermListResVo>> startPage = PageHelper.startPage(page, limit);
//        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        List<PermListResVo> resultList = navigationService.queryNavList(id);
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
        ModelAndView mav = new ModelAndView("/perm/detail");
        if(2==type){
            mav.setViewName("/perm/update");
        }
//        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        Navigation navigation = navigationService.selectDetailById(id);

        PermListResVo detail = new PermListResVo();
        PropertiesUtil.copyProperties(navigation, detail);
        detail.setCreateUser(userService.selectNameById(navigation.getCreateBy()));
        detail.setUpdateUser(userService.selectNameById(navigation.getUpdateBy()));
        mav.addObject("detail",detail);
        return mav;
    }

    /**
     * 新增页跳转
     * @return
     */
    @RequestMapping("addInit")
    public ModelAndView addInit(){
        ModelAndView mav = new ModelAndView("/perm/add");
        return mav;
    }



    /**
     * 新增菜单
     * @param reqVo
     * @param result
     * @param session
     * @return
     */
    @RequestMapping("addMenu")
    @ResponseBody
    public ReturnResult addPerm(@RequestBody @Valid PermAddReqVo reqVo, BindingResult result, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        Date date = new Date();
        try {
            Navigation navigation = new Navigation();
            PropertiesUtil.copyProperties(reqVo,navigation);
            navigation.setDeleteFlag(false);
            navigation.setCreateBy(user.getId());
            navigation.setCreateTime(date);
            navigation.setUpdateBy(user.getId());
            navigation.setUpdateTime(date);
            if (navigationService.addPerm(navigation)){
                return returnResult;
            }
        } catch (DataIntegrityViolationException e) {
            log.error("新增菜单异常",e);
            returnResult.setCode(StatusEnum.OP_ERROR.getCode());
            returnResult.setMsg("菜单名称已存在");
            return returnResult;
        }
        return new ReturnResult(StatusEnum.OP_ERROR);
    }


    /**
     * 根据主键修改
     * @param reqVo
     * @param result
     * @param session
     * @return
     */
    @RequestMapping("updateMenu")
    @ResponseBody
    public ReturnResult updatePerm(@RequestBody @Valid PermAddReqVo reqVo, BindingResult result, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        if (result.hasErrors()){
            returnResult.setCode(StatusEnum.PARAMS_ERROR.getCode());
            returnResult.setMsg(RequestParamValidUtil.getBindingResultMessage(result));
            return returnResult;
        }
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        Navigation navigation = new Navigation();
        try {
            PropertiesUtil.copyProperties(reqVo,navigation);
            navigation.setUpdateBy(user.getId());
            navigation.setUpdateTime(new Date());
            if (navigationService.updateByPrimaryKeySelective(navigation)){
                return returnResult;
            }
        } catch (DataIntegrityViolationException e) {
            log.error("修改菜单异常",e);
            returnResult.setCode(StatusEnum.OP_ERROR.getCode());
            returnResult.setMsg("菜单名称已存在");
            return returnResult;
        }

        return new ReturnResult(StatusEnum.OP_ERROR);
    }


    /**
     * 删除菜单
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("deleteMenu")
    @ResponseBody
    public ReturnResult deleteRole(@RequestParam("id")Integer id, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        if (navigationService.deletePerm(id,user.getId())){
            return returnResult;
        }
        return new ReturnResult(StatusEnum.OP_ERROR);
    }



}
