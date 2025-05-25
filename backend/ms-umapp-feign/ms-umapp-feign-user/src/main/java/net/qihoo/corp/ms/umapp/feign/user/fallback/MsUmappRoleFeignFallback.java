package net.qihoo.corp.ms.umapp.feign.user.fallback;

import net.qihoo.corp.ms.umapp.common.feign.config.IMsUmappServiceConstants;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.feign.IMsUmappRoleFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.user.fallback
 * @ClassName: MsUmappRoleFeignFallback
 * @Description:
 * @date 2022-10-12 14:55:18
 */
@Component
@SuppressWarnings("all")
public class MsUmappRoleFeignFallback implements IMsUmappRoleFeign {

    @Override
    public MsUmappBaseResult<List<String>> findRoleCodesByUserId(Integer userId) {
        return MsUmappBaseResult.hystrixError(IMsUmappServiceConstants.MS_UMAPP_USER,"findRoleCodesByUserId");
    }
}
