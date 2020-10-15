package com.lhx.study.mygeneratorcode.controller;

import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.constant.StatusEnum;
import com.lhx.study.mygeneratorcode.entity.po.MysqlConnectionConfig;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.service.MysqlConnectionConfigService;
import com.lhx.study.mygeneratorcode.utils.PropertiesUtil;
import com.lhx.study.mygeneratorcode.utils.RequestParamValidUtil;
import com.lhx.study.mygeneratorcode.vo.ReturnResult;
import com.lhx.study.mygeneratorcode.vo.request.database.DataBaseAddReqVo;
import com.lhx.study.mygeneratorcode.vo.request.database.DataBaseListReqVo;
import com.lhx.study.mygeneratorcode.vo.response.database.DataBaseListResVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 *数据库配置
 * @Author: lhx
 * @Date: 2018/12/17 20:26
 */
@Slf4j
@Controller
@RequestMapping("/api/database/")
public class DataBaseController {


    @Autowired
    private MysqlConnectionConfigService configService;


    /**
     * 初始化页面
     * @return
     */
    @GetMapping("init")
    public ModelAndView init(){
        return new ModelAndView("/database/list");
    }


    /**
     * 分页查询
     * @param req
     * @param session
     * @return
     */
    @RequestMapping("dataBaseList")
    @ResponseBody
    public ReturnResult<List<DataBaseListResVo>> dataBaseList(@ModelAttribute DataBaseListReqVo req, HttpSession session){
        ReturnResult<List<DataBaseListResVo>> result = new ReturnResult<>();
        try {
            User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
            result = configService.dataBaseList(req, user);
        } catch (Exception e) {
           log.error("分页查询数据库配置失败",e);
            result.setCode(StatusEnum.SERVER_ERROR.getCode());
            result.setMsg(StatusEnum.SERVER_ERROR.getValue());
        }
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
        ModelAndView mav = new ModelAndView("/database/detail");
        if(2==type){
            mav.setViewName("/database/update");
        }
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        DataBaseListResVo resVo = configService.queryDataBaseById(id, user);
        mav.addObject("detail",resVo);
        return mav;
    }


    /**
     * 新增数据库配置
     * @param req
     * @param session
     * @param result
     * @return
     */
    @PostMapping("addDataBase")
    @ResponseBody
    public ReturnResult addDataBase(@RequestBody @Valid DataBaseAddReqVo req, BindingResult result, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        if (result.hasErrors()){
            returnResult.setCode(StatusEnum.PARAMS_ERROR.getCode());
            returnResult.setMsg(RequestParamValidUtil.getBindingResultMessage(result));
            return returnResult;
        }
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        Date date = new Date();
        MysqlConnectionConfig entity = new MysqlConnectionConfig();
        entity.setCreateBy(user.getId());
        entity.setCreateTime(date);
        entity.setUpdateBy(user.getId());
        entity.setUpdateTime(date);
        PropertiesUtil.copyProperties(req,entity);
        if ( configService.insertSelective(entity) <= 0){
            return new ReturnResult(StatusEnum.OP_ERROR);
        }
        return returnResult;
    }


    /**
     * 修改数据库配置
     * @param req
     * @param result
     * @param session
     * @return
     */
    @PostMapping("updateDataBase")
    @ResponseBody
    public ReturnResult updateDataBase(@ModelAttribute @Valid DataBaseAddReqVo req, BindingResult result, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        if (result.hasErrors()){
            returnResult.setCode(StatusEnum.PARAMS_ERROR.getCode());
            returnResult.setMsg(RequestParamValidUtil.getBindingResultMessage(result));
            return returnResult;
        }
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        MysqlConnectionConfig entity = new MysqlConnectionConfig();
        entity.setUpdateBy(user.getId());
        entity.setUpdateTime(new Date());
        PropertiesUtil.copyProperties(req,entity);
        if(configService.updateByPrimaryKey(entity)<= 0){
            return new ReturnResult(StatusEnum.OP_ERROR);
        }
        return returnResult;
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
        configService.updateByPrimaryKey(new MysqlConnectionConfig(){{
            setId(id);
            setIsEnabled(BaseConstant.IS_ENABLED_YES);
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
        configService.updateByPrimaryKey(new MysqlConnectionConfig(){{
            setId(id);
            setIsEnabled(BaseConstant.IS_ENABLED_NO);
            setUpdateTime(new Date());
            setUpdateBy(user.getId());
        }});
        return returnResult;
    }


    /**
     * 删除
     * @param id
     * @param session
     * @return
     */
    @GetMapping("delete")
    @ResponseBody
    public ReturnResult delete(@RequestParam("id")Integer id, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        configService.updateByPrimaryKey(new MysqlConnectionConfig(){{
            setId(id);
            setDeleteFlag(BaseConstant.DELETE_FLAG_YES);
            setUpdateTime(new Date());
            setUpdateBy(user.getId());
        }});
        return returnResult;
    }


}
