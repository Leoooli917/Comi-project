package net.qihoo.corp.ms.umapp.common.util.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.util.user
 * @ClassName: MsUmappCurrUserUtil
 * @Description:
 * @date 2022-10-11 17:11:55
 */
public class MsUmappCurrUserUtil {

    /**
     * 获取当前登录用户ID
     *
     * @return
     */
    public static synchronized Integer getCurrentUserId() {
        Map<String, ?> currentUser = getCurrentUser();
        if (!CollectionUtils.isEmpty(currentUser)) {
            Map<String, Object> principal = (Map<String, Object>) currentUser.get("principal");
            if (!CollectionUtils.isEmpty(principal)) {
                return (Integer) principal.get("id");
            }
        }
        return null;
    }

    private static Map<String, ?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            authentication = oAuth2Authentication.getUserAuthentication();
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                Object principal = authentication.getDetails();
                return (Map<String, ?>) principal;
            }
        }
        return null;
    }
}
