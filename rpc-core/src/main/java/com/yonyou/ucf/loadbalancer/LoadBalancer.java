package com.yonyou.ucf.loadbalancer;

import com.yonyou.ucf.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/21 下午2:19
 * @description 自定义项
 */
public interface LoadBalancer {
    /**
     * 选择服务调用
     *
     * @param requestParams       请求参数
     * @param serviceMetaInfoList 可用服务列表
     * @return
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);

}
