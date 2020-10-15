package com.lhx.study.mygeneratorcode.admin;

import com.lhx.study.mygeneratorcode.MyGeneratorCodeApplication;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @Author: lhx
 * @Date: 2019/1/30 15:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyGeneratorCodeApplication.class)
@WebAppConfiguration
public class adminTest {

    @Autowired
    private WebApplicationContext context;

    //mock api 模拟http请求
    private MockMvc mockMvc;

    //新增
    private static String add = "/api/admin/add";

    //批量新增
    private static String addList = "/api/admin/addList";

    private static String updateByPrimaryKey = "/api/admin/updateByPrimaryKey";

    private static String updateBatch = "/api/admin/updateBatch";



    @Before
    public void setUp() {
        //集成Web环境测试（此种方式并不会集成真正的web环境，而是通过相应的Mock API进行模拟测试，无须启动服务器）
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Ignore
    public void add() throws Exception{
        MultiValueMap<String, String> sendSmsMap = new LinkedMultiValueMap<>();
        sendSmsMap.add("account", "test");
        sendSmsMap.add("pwd", "123");
        sendSmsMap.add("userName", "测试");
        RequestBuilder req = post(add).params(sendSmsMap).accept(MediaType.APPLICATION_JSON);
        String res = mockMvc.perform(req).andReturn().getResponse().getContentAsString();
        System.out.println(res);
    }


    @Test
    @Ignore
    public void addList() throws Exception{
        MultiValueMap<String, String> sendSmsMap = new LinkedMultiValueMap<>();
        RequestBuilder req = post(addList).params(sendSmsMap).accept(MediaType.APPLICATION_JSON);
        String res = mockMvc.perform(req).andReturn().getResponse().getContentAsString();
        System.out.println(res);
    }

    @Test
    @Ignore
    public void updateByPrimaryKey() throws Exception{
        MultiValueMap<String, String> sendSmsMap = new LinkedMultiValueMap<>();
        RequestBuilder req = post(updateByPrimaryKey).params(sendSmsMap).accept(MediaType.APPLICATION_JSON);
        String res = mockMvc.perform(req).andReturn().getResponse().getContentAsString();
        System.out.println(res);
    }


    @Test
    @Ignore
    public void updateBatch() throws Exception{
        MultiValueMap<String, String> sendSmsMap = new LinkedMultiValueMap<>();
        RequestBuilder req = post(updateBatch).params(sendSmsMap).accept(MediaType.APPLICATION_JSON);
        String res = mockMvc.perform(req).andReturn().getResponse().getContentAsString();
        System.out.println(res);
    }













}
