package net.qihoo.corp.umapp.service.comi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/25
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ms.umapp.comi.whitelist")
public class MsUmappComiWhiteListProperties {

    private List<String> ignoredPath;
}
