package net.qihoo.corp.umapp.service.comi.config;

import net.qihoo.corp.ms.umapp.common.redis.token.MsUmappRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@SuppressWarnings("all")
public class MsUmappServiceComiConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore tokenStore() {
        return new MsUmappRedisTokenStore(redisConnectionFactory);
    }
}
