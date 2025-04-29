package com.yonyou.ucf.loadbalancer;

import com.yonyou.ucf.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/21 下午2:27
 * @description 轮询负载
 */
public class RoundRobinLoadBalancer implements LoadBalancer{
    /**
     * 当前轮询的下标-使用JUC包实现原子计数器，防止并发冲突
     * */
    private final AtomicInteger countIndex = new AtomicInteger(0);
    /**
     * 选择服务调用
     *
     * @param requestParams       请求参数
     * @param serviceMetaInfoList 可用服务列表
     * @return
     */
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (serviceMetaInfoList == null || serviceMetaInfoList.isEmpty()) {
            return null;
        }
        //只有一个服务
        if (serviceMetaInfoList.size()==1){
            return serviceMetaInfoList.get(0);
        }
        //取模---轮询
        int index=countIndex.getAndIncrement() % serviceMetaInfoList.size();
        return serviceMetaInfoList.get(index);
    }
}
