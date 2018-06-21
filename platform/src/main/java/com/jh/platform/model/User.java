package com.jh.platform.model;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-01 15:50
 **/
public class User {

    private static final long serialVersionUID = 1L;

    private long id;

    /**
     *  用户名（主持人使用YY号）
     */
    private String usercode;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密密码的盐
     */
    private String salt;

    /**
     *
     */
    private String username;

    /**
     * 角色 (主持人＝anchor、主管＝director、公会管理员＝manager、超级管理员＝admin）
     */
    private String role;

    /**
     * 所属公会
     */
    private Long consortia_id;

    private Long organization_id;

    /**
     * 支付宝账号
     */
    private String alipay;

    /**
     * 微信账号
     */
    private String weChat;

    /**
     * 账号是否可用状态
     */
    private Boolean locked = Boolean.FALSE;


    public String getCredentialsSalt() {
        return usercode + salt;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getConsortia_id() {
        return consortia_id;
    }

    public void setConsortia_id(Long consortia_id) {
        this.consortia_id = consortia_id;
    }

    public Long getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(Long organization_id) {
        this.organization_id = organization_id;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
