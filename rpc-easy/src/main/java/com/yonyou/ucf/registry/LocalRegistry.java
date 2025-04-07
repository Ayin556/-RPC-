package com.yonyou.ucf.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午1:52
 * @description 本地注册表
 */
public class LocalRegistry {
    /**
     * 注册信息存储-使用concurrentHashMap作为线程安全的hashMap处理key和value
     * */
    private static  final Map<String,Class<?>> map = new ConcurrentHashMap<>();
    /**
     * 注册服务
     * @param className 服务名称
     * @param clazz 反射实体(配置泛型实现任何类型)
     * */
    public static void register(String className, Class<?> clazz) {
        map.put(className, clazz);
    }
    /**
     * 获取服务
     * */
    public static Class<?> get(String className) {
       return map.get(className);
    }
    /**
     * 删除服务
     * */
    public static void remove(String className) {
        map.remove(className);
    }
}
