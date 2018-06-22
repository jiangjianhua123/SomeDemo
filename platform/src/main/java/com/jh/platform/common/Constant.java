package com.jh.platform.common;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-20 9:22
 **/
public class Constant {

    public static String underline = "_";

    /**
     * 未注册或密码错误 40001
     */
    public static int USER_PWD_OR_NULL = 40001;

    /**
     * 当前用户客户版本非法 40002
     */
    public static int USER_CLIENT_VERSION_ILLEGAL = 40002;


    /**
     * 账号过期 40003
     */
    public static int USER_CLIENT_VERSION_OVER = 40003;


    /**
     * 账号太多 40004
     */
    public static int USER_LIMIT = 40004;

    /**
     *登陆失败次数过多，今天限制登陆。 40005
     */
    public static int USER_LOGIN_LIMIT = 40005;


    /**
     *密码修改错误次数太多 40006
     */
    public static int USER_PWD_MODIFY_LIMIT = 40006;


    /**
     * 当前用户没有可用的客户版本 40007
     */
    public static int USER_CLIENT_NULL = 40007;



    /**
     * 当前用户客户版本不是最新，请马上更新 40008
     */
    public static int USER_CLIENT_VERSION_UPDATE = 40008;


}
