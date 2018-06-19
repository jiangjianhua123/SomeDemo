package com.jh.platform.controller.result;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-03 19:42
 **/
public class BaseResult {

    private String result;

    private int code;

    private Object data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
