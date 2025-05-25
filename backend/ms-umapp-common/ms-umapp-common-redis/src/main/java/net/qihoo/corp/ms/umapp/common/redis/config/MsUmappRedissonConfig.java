package net.qihoo.corp.ms.umapp.common.redis.config;

import net.qihoo.corp.ms.umapp.common.redis.properties.MsUmappRedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.redis.config
 * @ClassName: MsUmappRedissonConfig
 * @Description:
 * @date 2022-10-11 18:05:18
 */
public class MsUmappRedissonConfig {

    @Autowired
    MsUmappRedissonProperties umappRedissonProperties;

    // 创建redisson客户端，此时默认使用单节点
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + umappRedissonProperties.getRedisHost() + ":" + umappRedissonProperties.getRedisPort());
        config.useSingleServer().setDatabase(umappRedissonProperties.getDatabase());
        config.useSingleServer().setPassword(umappRedissonProperties.getPassword());
        config.useSingleServer().setTimeout(umappRedissonProperties.getTimeout());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
