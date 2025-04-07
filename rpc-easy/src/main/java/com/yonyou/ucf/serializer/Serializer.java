package com.yonyou.ucf.serializer;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午2:03
 * @description 序列化器接口
 */
public interface Serializer {
    /**
     * 序列化
     * */
    <T> byte[] serialize(T obj) throws Exception;

    /**
     * 反序列化
     * */
    <T> T deserialize(byte[] data, Class<T> clazz) throws Exception;
}
