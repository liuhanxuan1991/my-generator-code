package com.lhx.study.mygeneratorcode.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.constant.StatusEnum;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.service.RoleService;
import com.lhx.study.mygeneratorcode.service.UserService;
import com.lhx.study.mygeneratorcode.utils.PropertiesUtil;
import com.lhx.study.mygeneratorcode.utils.RequestParamValidUtil;
import com.lhx.study.mygeneratorcode.vo.ReturnResult;
import com.lhx.study.mygeneratorcode.vo.request.account.AccountAddReqVo;
import com.lhx.study.mygeneratorcode.vo.request.account.AccountListReqVo;
import com.lhx.study.mygeneratorcode.vo.response.account.AccountListResVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 账号管理
 * @Author: lhx
 * @Date: 2019/1/18 16:19
 */
@Slf4j
@Controller
@RequestMapping("/api/account/")
public class AccountController {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    /**
     * 初始化页面
     * @return
     */
    @GetMapping("init")
    public ModelAndView init(HttpSession session){
        ModelAndView mav = new ModelAndView("/account/list");
        User account = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        mav.addObject("account", account);
        return mav;
    }


    /**
     * 分页查询
     * @param req
     * @param session
     * @return
     */
    @PostMapping("accountList")
    @ResponseBody
    public ReturnResult<List<AccountListResVo>> accountList(@ModelAttribute AccountListReqVo req, HttpSession session){
        ReturnResult<List<AccountListResVo>> result = new ReturnResult<>();
        User currentUser = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        Page<List<AccountListResVo>> page = PageHelper.startPage(req.getPage(), req.getLimit());
        if (BaseConstant.SYS_ROLE_KEY_SUPER_ADMIN.equals(currentUser.getRoleKey())
                || BaseConstant.SYS_ROLE_KEY_ADMIN.equals(currentUser.getRoleKey())){
            req.setFlag(true);//管理员
        }
        List<AccountListResVo> resultList = userService.accountListPage(req);
        result.setCount(page.getTotal());
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
        ModelAndView mav = new ModelAndView("/account/detail");
        if(2==type){
            mav.setViewName("/account/update");
        }
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        User account = userService.selectByPrimaryKey(id);
        AccountListResVo detail = new AccountListResVo();
        PropertiesUtil.copyProperties(account, detail);
        detail.setCreateUser(userService.selectNameById(account.getCreateBy()));
        detail.setRoleName(roleService.selectNameByKey(account.getRoleKey()));
        detail.setUpdateUser(userService.selectNameById(account.getUpdateBy()));
        mav.addObject("detail",detail);
        mav.addObject("roleList",roleService.queryAll(user));
        return mav;
    }


    /**
     * 新增页跳转
     * @return
     */
    @RequestMapping("addInit")
    public ModelAndView addInit(HttpSession session){
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        ModelAndView mav = new ModelAndView("/account/add");
        mav.addObject("roleList",roleService.queryAll(user));
        return mav;
    }


    /**
     * 新增账号
     * @param req
     * @param result
     * @param session
     * @return
     */
    @RequestMapping("addAccount")
    @ResponseBody
    public ReturnResult addAccount(@RequestBody @Valid AccountAddReqVo req, BindingResult result, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        if (result.hasErrors()){
            returnResult.setCode(StatusEnum.PARAMS_ERROR.getCode());
            returnResult.setMsg(RequestParamValidUtil.getBindingResultMessage(result));
            return returnResult;
        }
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        Date date = new Date();
        User newUser = new User();
        PropertiesUtil.copyProperties(req,newUser);
        newUser.setPwd(DigestUtils.md5DigestAsHex(req.getPwd().getBytes()));
        newUser.setCreateTime(date);
        newUser.setCreateBy(user.getId());
        newUser.setUpdateTime(date);
        newUser.setUpdateBy(user.getId());
        try {
            if (userService.addAccount(newUser)){
                return returnResult;
            }
        } catch (DataIntegrityViolationException e) {
            log.error("新增用户异常",e);
            returnResult.setCode(StatusEnum.OP_ERROR.getCode());
            returnResult.setMsg("该账号已存在");
            return returnResult;
        }
        return new ReturnResult(StatusEnum.OP_ERROR);
    }


    /**
     * 修改账号信息
     * @param req
     * @param result
     * @param session
     * @return
     */
    @RequestMapping("updateAccount")
    @ResponseBody
    public ReturnResult updateAccount(@RequestBody @Valid AccountAddReqVo req, BindingResult result, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        if (result.hasErrors()){
            returnResult.setCode(StatusEnum.PARAMS_ERROR.getCode());
            returnResult.setMsg(RequestParamValidUtil.getBindingResultMessage(result));
            return returnResult;
        }
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        Date date = new Date();
        User newUser = new User();
        PropertiesUtil.copyProperties(req,newUser);
        User primary = userService.selectByPrimaryKey(req.getId());
        if (!req.getPwd().equals(primary.getPwd())){
            newUser.setPwd(DigestUtils.md5DigestAsHex(req.getPwd().getBytes()));
        }
        newUser.setUpdateTime(date);
        newUser.setUpdateBy(user.getId());
        try {
            if (userService.updateByPrimaryKeySelective(newUser)){
                return returnResult;
            }
        } catch (DataIntegrityViolationException e) {
            log.error("修改用户异常",e);
            returnResult.setCode(StatusEnum.OP_ERROR.getCode());
            returnResult.setMsg("该账号已存在");
            return returnResult;
        }

        return new ReturnResult(StatusEnum.OP_ERROR);
    }


    /**
     * 启用
     * @param id
     * @param session
     * @return
     */
    @GetMapping("startUsing")
    @ResponseBody
    public ReturnResult startUsing(@RequestParam("id")Integer id, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        userService.updateByPrimaryKeySelective(new User(){{
            setId(id);
            setStatus(false);
            setUpdateTime(new Date());
            setUpdateBy(user.getId());
        }});
        return returnResult;
    }


    /**
     * 禁用
     * @param id
     * @param session
     * @return
     */
    @GetMapping("forbidden")
    @ResponseBody
    public ReturnResult forbidden(@RequestParam("id")Integer id, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        userService.updateByPrimaryKeySelective(new User(){{
            setId(id);
            setStatus(true);
            setUpdateTime(new Date());
            setUpdateBy(user.getId());
        }});
        return returnResult;
    }






}
