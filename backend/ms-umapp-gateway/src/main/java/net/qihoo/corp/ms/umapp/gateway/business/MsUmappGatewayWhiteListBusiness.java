package net.qihoo.corp.ms.umapp.gateway.business;

import com.google.common.collect.Sets;
import net.qihoo.corp.ms.umapp.gateway.properties.MsUmappGatewayWhiteListProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.gateway.config
 * @ClassName: MsUmappGatewayWhiteListBusiness
 * @Description:
 * @date 2022-10-12 16:22:56
 */
@Component
public class MsUmappGatewayWhiteListBusiness {

    @Autowired
    MsUmappGatewayWhiteListProperties whiteListProperties;

    private static Set<String> defaultIgnoredPath = Sets.newHashSet();

    public MsUmappGatewayWhiteListBusiness() {
        defaultIgnoredPath.add("/oauth/token");
        defaultIgnoredPath.add("/oauth/check_token");
        defaultIgnoredPath.add("/oauth/getUserByAccount/*");
        defaultIgnoredPath.add("/sys/user/findByUsername/*");
        defaultIgnoredPath.add("/sys/role/findRoleCodesByUserId/*");
        defaultIgnoredPath.add("/sys/permission/findPermissionsByCodes/*");
    }



    public String[] getAllIgnoredPath() {
        if (whiteListProperties.getIgnoredPath() != null && !whiteListProperties.getIgnoredPath().isEmpty()) {
            whiteListProperties.getIgnoredPath().forEach(p -> {
                defaultIgnoredPath.add(p);
            });
        }
        return defaultIgnoredPath.toArray(new String[defaultIgnoredPath.size()]);
    }
}
