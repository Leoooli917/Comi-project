package net.qihoo.corp.ms.umapp.common.core.properties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.core.properties
 * @ClassName: MsUmappCommonWhiteListProperties
 * @Description:
 * @date 2022-10-14 21:15:17
 */
@Data
@Configuration
@SuppressWarnings("all")
public class MsUmappCommonWhiteListProperties {

    private List<String> ignoredPath;
}
