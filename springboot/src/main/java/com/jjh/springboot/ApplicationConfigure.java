package com.jjh.springboot;

import com.jjh.springboot.util.HttpClientUtil.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author jianghong
 * @Description loading
 * @create 2018-05-17 10:33
 **/
@Component
@Order(1)
public class ApplicationConfigure implements CommandLineRunner {

    @Value("${YT_IP}")
    private String YT_IP;
    @Value("${YT_PORT}")
    private int YT_PORT;


    @Override
    public void run(String... args)  {
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
        HttpClientUtil.host = YT_IP;
        HttpClientUtil.port = YT_PORT;
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
    }

}
