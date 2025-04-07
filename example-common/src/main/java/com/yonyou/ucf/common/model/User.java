package com.yonyou.ucf.common.model;

import java.io.Serializable;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午12:33
 * @description 用户
 */
public class User implements Serializable {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
