package net.qihoo.corp.ms.umapp.feign.user.feign;

import net.qihoo.corp.ms.umapp.common.feign.config.IMsUmappServiceConstants;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.fallback.MsUmappRoleFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.user.feign
 * @ClassName: IMsUmappRoleFeign
 * @Description:
 * @date 2022-10-12 14:54:30
 */
@SuppressWarnings("all")
@FeignClient(name= IMsUmappServiceConstants.MS_UMAPP_USER, fallback= MsUmappRoleFeignFallback.class)
public interface IMsUmappRoleFeign {

    @GetMapping("/ms/umapp/role/findRoleCodesByUserId/{userId}")
    MsUmappBaseResult<List<String>> findRoleCodesByUserId(@PathVariable("userId") Integer userId);
}
