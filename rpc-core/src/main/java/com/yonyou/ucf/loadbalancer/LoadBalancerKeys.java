package com.yonyou.ucf.loadbalancer;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/21 下午2:53
 * @description 负载器均衡器键名常量
 */
public interface LoadBalancerKeys {
    /**
     * 轮询
     * */
    String ROUND_ROBIN = "roundRobin";
    String RANDOM = "random";
    String CONSISTENT_HASH = "consistentHash";

}
