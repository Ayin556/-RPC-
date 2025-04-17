package com.yonyou.ucf.registry;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import com.yonyou.ucf.config.RegistryConfig;
import com.yonyou.ucf.model.ServiceMetaInfo;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.watch.WatchEvent;
import org.omg.SendingContext.RunTime;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/11 下午4:42
 * @description Etcd注册中心
 */
public class EtcdRegistry implements Registry {
    private Client client;
    private KV kvClient;

    /**
     * 本机注册的节点 key 集合（用于维护续期）
     */
    private final Set<String> localRegisterNodeKeySet = new HashSet<>();

    /**
     * 本地缓存
     * */
    private final RegistryServiceCache registryServiceCache = new RegistryServiceCache();

    /**
     * 监听key集合
     * */
    private final Set<String> watchServiceKeySet = new ConcurrentHashSet<>();


    /**
     * 根节点
     * */
    private static final String ETCD_ROOT_PATH = "/rpc";

    @Override
    public void init(RegistryConfig registryConfig) {
        client = Client.builder()
                .endpoints(registryConfig.getAddress())
                .connectTimeout(Duration.ofMillis(registryConfig.getTimeout()))
                .build();
        kvClient = client.getKVClient();
        heartBeat();
    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {
    //创建Lease 和KV客户端
        Lease leaseClient = client.getLeaseClient();
        //创建一个30s租约
        long leaseId = leaseClient.grant(30).get().getID();
        //设置要存储的键值对
        String registerKey=ETCD_ROOT_PATH+serviceMetaInfo.getServiceNodeKey();
        ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);
        //将键值对与租约关联，设置过期时间
        PutOption putOption = PutOption.builder().withLeaseId(leaseId).build();
        kvClient.put(key, value, putOption).get();

        //添加节点信息本地缓存
        localRegisterNodeKeySet.add(registerKey);
    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        kvClient.delete(ByteSequence.from(ETCD_ROOT_PATH+serviceMetaInfo.getServiceNodeKey(), StandardCharsets.UTF_8));
        localRegisterNodeKeySet.remove(serviceMetaInfo.getServiceKey());
    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        //启动缓存
        List<ServiceMetaInfo> cacheServiceMetaInfo = registryServiceCache.readCache();
        if (cacheServiceMetaInfo != null) {
            return cacheServiceMetaInfo;
        }
        //前缀搜索
        String searchPrefix = ETCD_ROOT_PATH+serviceKey+"/";
        try {
            // 前缀查询
            GetOption getOption = GetOption.builder().isPrefix(true).build();
            List<KeyValue> keyValues = kvClient.get(
                            ByteSequence.from(searchPrefix, StandardCharsets.UTF_8),
                            getOption)
                    .get()
                    .getKvs();
            List<ServiceMetaInfo> infoList = keyValues.stream().map(keyValue -> {
                //监听key变化
                String key = keyValue.getKey().toString(StandardCharsets.UTF_8);
                watch(key);
                String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                return JSONUtil.toBean(value, ServiceMetaInfo.class);
            }).collect(Collectors.toList());
            registryServiceCache.writeCache(infoList);
            return infoList;
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败", e);
        }
    }

    @Override
    public void destroy() {
        System.out.println("当前节点线下");
        if (kvClient!=null){
            kvClient.close();
        }
        if (client != null) {
            client.close();
        }
    }

    @Override
    public void heartBeat() {
        CronUtil.schedule("*/10****", new Task() {
            @Override
            public void execute() {
                //遍历所有节点
                for (String key :localRegisterNodeKeySet) {
                    try {
                        List<KeyValue> keyValueList = kvClient.get(ByteSequence.from(key, StandardCharsets.UTF_8))
                                .get()
                                .getKvs();
                        //该节点已过期（重启节点才能重新注册）
                        if (CollUtil.isEmpty(keyValueList)){
                            continue;
                        }
                        //节点未过期，续签
                        KeyValue keyValue = keyValueList.get(0);
                        String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        ServiceMetaInfo bean = JSONUtil.toBean(value, ServiceMetaInfo.class);
                        register(bean);
                    }  catch ( Exception e) {
                        throw new RuntimeException("续签失败"+e.getMessage());
                    }
                }
            }
        });
        //支持秒级别任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }

    /**
     * 监听（消费端）
     *
     * @param serviceNodeKey
     */
    @Override
    public void watch(String serviceNodeKey) {
        Watch watchClient = client.getWatchClient();
        // 之前未被监听，开启监听
        boolean newWatch = watchServiceKeySet.add(serviceNodeKey);
        if (newWatch) {
            watchClient.watch(ByteSequence.from(serviceNodeKey, StandardCharsets.UTF_8), response -> {
                for (WatchEvent event : response.getEvents()) {
                    switch (event.getEventType()) {
                        // key 删除时触发
                        case DELETE:
                            // 清理注册服务缓存
                            registryServiceCache.clearCache();
                            break;
                        case PUT:
                        default:
                            break;
                    }
                }
            });
        }
    }

}
