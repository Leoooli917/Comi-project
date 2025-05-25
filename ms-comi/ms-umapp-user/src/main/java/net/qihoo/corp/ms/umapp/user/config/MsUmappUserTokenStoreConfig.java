package net.qihoo.corp.ms.umapp.user.config;

import net.qihoo.corp.ms.umapp.common.redis.token.MsUmappRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.user.config
 * @ClassName: MsUmappUserTokenStoreConfig
 * @Description:
 * @date 2022-10-12 17:53:03
 */
@Configuration
@SuppressWarnings("all")
public class MsUmappUserTokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore tokenStore() {
        return new MsUmappRedisTokenStore(redisConnectionFactory);
    }
}
