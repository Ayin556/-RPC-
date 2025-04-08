package com.yonyou.ucf.serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/8 上午11:56
 * @description 序列化工厂类
 */
public class SeriailzerFactoryeasy {
    private static final Map<String,Serializer> KEY_SERIALIZER_MAP = new HashMap<String,Serializer>(){{
        put(SerializerKeys.JDK, new JdkSerializer ());
        put(SerializerKeys.KRYO, new KryoSerializer ());
        put(SerializerKeys.JSON, new JsonSerializer ());
        put(SerializerKeys.HESSIAN,new HessianSerializer ());
    }};
    /**
     * 默认序列化器
     * */
    private static final  Serializer DEFAULT_SERIALIZER =KEY_SERIALIZER_MAP.get("jdk");

    /**
     * 获取实例
     * */
    public static  Serializer getInstance(String key){
       return KEY_SERIALIZER_MAP.get(key);
    }
}
