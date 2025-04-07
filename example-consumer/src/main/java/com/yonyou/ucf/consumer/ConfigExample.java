package com.yonyou.ucf.consumer;

import com.yonyou.ucf.config.RpcConfig;
import com.yonyou.ucf.untils.ConfigUtils.ConfigUtils;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午5:04
 * @description 简易消费者示例
 */
public class ConfigExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
