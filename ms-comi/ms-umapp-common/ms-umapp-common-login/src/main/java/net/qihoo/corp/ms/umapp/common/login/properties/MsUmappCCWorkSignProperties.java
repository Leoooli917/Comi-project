package net.qihoo.corp.ms.umapp.common.login.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.login.properties
 * @ClassName: MsUmappCCWorkSignProperties
 * @Description:
 * @date 2022-10-13 11:43:43
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ms.umapp.login.ccwork")
@SuppressWarnings("all")
public class MsUmappCCWorkSignProperties {
    private String checkSignDomain;
    private String checkSignUrl;
}
