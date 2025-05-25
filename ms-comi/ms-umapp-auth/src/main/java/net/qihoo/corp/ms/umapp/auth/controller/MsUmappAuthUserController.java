package net.qihoo.corp.ms.umapp.auth.controller;

import com.alibaba.fastjson.JSONObject;
import net.qihoo.corp.ms.umapp.common.core.constants.IMsUmappVersionConstant;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.auth.controller
 * @ClassName: MsUmappAuthUserController
 * @Description:
 * @date 2022-10-12 15:07:45
 */
@RestController
@RequestMapping("/oauth")
public class MsUmappAuthUserController {

    @GetMapping("/getCurrentUserId")
    public MsUmappBaseResult getCurrentUserId(Map map, Principal principal, Authentication authentication) {
        Object obj = authentication.getPrincipal();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(obj);
        return MsUmappBaseResult.ok(jsonObject.get("id"));
    }


    @GetMapping("/user")
    public Principal principal(Map map, Principal principal, Authentication authentication) {
        return principal;
    }
}
