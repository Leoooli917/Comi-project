package net.qihoo.corp.ms.umapp.feign.auth.feign;

import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.common.feign.config.IMsUmappServiceConstants;
import net.qihoo.corp.ms.umapp.feign.auth.entity.req.MsUmappAuthCheckTokenReq;
import net.qihoo.corp.ms.umapp.feign.auth.fallback.MsUmappAuthFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.auth.feign
 * @ClassName: IMsUmappAuthFeign
 * @Description:
 * @date 2022-10-13 11:29:25
 */
@FeignClient(name = IMsUmappServiceConstants.MS_UMAPP_AUTH, fallback = MsUmappAuthFeignFallback.class)
public interface IMsUmappAuthFeign {

    @GetMapping("/oauth/getCurrentUserId")
    MsUmappBaseResult getCurrentUserId();

    @GetMapping("/oauth/token")
    Object getToken(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("client_id") String clientId, @RequestParam("client_secret") String clientSecret, @RequestParam("grant_type") String grantType);

    @PostMapping("/oauth/check_token")
    Map<String, ?> checkToken(@RequestBody MsUmappAuthCheckTokenReq checkTokenReq);
}
