package net.qihoo.corp.umapp.service.comi.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.*;
import java.util.stream.Collectors;

public class OAuth2Helper {

    public static String extractBearerToken(String user_ID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            return details.getTokenValue();
        }
        return null;
    }

    private static OAuth2Authentication getOAuth2Authentication(String user, String mail, Set<String> roles) {
        // 创建 OAuth2Request 对象并设置详细信息
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("user", user);
        requestParameters.put("mail", mail);

        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, null, null, true, new HashSet<>(), new HashSet<>(), null, new HashSet<>(), new HashMap<>());

        // 创建 Authentication 对象
        final List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        User userPrincipal = new User(user, mail, authorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);

        // 创建 OAuth2Authentication 对象
        return new OAuth2Authentication(oAuth2Request, authenticationToken);
    }


}