package com.jjh.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jjh.springboot.util.Constant;
import com.jjh.springboot.util.HttpClientUtil.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-16 17:26
 **/

@Component
@Order(2)
public class LoginComponent {

    private static Logger LOG = LoggerFactory
            .getLogger(LoginComponent.class);

    /**
     * 定时 1000*60*30 同步登陆
     */
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void scheduledLogin() {
        syncLogin();
    }

    /**
     * 定时 1000*60*30 同步登陆
     */
//    @Scheduled(fixedRate = 60000)
//    public void isLogin(){
//        syncLogin();
//    }


    /**
     * 同步登陆
     */
    public static void syncLogin() {
        try {
            String json = HttpClientUtil.OkSyncPostProxy(Constant.postLogin, "{\"name\":\"admin\",\"password\":\"21232f297a57a5a743894a0e4a801fc3\"}", true);
            JSONObject jsonObject = JSONObject.parseObject(json);
            if ("OK".equals(jsonObject.getString("message"))) {
                HttpClientUtil.SESSION_ID = jsonObject.getString("session_id");
            }
            LOG.info("The time is now {}, Callback data is {},session_id is {}", LocalDateTime.now(), json,HttpClientUtil.SESSION_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
