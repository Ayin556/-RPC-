package com.yonyou.ucf.serializer;

import com.yonyou.ucf.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/8 上午11:56
 * @description 序列化工厂类-动态配置
 */
public class SeriailzerFactory {
    //动态加载
    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     * */
    private static final  Serializer DEFAULT_SERIALIZER =new JdkSerializer();

    /**
     * 获取实例
     * */
    public static  Serializer getInstance(String key){
        return SpiLoader.getInstance(Serializer.class,key);
    }
}
