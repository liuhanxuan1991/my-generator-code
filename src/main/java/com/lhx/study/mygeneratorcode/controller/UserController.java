package com.lhx.study.mygeneratorcode.controller;

import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.constant.StatusEnum;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.service.NavigationService;
import com.lhx.study.mygeneratorcode.service.UserService;
import com.lhx.study.mygeneratorcode.utils.RequestParamValidUtil;
import com.lhx.study.mygeneratorcode.vo.ReturnResult;
import com.lhx.study.mygeneratorcode.vo.request.user.ModifyPassReqVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

/**
 * 用户管理
 * @Author: lhx
 * @Date: 2018/12/15 15:18
 */
@Controller
@RequestMapping("/api/user/")
public class UserController {


    @Autowired
    private NavigationService navigationService;

    @Autowired
    private UserService userService;


    /**
     * 获取用户菜单
     * @param session
     * @return
     */
    @GetMapping("getMenu")
    @ResponseBody
    public String getMenu(HttpSession session){
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        if (user == null || StringUtils.isBlank(user.getRoleKey())) {
            return "[]";
        }
       return navigationService.getMenu(user);
    }


    /**
     * 修改密码
     * @param request
     * @param result
     * @param session
     * @return
     */
    @PostMapping("modifyPass")
    @ResponseBody
    public ReturnResult modifyPass(@RequestBody @Valid ModifyPassReqVo request, BindingResult result, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        if (result.hasErrors()){
            returnResult.setCode(StatusEnum.PARAMS_ERROR.getCode());
            returnResult.setMsg(RequestParamValidUtil.getBindingResultMessage(result));
            return returnResult;
        }
        if (!request.getNewPassword().equals(request.getRePassword())){
            returnResult.setCode(StatusEnum.USERNAME_PASSWORD_INCONFORMITY.getCode());
            returnResult.setMsg(StatusEnum.USERNAME_PASSWORD_INCONFORMITY.getValue());
            return returnResult;
        }
        if (request.getNewPassword().equals(request.getOldPassword())){
            returnResult.setCode(StatusEnum.OLD_NEW_PASSWORD_ACCORDANCE.getCode());
            returnResult.setMsg(StatusEnum.OLD_NEW_PASSWORD_ACCORDANCE.getValue());
            return returnResult;
        }

        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        if (!user.getPwd().equals(DigestUtils.md5DigestAsHex(request.getOldPassword().getBytes()))){
            returnResult.setCode(StatusEnum.OLD_PASSWORD_ERROR.getCode());
            returnResult.setMsg(StatusEnum.OLD_PASSWORD_ERROR.getValue());
            return returnResult;
        }
        if (userService.updateByPrimaryKeySelective(new User() {{
            setId(user.getId());
            setPwd(DigestUtils.md5DigestAsHex(request.getNewPassword().getBytes()));
            setUpdateBy(user.getId());
            setUpdateTime(new Date());
        }})) {
            return returnResult;
        }

        returnResult.setCode(StatusEnum.OP_ERROR.getCode());
        returnResult.setMsg(StatusEnum.OP_ERROR.getValue());
        return returnResult;

    }


    /**
     * 退出登录
     * @param session
     * @return
     */
    @GetMapping("loginOut")
    public ModelAndView loginOut(HttpSession session){
        session.removeAttribute(BaseConstant.SESSION_KEY);
        return new ModelAndView("login");
    }


    /**
     * 解锁屏
     * @param pwd
     * @param session
     * @return
     */
    @PostMapping("unLock")
    @ResponseBody
    public ReturnResult unLock(@RequestParam("password")String pwd, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        if(DigestUtils.md5DigestAsHex(pwd.getBytes()).equals(user.getPwd())){
            return returnResult;
        }
        returnResult.setCode(StatusEnum.PASSWORD_ERROR.getCode());
        returnResult.setMsg(StatusEnum.PASSWORD_ERROR.getValue());
        return returnResult;
    }


}
