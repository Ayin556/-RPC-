package com.yonyou.ucf.registry;

import com.yonyou.ucf.config.RegistryConfig;
import com.yonyou.ucf.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/11 下午4:28
 * @description 注册中心-接口化
 */
public interface Registry {
    /**
     * 初始化
     * */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     * */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     * */
    void unRegister(ServiceMetaInfo serviceMetaInfo);
    /**
     * 服务发现（获取某服务的所有节点，消费端）
     * */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);
    /**
     * 服务销毁
     */
    void destroy();
}
