package net.qihoo.corp.ms.umapp.feign.user.fallback;

import net.qihoo.corp.ms.umapp.common.feign.config.IMsUmappServiceConstants;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.feign.IMsUmappPermissionFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.user.fallback
 * @ClassName: MsUmappPermissionFallback
 * @Description:
 * @date 2022-10-12 14:38:53
 */
@Component
@SuppressWarnings("all")
public class MsUmappPermissionFeignFallback implements IMsUmappPermissionFeign {

    @Override
    public MsUmappBaseResult<List<String>> findPermissionsByCodes(String codeStr) {
        return MsUmappBaseResult.hystrixError(IMsUmappServiceConstants.MS_UMAPP_USER, "findPermissionsByCodes");
    }
}
