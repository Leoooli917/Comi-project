package net.qihoo.corp.ms.umapp.user.controller;

import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.user.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.user.controller
 * @ClassName: MsUmappUserRoleController
 * @Description:
 * @date 2022-10-14 21:24:56
 */
@RestController
@RequestMapping("/ms/umapp/role")
@SuppressWarnings("all")
public class MsUmappUserRoleController {

    @Autowired
    IRoleService roleService;

    @GetMapping("/findRoleCodesByUserId/{userId}")
    public MsUmappBaseResult findRoleCodesByUserId(@PathVariable("userId")Integer userId) {
        MsUmappBaseResult result = roleService.findRoleCodesByUserId(userId);
        return result;
    }
}
