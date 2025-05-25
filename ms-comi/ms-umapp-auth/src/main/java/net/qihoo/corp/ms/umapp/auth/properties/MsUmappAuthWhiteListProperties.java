package net.qihoo.corp.ms.umapp.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.auth.properties
 * @ClassName: MsUmappAuthWhiteListProperties
 * @Description:
 * @date 2022-10-12 18:43:29
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ms.umapp.auth.whitelist")
public class MsUmappAuthWhiteListProperties {

    private List<String> ignoredPath;
}
