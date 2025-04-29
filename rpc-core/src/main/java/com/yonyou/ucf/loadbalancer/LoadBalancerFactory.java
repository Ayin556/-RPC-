package com.yonyou.ucf.loadbalancer;

import com.yonyou.ucf.spi.SpiLoader;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/21 下午2:55
 * @description 负载均衡工厂
 */
public class LoadBalancerFactory {
    static {
        SpiLoader.load(LoadBalancer.class);
    }
    /**
     * 默认负载均衡
     * */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();
    /**
     * 获取实例
     * */
    public static LoadBalancer getInstance(String key){
        return SpiLoader.getInstance(LoadBalancer.class,key);
    }
}
