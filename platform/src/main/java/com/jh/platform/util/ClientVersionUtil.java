package com.jh.platform.util;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-21 16:35
 **/
public class ClientVersionUtil {

    /**
     * 版本验证
     */
    public static String VERSION_RULE = "(\\d+\\.){3}\\d+";

    /**
     * 验证是否为正确的版本
     * @param version
     * @return
     */
    public static boolean isVersion(String version){
        return version.matches(VERSION_RULE);
    }



}
