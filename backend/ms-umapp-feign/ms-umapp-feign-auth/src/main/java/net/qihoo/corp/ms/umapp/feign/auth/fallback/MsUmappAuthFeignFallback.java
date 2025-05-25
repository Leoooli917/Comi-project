package net.qihoo.corp.ms.umapp.feign.auth.fallback;

import net.qihoo.corp.ms.umapp.common.feign.config.IMsUmappServiceConstants;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.auth.entity.req.MsUmappAuthCheckTokenReq;
import net.qihoo.corp.ms.umapp.feign.auth.feign.IMsUmappAuthFeign;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.auth.fallback
 * @ClassName: MsUmappAuthFeignFallback
 * @Description:
 * @date 2022-10-13 11:28:56
 */
@Component
@SuppressWarnings("all")
public class MsUmappAuthFeignFallback implements IMsUmappAuthFeign {

    @Override
    public MsUmappBaseResult getCurrentUserId() {
        return MsUmappBaseResult.hystrixError(IMsUmappServiceConstants.MS_UMAPP_AUTH, "getCurrentUserId");
    }

    @Override
    public Object getToken(String username, String password, String clientId, String clientSecret, String grantType) {
        return MsUmappBaseResult.hystrixError(IMsUmappServiceConstants.MS_UMAPP_AUTH, "getToken");
    }

    @Override
    public Map<String, ?> checkToken(MsUmappAuthCheckTokenReq checkTokenReq) {
        Map<String, String> map = new HashMap<>();
        map.put(IMsUmappServiceConstants.MS_UMAPP_AUTH, "getToken");
        return map;
    }
}
