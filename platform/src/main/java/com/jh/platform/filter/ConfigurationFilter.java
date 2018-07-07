package com.jh.platform.filter;

import com.jh.platform.util.Base64Utils;
import com.jh.platform.util.RSAUtils;
import org.apache.catalina.filters.RemoteIpFilter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-22 21:10
 **/
@Configuration
public class ConfigurationFilter {

    private static Logger LOG = LoggerFactory.getLogger(ConfigurationFilter.class);

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
        registration.addUrlPatterns("/yy/client/*");
        //添加默认参数
       // registration.addInitParameter("name", "alue");
        //设置优先级
        registration.setName("MyFilter");
        //设置优先级
        registration.setOrder(1);
        return registration;
    }

    public class MyFilter implements Filter{

        @Override
        public void init(FilterConfig filterConfig){

        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)  {
            try{
                byte[] decodedData = RSAUtils.decryptByPrivateKey(Base64Utils.decode(IOUtils.toByteArray(servletRequest.getInputStream())), RSAUtils.serverPrivateKey);
                String decodedDataStr = new String(decodedData);
                LOG.info("decodedDataStr:"+decodedDataStr);
//                JSONObject res = JSONObject.parseObject(decodedDataStr,Feature.IgnoreAutoType);
//                LOG.info("JsonObject:"+res);
                servletRequest.setAttribute("arg",decodedDataStr);
                filterChain.doFilter(servletRequest, servletResponse);
//                String sign = res.getString("sign");
//                res.remove("sign");
//                LOG.info("sign数据：" + sign);
//                boolean status = RSAUtils.verify(JSONObject.toJSONString(res,true).getBytes(), RSAUtils.clientPublicKey, sign);
//                LOG.info("sign结果：" + status);
//                if(status){
//                    servletRequest.setAttribute("arg",res);
//                    filterChain.doFilter(servletRequest, servletResponse);
//                }else{
//                    servletResponse.getWriter().append("data format is wrong");
//                    servletResponse.getWriter().flush();
//                }
            }catch (Exception e){
                try {
                    LOG.error(e.getMessage());
                    servletResponse.getWriter().append("data format is wrong");
                    servletResponse.getWriter().flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        @Override
        public void destroy() {

        }
    }


}
