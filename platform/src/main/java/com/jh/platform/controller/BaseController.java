package com.jh.platform.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.platform.controller.vo.BaseVO;
import com.jh.platform.util.Base64Utils;
import com.jh.platform.util.RSAUtils;

import java.io.IOException;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-01 11:04
 **/
public class BaseController {

    /**
     * jackson转换工具
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    /**
     * covert request data to POJO
     * @param o
     * @param clazz
     * @return
     */
    protected BaseVO objectToVO(Object o, Class<? extends BaseVO> clazz){
        if(o instanceof JSONObject){
            return JSON.toJavaObject((JSONObject)o,clazz);
        }else if(o instanceof String){
            try {
                return  OBJECT_MAPPER.readValue((String)o,clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return JSON.parseObject((String)o,clazz);
        }
        return null;
    }


    /**
     * encrypt Response Data
     * @param o
     * @return
     */
    protected String encryptResponseData(Object o){
        try{
            String source = JSON.toJSONString(o);
            String sign = RSAUtils.sign(source.getBytes(), RSAUtils.serverPrivateKey);
            JSONObject jsonObject = JSON.parseObject(source);
            jsonObject.put("sign", sign);
            source = jsonObject.toJSONString();
            return Base64Utils.encode(RSAUtils.encryptByPublicKey(source.getBytes(), RSAUtils.clientPublicKey));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
