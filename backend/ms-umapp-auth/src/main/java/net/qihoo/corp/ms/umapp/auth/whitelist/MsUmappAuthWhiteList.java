package net.qihoo.corp.ms.umapp.auth.whitelist;

import com.google.common.collect.Sets;
import net.qihoo.corp.ms.umapp.auth.properties.MsUmappAuthWhiteListProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.auth.whitelist
 * @ClassName: MsUmappAuthWhiteList
 * @Description:
 * @date 2022-10-14 20:17:26
 */
@Component
@SuppressWarnings("all")
public class MsUmappAuthWhiteList {

    @Autowired
    private MsUmappAuthWhiteListProperties whiteListProperties;

    private static Set<String> defaultIgnoredPath = Sets.newHashSet();

    public MsUmappAuthWhiteList() {
        defaultIgnoredPath.add("/oauth/token");
        defaultIgnoredPath.add("/oauth/check_token/*");
    }

    private String[] allIgnoredPath;

    public String[] getAllIgnoredPath() {
        System.out.println("执行getAllIgnoredPath...");
        if (whiteListProperties.getIgnoredPath() != null && !whiteListProperties.getIgnoredPath().isEmpty()) {
            whiteListProperties.getIgnoredPath().forEach(p -> {
                defaultIgnoredPath.add(p);
            });
        }
        return defaultIgnoredPath.toArray(new String[defaultIgnoredPath.size()]);
    }
}
