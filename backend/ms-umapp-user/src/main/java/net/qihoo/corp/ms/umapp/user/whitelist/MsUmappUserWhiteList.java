package net.qihoo.corp.ms.umapp.user.whitelist;

import com.google.common.collect.Sets;
import net.qihoo.corp.ms.umapp.user.properties.MsUmappUserWhiteListProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.user.whitelist
 * @ClassName: MsUmappUserWhiteList
 * @Description:
 * @date 2022-10-14 21:13:05
 */
@Component
@SuppressWarnings("all")
public class MsUmappUserWhiteList {

    @Autowired
    MsUmappUserWhiteListProperties whiteListProperties;

    private static Set<String> defaultIgnoredPath = Sets.newHashSet();
    private String[] allIgnoredPath;

    public MsUmappUserWhiteList() {
        defaultIgnoredPath.add("/oauth/token");
    }

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
