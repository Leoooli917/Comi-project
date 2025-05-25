package net.qihoo.corp.ms.umapp.common.redis.properties;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.redis.properties
 * @ClassName: MsUmappRedissonProperties
 * @Description:
 * @date 2022-10-11 18:06:28
 */
@Configuration
@ConfigurationProperties(value = "spring.redis")
@Data
public class MsUmappRedissonProperties {

    // redis相关配置
    private String redisHost;

    private String redisPort;

    private int database;

    private String password;

    private int timeout;

}
