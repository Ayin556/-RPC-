package com.yonyou.ucf.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.yonyou.ucf.RpcApplication;
import com.yonyou.ucf.config.RpcConfig;
import com.yonyou.ucf.constant.RpcConstant;
import com.yonyou.ucf.model.RpcRequest;
import com.yonyou.ucf.model.RpcResponse;
import com.yonyou.ucf.model.ServiceMetaInfo;
import com.yonyou.ucf.registry.Registry;
import com.yonyou.ucf.registry.RegistryFactory;
import com.yonyou.ucf.serializer.JdkSerializer;
import com.yonyou.ucf.serializer.SeriailzerFactory;
import com.yonyou.ucf.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 服务代理（JDK 动态代理）
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        Serializer serializer = SeriailzerFactory.getInstance(RpcApplication.getRpcConfig().getSeriailzer());

        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 发送请求
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo=new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(rpcRequest.getServiceName());
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfos = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if(CollUtil.isEmpty(serviceMetaInfos)){
                throw new RuntimeException("service未发现！");
            }
            //暂时只取1个
            ServiceMetaInfo serviceMetaInfo1 = serviceMetaInfos.get(0);
            // todo 动态地址配置
                HttpResponse httpResponse = HttpRequest.post(serviceMetaInfo1.getServiceAddress())
                        .body(bodyBytes)
                        .execute();
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
