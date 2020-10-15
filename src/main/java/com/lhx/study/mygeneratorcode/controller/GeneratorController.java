package com.lhx.study.mygeneratorcode.controller;

import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import com.lhx.study.mygeneratorcode.constant.StatusEnum;
import com.lhx.study.mygeneratorcode.entity.po.User;
import com.lhx.study.mygeneratorcode.service.GenerateService;
import com.lhx.study.mygeneratorcode.service.MysqlConnectionConfigService;
import com.lhx.study.mygeneratorcode.utils.RequestParamValidUtil;
import com.lhx.study.mygeneratorcode.vo.ReturnResult;
import com.lhx.study.mygeneratorcode.vo.request.generate.GenerateReqVo;
import com.lhx.study.mygeneratorcode.vo.response.database.DataBaseTableListResVo;
import com.lhx.study.mygeneratorcode.vo.response.database.MysqlDbColumnResVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.List;

/**
 * 生产代码管理
 * @Author: lhx
 * @Date: 2019/1/4 16:52
 */
@Slf4j
@Controller
@RequestMapping("/api/generator/")
public class GeneratorController {

    @Autowired
    private MysqlConnectionConfigService configService;

    @Autowired
    private GenerateService generateService;

    @Value("${basePath}")
    private String basePath;


    /**
     * 初始化页面
     * @return
     */
    @GetMapping("init")
    public ModelAndView init(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("/generate/list");
        modelAndView.addObject("dataBases",configService.dataBases((User) session.getAttribute(BaseConstant.SESSION_KEY)));
        return modelAndView;
    }


    /**
     * 根据数据量配置id查询数据库表
     * @param id
     * @param session
     * @return
     */
    @GetMapping("queryTable")
    @ResponseBody
    public List<DataBaseTableListResVo> queryTable(@RequestParam("id")Integer id, HttpSession session){
      return generateService.queryTable(id,(User) session.getAttribute(BaseConstant.SESSION_KEY));
    }


    /**
     * 根据表名获取表字段信息
     * @param id 数据库连接配置id
     * @param table 数据库表名
     * @param session
     * @return
     */
    @GetMapping("queryTableColumns")
    @ResponseBody
    public ReturnResult<List<MysqlDbColumnResVo>> queryTableColumns(@RequestParam("id")Integer id, @RequestParam("table")String table, HttpSession session){

        ReturnResult<List<MysqlDbColumnResVo>> returnResult = new ReturnResult<>();
        List<MysqlDbColumnResVo> mysqlDbColumnResVos = generateService.queryTableColumns(id, table, (User) session.getAttribute(BaseConstant.SESSION_KEY));
        if (CollectionUtils.isNotEmpty(mysqlDbColumnResVos)){
            returnResult.setData(mysqlDbColumnResVos);
        }
        return returnResult;
    }


    /**
     * 生成代码
     * @param request
     * @return
     */
    @PostMapping("generate")
    @ResponseBody
    public ReturnResult generate(@RequestBody @Valid GenerateReqVo request, BindingResult result, HttpSession session){
        ReturnResult returnResult = new ReturnResult();
        if (result.hasErrors()){
            returnResult.setCode(StatusEnum.PARAMS_ERROR.getCode());
            returnResult.setMsg(RequestParamValidUtil.getBindingResultMessage(result));
            return returnResult;
        }
        if (!(request.isEntitySwitchFlag() || request.isServiceSwitchFlag() || request.isDaoSwitchFlag() || request.isMapperSwitchFlag())){
            returnResult.setCode(StatusEnum.PARAMS_ERROR.getCode());
            returnResult.setMsg("请至少选择一个生成路径");
            return returnResult;
        }
        if (StringUtils.isBlank(request.getEntityPath()) && StringUtils.isBlank(request.getServicePath()) && StringUtils.isBlank(request.getDaoPath())){
            returnResult.setCode(StatusEnum.PARAMS_ERROR.getCode());
            returnResult.setMsg("请输入您选择的生成路径");
            return returnResult;
        }
        String generate = generateService.generate(request, (User) session.getAttribute(BaseConstant.SESSION_KEY));
        if (StringUtils.isNotBlank(generate)){
            returnResult.setData(generate);
            return returnResult;
        }
        returnResult.setCode(StatusEnum.OP_ERROR.getCode());
        returnResult.setMsg("下载生成代码失败");
        return returnResult;
    }

    /**
     * 下载代码
     * @param fileName
     * @param response
     * @param session
     */
    @GetMapping("downCode")
    public void downCode(@RequestParam("fileName")String fileName, HttpServletResponse response, HttpSession session){
        User user = (User) session.getAttribute(BaseConstant.SESSION_KEY);
        log.info("用户:{}下载：{}文件",user.getName(), fileName);
        FileInputStream is = null;
        BufferedInputStream bs = null;
        OutputStream os = null;
        File file = new File(basePath + "/zip/",fileName);
        try {
            if (file.exists()) {
                //设置Headers
                response.setHeader("Content-Type","application/octet-stream");
                //设置下载的文件的名称-该方式已解决中文乱码问题
                response.setHeader("Content-Disposition","attachment;filename=" +  new String( fileName.replaceAll("\\d","").getBytes("gb2312"), "ISO8859-1" ));
                is = new FileInputStream(file);
                bs =new BufferedInputStream(is);
                os = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while((len = bs.read(buffer)) != -1){
                    os.write(buffer,0,len);
                }
            }else {
                log.info("用户:{}下载：{}未找到文件,路径{}",user.getName(), fileName,file.getPath());
            }
        } catch (Exception e) {
            log.error("下载代码失败",e);
        } finally {
            try{
                if(is != null){
                    is.close();
                }
                if( bs != null ){
                    bs.close();
                }
                if( os != null){
                    os.flush();
                    os.close();
                }
            }catch (IOException e){
                log.error("关闭流失败",e);
            }
        }
        if (file.exists()) {
            file.delete();
        }

    }

}
