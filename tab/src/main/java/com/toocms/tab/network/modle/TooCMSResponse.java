package com.toocms.tab.network.modle;

import java.io.Serializable;

/**
 * 类描述：晟轩科技响应BEAN类
 * 创建人：Zero
 * 创建时间：2017/2/15 11:58
 * 修改人：Zero
 * 修改时间：2020/10/10
 * 修改备注：
 */
public class TooCMSResponse<D> implements Serializable {

    private static final long serialVersionUID = -3470353247357331047L;

    /**
     * 标识
     */
    private String flag;

    /**
     * 信息
     */
    private String message;

    /**
     * 数据
     */
    private D data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
