package net.qihoo.corp.ms.umapp.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.gateway.properties
 * @ClassName: MsUmappGatewayWhiteListProperties
 * @Description:
 * @date 2022-10-14 12:00:36
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ms.umapp.gw.whitelist")
public class MsUmappGatewayWhiteListProperties {

    private List<String> ignoredPath;
}
