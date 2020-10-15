package com.lhx.study.mygeneratorcode.config;

import com.lhx.study.mygeneratorcode.constant.BaseConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 请求拦截
 * @Author: lhx
 * @Date: 2018/12/21 10:59
 */
@Slf4j
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthInterceptor())
                .addPathPatterns("/api/**");
//                .excludePathPatterns("");
    }


    @Bean
    public AuthInterceptor getAuthInterceptor(){
        return new AuthInterceptor();
    }

    /**
     * 实现拦截器HandlerInterceptor 或继承HandlerInterceptorAdapter
     */
    private class AuthInterceptor implements HandlerInterceptor{
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
            boolean verify = true;
            HttpSession session = request.getSession();
            if (null == session.getAttribute(BaseConstant.SESSION_KEY)){
                verify = false;
                reDirect(request,response);
            }
            return verify;
        }


        // 对于请求是ajax请求重定向问题的处理方法
        private void reDirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
            // 获取当前请求的路径
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            log.info("请求路径{}未获取到用户信息，重定向到登录页",basePath);
            // 如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                // 告诉ajax我是重定向
                response.setHeader("REDIRECT", "REDIRECT");
                // 告诉ajax我重定向的路径
                response.setHeader("CONTENTPATH", "/"+BaseConstant.PAGE_LOGIN);
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            } else {
                response.sendRedirect("/"+BaseConstant.PAGE_LOGIN);
            }
        }
    }



}
