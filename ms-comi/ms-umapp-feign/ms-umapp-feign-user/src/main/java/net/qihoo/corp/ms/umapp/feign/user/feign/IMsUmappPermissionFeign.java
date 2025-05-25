package net.qihoo.corp.ms.umapp.feign.user.feign;

import net.qihoo.corp.ms.umapp.common.feign.config.IMsUmappServiceConstants;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.fallback.MsUmappPermissionFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.user.feign
 * @ClassName: IMsUmappPermissionFeign
 * @Description:
 * @date 2022-10-12 14:39:39
 */
@SuppressWarnings("all")
@FeignClient(name = IMsUmappServiceConstants.MS_UMAPP_AUTH, fallback = MsUmappPermissionFeignFallback.class)
public interface IMsUmappPermissionFeign {

    @GetMapping("/umapp/permission/findPermissionsByCodes/{codeStr}")
    MsUmappBaseResult<List<String>> findPermissionsByCodes(@PathVariable("codeStr") String codeStr);
}
