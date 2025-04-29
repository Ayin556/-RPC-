package com.yonyou.ucf.loadbalancer;

import com.yonyou.ucf.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/21 下午2:35
 * @description 随机数轮询
 */
public class RandomLoadBalancer implements LoadBalancer {
    private final Random random=new Random();
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (serviceMetaInfoList == null || serviceMetaInfoList.isEmpty()) {
            return null;
        }
        //只有一个服务
        if (serviceMetaInfoList.size()==1){
            return serviceMetaInfoList.get(0);
        }
        return serviceMetaInfoList.get(random.nextInt(serviceMetaInfoList.size()));
    }
}
