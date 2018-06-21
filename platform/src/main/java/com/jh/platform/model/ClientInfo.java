package com.jh.platform.model;

import java.sql.Timestamp;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-21 16:09
 **/
public class ClientInfo {

    private long id;

    private String usercode;

    private String client_version;

    private Timestamp expires;

    private String remark;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getClient_version() {
        return client_version;
    }

    public void setClient_version(String client_version) {
        this.client_version = client_version;
    }

    public Timestamp getExpires() {
        return expires;
    }

    public void setExpires(Timestamp expires) {
        this.expires = expires;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
