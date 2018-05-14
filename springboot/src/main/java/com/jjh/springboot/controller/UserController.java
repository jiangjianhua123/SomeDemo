package com.jjh.springboot.controller;

import com.jjh.springboot.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-14 13:16
 **/
@RestController
public class UserController {

    @RequestMapping(value = "/users",method = RequestMethod.POST,consumes = "application/json")
    public String getUser(@RequestBody User user){
        return "hello,"+ user.getUsername();
    }

}
