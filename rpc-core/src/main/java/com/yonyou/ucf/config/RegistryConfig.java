package com.yonyou.ucf.config;

import lombok.Data;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/10 下午5:28
 * @description 注册中心配置
 */
@Data
public class RegistryConfig {
    /**
     * 注册中心类别
     */
    private String registry = "etcd";

    /**
     * 注册中心地址
     */
    private String address = "http://192.168.18.128:2379";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间（单位毫秒）
     */
    private Long timeout = 10000L;
}
