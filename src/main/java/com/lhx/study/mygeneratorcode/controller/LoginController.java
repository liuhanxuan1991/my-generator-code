package com.lhx.study.mygeneratorcode.controller;

import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.constant.StatusEnum;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.service.UserService;
import com.lhx.study.mygeneratorcode.utils.RequestParamValidUtil;
import com.lhx.study.mygeneratorcode.utils.ValidateCode;
import com.lhx.study.mygeneratorcode.vo.request.login.DoLoginReqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 登录
 * @Author: lhx
 * @Date: 2018/12/13 15:48
 */
@Slf4j
@Controller
public class LoginController {


    @Autowired
    private UserService userService;


    /**
     * 登录跳转
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    /**
     * 登录跳转
     * @return
     */
    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("login");
    }


    /**
     * 登录
     * @param req
     * @param result
     * @param session
     * @return
     */
    @RequestMapping("/doLogin")
    public ModelAndView doLogin(@ModelAttribute @Valid DoLoginReqVo req, BindingResult result, HttpSession session){
        ModelAndView mav = new ModelAndView(BaseConstant.PAGE_LOGIN);
        if (result.hasErrors()){
            mav.addObject(BaseConstant.MSG,RequestParamValidUtil.getBindingResultMessage(result));
            return mav;
        }
        if(!req.getCode().equalsIgnoreCase((String)session.getAttribute("code"))){
            mav.addObject(BaseConstant.MSG, StatusEnum.VALIDATE_CODE_INVALID.getValue());
            return mav;
        }
        User user = userService.selectByEntity(new User() {{
            setAccount(req.getAccount());
        }});
        if (null == user){
            mav.addObject(BaseConstant.MSG, StatusEnum.USERNAME_PASSWORD_ERROR.getValue());
            return mav;
        }

        if (!user.getPwd().equals(DigestUtils.md5DigestAsHex(req.getPwd().getBytes()))){
            mav.addObject(BaseConstant.MSG, StatusEnum.USERNAME_PASSWORD_ERROR.getValue());
            return mav;
        }
        if (user.getStatus()){
            mav.addObject(BaseConstant.MSG, StatusEnum.USERNAME_STATUS_ERROR.getValue());
            return mav;
        }
        session.setAttribute(BaseConstant.SESSION_KEY,user);
        //设置session失效时间为1小时
        session.setMaxInactiveInterval(3600);
        mav.setViewName(BaseConstant.PAGE_INDEX);
        return mav;
    }

    /**
     * 获取验证码
     * @param response
     * @param session
     */
    @RequestMapping("/validateCode")
    public void validateCode(HttpServletResponse response, HttpSession session) {
        try {
            // 设置响应的类型格式为图片格式
            response.setContentType("image/jpeg");
            //禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            ValidateCode vCode = new ValidateCode(120,40,5,100);
            session.setAttribute("code", vCode.getCode());
            vCode.write(response.getOutputStream());
        }catch (Exception ex){
            log.error("验证码生成异常", ex);
        }
    }


}
