package com.jh.platform.controller;

import com.alibaba.fastjson.JSON;
import com.jh.platform.controller.vo.LoginVO;
import com.jh.platform.mapper.LogUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-22 21:20
 **/
@RestController
@RequestMapping("/yy/client")
public class LoginController extends BaseController{

    @Autowired
    private LogUserMapper logUserMapper;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response){
        LoginVO loginVO = (LoginVO)objectToVO(request.getAttribute("arg"),LoginVO.class);
        System.err.println(JSON.toJSONString(logUserMapper.findUserByName("admin")));
        return "jainghong";
    }

}
