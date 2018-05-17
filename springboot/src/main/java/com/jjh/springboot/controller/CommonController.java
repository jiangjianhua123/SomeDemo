package com.jjh.springboot.controller;

import com.jjh.springboot.util.Constant;
import com.jjh.springboot.util.HttpClientUtil.HttpClientUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-17 13:33
 **/
@RestController
@RequestMapping("/business/api")
public class CommonController {

    @RequestMapping(value = "/*",method = RequestMethod.GET,consumes = "application/json")
    @ResponseBody
    public String getUser(HttpServletRequest request) throws IOException {
        String url = request.getRequestURI();
        return HttpClientUtil.OkSyncGetProxy(Constant.ipAndPort+url,true);
    }


}
