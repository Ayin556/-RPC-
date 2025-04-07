package com.yonyou.ucf.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.yonyou.ucf.common.model.User;
import com.yonyou.ucf.common.service.UserService;
import com.yonyou.ucf.model.RpcRequest;
import com.yonyou.ucf.model.RpcResponse;
import com.yonyou.ucf.serializer.JdkSerializer;

import java.io.IOException;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午2:43
 * @description 静态代理
 */
public class UserServiceProxy implements UserService {
    public User getUser(User user) {
        //指定序列化器
        JdkSerializer serializer = new JdkSerializer();
        //发送请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] bodybtyes = serializer.serialize(rpcRequest);
            byte[] reslut;
            HttpResponse response = HttpRequest.post("http://localhost:8080")
                    .body(bodybtyes)
                    .execute();
            reslut=response.bodyBytes();
            RpcResponse rpcResponse = serializer.deserialize(reslut, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
