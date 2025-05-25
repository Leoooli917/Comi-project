package net.qihoo.corp.ms.umapp.user.properties;

import net.qihoo.corp.ms.umapp.common.core.properties.MsUmappCommonWhiteListProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.user.properties
 * @ClassName: MsUmappUserWhiteListProperties
 * @Description:
 * @date 2022-10-12 17:37:08
 */
@Configuration
@SuppressWarnings("all")
@ConfigurationProperties(prefix = "ms.umapp.user.whitelist")
public class MsUmappUserWhiteListProperties extends MsUmappCommonWhiteListProperties {

}
