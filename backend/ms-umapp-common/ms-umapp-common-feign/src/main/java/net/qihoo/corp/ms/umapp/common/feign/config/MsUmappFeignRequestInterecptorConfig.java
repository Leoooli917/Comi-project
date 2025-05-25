package net.qihoo.corp.ms.umapp.common.feign.config;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Target;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.feign.config
 * @ClassName: MsUmappFeignRequestInterecptorConfig
 * @Description:
 * @date 2022-10-11 17:52:14
 */
@Component
@Slf4j
public class MsUmappFeignRequestInterecptorConfig implements RequestInterceptor {

    private final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        final Target<?> target = requestTemplate.feignTarget();
        if (target != null && StrUtil.isNotBlank(target.name()) && IMsUmappServiceConstants.MS_UMAPP_SERVICE_EVALUATE.equals(target.name())) {
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            log.debug("apply attributes is null");
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        log.debug("调用feign传递header携带token");
        // 只携带token
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        // requestTemplate.header("Authorization", authorization);
        log.debug("Authorization :\t\t" + authorization);
        // 携带全部
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                // 跳过 content-length
                if (name.equals("content-length")) {
                    continue;
                }
                requestTemplate.header(name, values);
            }
        }
        Enumeration<String> paramNames = request.getParameterNames();
        if (paramNames != null) {
            while (paramNames.hasMoreElements()) {
                String name = paramNames.nextElement();
                String values = request.getParameter(name);
                requestTemplate.query(name, values);
            }
        }
    }
}
