package com.igeek.ebuy.util.pojo;

import java.io.Serializable;

/**
 * @author hftang
 * @date 2019-07-01 17:35
 * @desc
 */
public class BuyResult implements Serializable {

    private int status;
    private String msg;
    private Object data;

    public BuyResult(int status) {
        this.status = status;
    }

    public static BuyResult ok(int status) {

        return new BuyResult(status);
    }


    public int getstatus() {
        return status;
    }

    public void setstatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
