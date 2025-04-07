package com.yonyou.ucf.provider;

import com.yonyou.ucf.common.service.UserService;
import com.yonyou.ucf.registry.LocalRegistry;
import com.yonyou.ucf.service.VertxHttpService;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午12:38
 * @description 简易服务提供者示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        //注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
//        VertxHttpService vertxHttpService = new VertxHttpService();
//        vertxHttpService.start(8080);
    }
}
