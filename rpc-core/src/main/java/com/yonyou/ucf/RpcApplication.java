package com.yonyou.ucf;

import com.yonyou.ucf.config.RpcConfig;
import com.yonyou.ucf.untils.ConfigUtils.ConfigUtils;
import com.yonyou.ucf.untils.constant.RpcConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午4:50
 * @description rpc标准默认配置启动类
 */
@Slf4j
public class RpcApplication {
    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持传入自定义配置
     * */
    public static void  init(RpcConfig newrpcConfig){
       rpcConfig = newrpcConfig;
       log.info("rpc初始化配置信息init, config = {}", newrpcConfig.toString());
    }

    /**
     * 初始化
     * */
    public static void init(){
        RpcConfig  newrpconfig;
        try {
            newrpconfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            //配置加载失败-使用默认配置
            newrpconfig=new RpcConfig();
        }
        init(newrpconfig);
    }
    /**
     * 获取配置
     *
     * @return
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
