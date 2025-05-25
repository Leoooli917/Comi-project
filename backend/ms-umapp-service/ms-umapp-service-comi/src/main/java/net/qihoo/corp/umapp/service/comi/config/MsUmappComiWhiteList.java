package net.qihoo.corp.umapp.service.comi.config;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @quthor cnn
 * @date 2024/3/25
 */
@Component
@SuppressWarnings("all")
public class MsUmappComiWhiteList {

    @Autowired
    private MsUmappComiWhiteListProperties whiteListProperties;

    private static Set<String> defaultIgnoredPath = Sets.newHashSet();

    public MsUmappComiWhiteList() {
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
