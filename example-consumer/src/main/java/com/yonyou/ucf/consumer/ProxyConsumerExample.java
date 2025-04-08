package com.yonyou.ucf.consumer;

import com.yonyou.ucf.RpcApplication;
import com.yonyou.ucf.common.model.User;
import com.yonyou.ucf.common.service.UserService;
import com.yonyou.ucf.proxy.ServiceProxyFactory;
import com.yonyou.ucf.proxy.UserServiceProxy;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午12:41
 * @description 简易服务消费者启动示例
 */
public class ProxyConsumerExample {
    public static void main(String[] args) {
        User user = new User();
        user.setCode("9527");
        //staticProxy(user);
        dynamicProxy(user);
    }
    /**
     * 静态代理-staticProxy
     * 缺点：如果要实现多个，则每一个都要重新实现一个
     */
    public static void staticProxy(User user){
        UserServiceProxy userServiceProxy = new UserServiceProxy();
        System.out.println("实现静态代理--");
        userServiceProxy.getUser(user);
        System.out.println("静态代理结束--");
    }
    /**
     * 动态代理-DynamicProxy --标准类型
     * */
    public static void dynamicProxy(User user){
        UserService service = ServiceProxyFactory.getProxy(UserService.class);
        user.setCode("9527111");
        // 调用
        User newUser = service.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getCode());
        } else {
            System.out.println("user == null");
        }
    }

}
