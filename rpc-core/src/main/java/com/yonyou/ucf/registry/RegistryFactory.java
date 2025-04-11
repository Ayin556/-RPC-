package com.yonyou.ucf.registry;

import com.yonyou.ucf.spi.SpiLoader;
import sun.rmi.registry.RegistryImpl;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/11 下午5:24
 * @description 注册中心工厂（用于获取注册中心对象）
 */
public class RegistryFactory {
    static {
        SpiLoader.load(RegistryFactory.class);
    }
    /**
     * 默认注册中心
     * */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();
    /**
     * 获取实例
     * */
    public static Registry getInstance(String key){
        return SpiLoader.getInstance(Registry.class, key);
    }
}
