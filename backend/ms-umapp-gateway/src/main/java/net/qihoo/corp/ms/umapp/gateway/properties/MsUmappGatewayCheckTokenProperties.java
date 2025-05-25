package net.qihoo.corp.ms.umapp.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.gateway.properties
 * @ClassName: MsUmappGatewayCheckTokenProperties
 * @Description:
 * @date 2022-10-12 16:00:04
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ms.umapp.gw.auth")
public class MsUmappGatewayCheckTokenProperties {

    private String checkUrl;
    private String checkMethod;
}
