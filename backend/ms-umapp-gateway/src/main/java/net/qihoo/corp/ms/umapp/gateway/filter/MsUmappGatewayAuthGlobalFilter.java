package net.qihoo.corp.ms.umapp.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.gateway.business.MsUmappGatewayWhiteListBusiness;
import net.qihoo.corp.ms.umapp.gateway.constants.IMsUmappGatewayTokenConstants;
import net.qihoo.corp.ms.umapp.gateway.util.MsUmappGatewayAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.gateway.filter
 * @ClassName: MsUmappGatewayAuthGlobalFilter
 * @Description:
 * @date 2022-10-12 16:24:25
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class MsUmappGatewayAuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private MsUmappGatewayWhiteListBusiness whiteListBusiness;
    @Autowired
    MsUmappGatewayAuthUtil umappAuthUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求路径
        String path = request.getURI().getPath();
        // 匹配白名单
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String[] whiteList = whiteListBusiness.getAllIgnoredPath();
        boolean action = false;
        for (String url : whiteList) {
            System.out.println(url+"________"+path);
            if (url!=null&&antPathMatcher.match(url, path)) {
                action = true;
                break;
            }
        }
        // 如果路径匹配，直接放行
        if (action) {
            return chain.filter(exchange);
        }
        // 获取请求头中的token
        String token = getToken(exchange);
        if (StringUtils.isEmpty(token)) {
            log.error("无token: {}", token);
            return invalidTokenMono(exchange);
        }
//        else {
//            // 校验token
//            if (!umappAuthUtil.accessable(request)) {
//                return unauthorized(exchange);
//            }
//        }
        ServerHttpRequest tokenRequest = request.mutate().header(IMsUmappGatewayTokenConstants.GW_TOKEN_KEY, IMsUmappGatewayTokenConstants.GW_QUERY_PREFIX + token).build();
        ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
        return chain.filter(build);
    }

    private String getToken(ServerWebExchange exchange) {
        String tokenHeader = exchange.getRequest().getHeaders().getFirst(IMsUmappGatewayTokenConstants.GW_TOKEN_KEY);
        if (StringUtils.isEmpty(tokenHeader)) {
            return null;
        }
        String[] tokenArr = tokenHeader.split(" ");
        String token = null;
        if (null != tokenArr && tokenArr.length > 1) {
            token = tokenArr[1];
        }
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return token;
    }

    private Mono<Void> invalidTokenMono(ServerWebExchange exchange) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.UNAUTHORIZED.value());
        jsonObject.put("data", "没有token");
        return buildReturnMono(jsonObject, exchange);
    }

    private Mono<Void> buildReturnMono(JSONObject jsonObject, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        byte[] bytes = jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.UNAUTHORIZED.value());
        jsonObject.put("data", "token验证失败");
        return buildReturnMono(jsonObject, serverWebExchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
