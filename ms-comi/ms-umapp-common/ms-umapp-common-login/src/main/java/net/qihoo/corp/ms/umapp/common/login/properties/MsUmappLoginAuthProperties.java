package net.qihoo.corp.ms.umapp.common.login.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.login.properties
 * @ClassName: MsUmappLoginAuthProperties
 * @Description:
 * @date 2022-10-13 11:42:06
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ms.umapp.login.auth")
@SuppressWarnings("all")
public class MsUmappLoginAuthProperties {
    private String clientId;
    private String clientSecret;
    private String grantType;
    private String defaultPassword;
}
