package com.jh.platform.controller.vo;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-17 17:46
 **/
public class RegisterVO extends BaseVO{

    /**
     *  用户名（主持人使用YY号）
     */
    private String usercode;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信
     */
    private String weChat;

    /**
     * 机器码
     */
    private String hardwareCode;

    /**
     * 客户端版本号
     */
    private String clientsVersion;

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

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getHardwareCode() {
        return hardwareCode;
    }

    public void setHardwareCode(String hardwareCode) {
        this.hardwareCode = hardwareCode;
    }

    public String getClientsVersion() {
        return clientsVersion;
    }

    public void setClientsVersion(String clientsVersion) {
        this.clientsVersion = clientsVersion;
    }
}
