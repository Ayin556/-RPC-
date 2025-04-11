package com.yonyou.ucf.registry;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;

import java.util.concurrent.CompletableFuture;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/10 下午4:51
 * @description ETCD注册中心简易案例
 */
public class etcdSampleRegistry {
    public static void main(String[] args) {
        etcdsample();
    }
    /**
     * 简易启动案例
     * */
    private static void etcdsample() {
        try {
            Client client = Client.builder().endpoints("http://192.168.18.128:2379").build();
            KV kvClient = client.getKVClient();
            ByteSequence key = ByteSequence.from("Ayin".getBytes());
            ByteSequence value = ByteSequence.from("rpc".getBytes());
            kvClient.put(key, value).get();
            CompletableFuture<GetResponse> getFuture = kvClient.get(key);
            GetResponse response = getFuture.get();
            //kvClient.delete(key).get();
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }
    }
}
