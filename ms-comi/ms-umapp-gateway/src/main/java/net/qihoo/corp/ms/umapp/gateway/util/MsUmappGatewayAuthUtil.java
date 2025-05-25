package net.qihoo.corp.ms.umapp.gateway.util;

import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.gateway.constants.IMsUmappGatewayTokenConstants;
import net.qihoo.corp.ms.umapp.gateway.entity.MsUmappGatewayTokenInfo;
import net.qihoo.corp.ms.umapp.gateway.properties.MsUmappGatewayCheckTokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.gateway.util
 * @ClassName: MsUmappGatewayAuthUtil
 * @Description:
 * @date 2022-10-12 16:01:54
 */
@Component
@Slf4j
public class MsUmappGatewayAuthUtil {

    @Autowired
    private MsUmappGatewayCheckTokenProperties checkTokenProperties;

    private RestTemplate restTemplate = new RestTemplate();

    public boolean hasPermissionControl(String url) {
        return url.startsWith("/house");
    }

    public boolean accessable(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(IMsUmappGatewayTokenConstants.GW_TOKEN_KEY);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(checkTokenProperties.getCheckUrl() + checkTokenProperties.getCheckMethod()).queryParam(IMsUmappGatewayTokenConstants.GW_QUERY_PARAM, token.substring(IMsUmappGatewayTokenConstants.GW_QUERY_PREFIX.length(), token.length()));
        URI url = builder.build().encode().toUri();
        HttpEntity<?> entity = new HttpEntity<>(request.getHeaders());
        try {
            ResponseEntity<MsUmappGatewayTokenInfo> response = restTemplate.exchange(url, HttpMethod.GET, entity, MsUmappGatewayTokenInfo.class);
            log.info("oauth request: {}, response body: {}, reponse status: {}",
                    entity, response.getBody(), response.getStatusCode());
            return response.getBody() != null && response.getBody().getUser_name() != null;
        } catch (RestClientException e) {
            log.error("oauth failed.", e);
        }
        return false;
    }
}
