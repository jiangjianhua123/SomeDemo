package com.jjh.springboot.filter;

import com.jjh.springboot.util.HttpClientUtil.HttpClientUtil;
import com.jjh.springboot.util.LoginComponent;
import org.apache.catalina.filters.RemoteIpFilter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-16 17:22
 **/
@Configuration
public class ConfigurationFilter {

    private static Logger LOG = LoggerFactory
            .getLogger(ConfigurationFilter.class);

    CopyOnWriteArrayList<String> sencondLogin = new CopyOnWriteArrayList<String>(){{
        add("camera");
        add("repository");
    }};

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //添加过滤器
        registration.setFilter(new MyFilter());
        //设置过滤路径，/*所有路径
        registration.addUrlPatterns("/*");
        //添加默认参数
        registration.addInitParameter("name", "alue");
        //设置优先级
        registration.setName("MyFilter");
        //设置优先级
        registration.setOrder(1);
        return registration;
    }

    public class MyFilter implements Filter {
        @Override
        public void destroy() {
        }

        @Override
        public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain
                filterChain)
                throws IOException {
            HttpServletRequest request = (HttpServletRequest) srequest;
            HttpServletResponse response = (HttpServletResponse ) sresponse;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorizationaccept, origin, content-type,token");
//            //允许跨域请求中携带cookie
            response.setHeader("Access-Control-Allow-Credentials","true");

            LOG.info("Theadname is {}",Thread.currentThread().getName());
            LOG.info("RequestURL is {}",request.getRequestURL());
            String str = sencondLogin.stream().filter((s)->request.getRequestURL().indexOf(s)>0).findFirst().orElse("");
            switch (RequestMethod.valueOf(request.getMethod())){
                case GET:
                    getMethod(request,response);
                    break;
                case POST:
                    postMethod(request,response);
                    if(!StringUtils.isEmpty(str)){
                        LoginComponent.syncLogin();}
                    break;
                case PUT:
                    putMethod(request,response);
                    if(!StringUtils.isEmpty(str)){
                        LoginComponent.syncLogin();}
                    break;
                case DELETE:
                    deleteMethod(request,response);
                    if(!StringUtils.isEmpty(str)){
                        LoginComponent.syncLogin();}
                    break;
                case PATCH:
                    break;
                case OPTIONS:
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case HEAD:
                    break;
                case TRACE:
                    break;
            }
            //filterChain.doFilter(srequest, sresponse);
        }

        private  String utf8Charset = "UTF-8";


        private void getMethod(HttpServletRequest request,HttpServletResponse response) throws IOException{
            String json = HttpClientUtil.httpSyncGet(request.getRequestURL().toString(),request.getQueryString(),true);
            response.setCharacterEncoding(utf8Charset);
            response.setContentType("application/json; charset=utf-8");
            LOG.info("getMethod json is {}",json);
            try(PrintWriter out = response.getWriter()) {
                out.append(json);
            }
        }

        private void postMethod(HttpServletRequest request,HttpServletResponse response) throws IOException{
            String json = HttpClientUtil.OkSyncPostProxy(request.getRequestURL().toString(),IOUtils.toString(request.getInputStream(),utf8Charset),true);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            LOG.info("postMethod json is {}",json);
            try(PrintWriter out = response.getWriter()) {
                out.append(json);
            }
        }

        private void putMethod(HttpServletRequest request,HttpServletResponse response) throws IOException{
            String json = HttpClientUtil.OkSyncPutProxy(request.getRequestURL().toString(),IOUtils.toString(request.getInputStream(),utf8Charset),true);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            LOG.info("putMethod json is {}",json);
            try(PrintWriter out = response.getWriter()) {
                out.append(json);
            }
        }

        private void deleteMethod(HttpServletRequest request,HttpServletResponse response) throws IOException{
            String json = HttpClientUtil.OkSyncDeleteProxy(request.getRequestURL().toString(),IOUtils.toString(request.getInputStream(),utf8Charset),request.getQueryString(),true);
            response.setCharacterEncoding(utf8Charset);
            response.setContentType("application/json; charset=utf-8");
            LOG.info("deleteMethod json is {}",json);
            try(PrintWriter out = response.getWriter()) {
                out.append(json);
            }
        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {

        }
    }
}
