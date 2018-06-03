package com.jh.platform.controller.vo;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-01 14:19
 **/
public class LoginVO extends BaseVO {

    private String name;

    private String pwd;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
