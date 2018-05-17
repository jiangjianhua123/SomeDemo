package com.jjh.springboot.util;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-17 13:18
 **/
public class Constant {

    public static String ipAndPort = "http://127.0.0.1";

    public static String prefix = "/business/api/";

    public static String getMySelf = ipAndPort+prefix+"user/self";

    public static String postLogin = ipAndPort+prefix+"login";

}
