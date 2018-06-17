package com.jh.platform.controller.vo;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-17 19:44
 **/
public class ChangePWVO extends BaseVO{

    /**
     *  用户名
     */
    private String usercode;

    /**
     * 密码
     */
    private String password;

    /**
     * 新密码
     */
    private String newPW;

    /**
     * 机器码_时间戳
     */
    private String code_Timestamps;

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPW() {
        return newPW;
    }

    public void setNewPW(String newPW) {
        this.newPW = newPW;
    }

    public String getCode_Timestamps() {
        return code_Timestamps;
    }

    public void setCode_Timestamps(String code_Timestamps) {
        this.code_Timestamps = code_Timestamps;
    }
}
