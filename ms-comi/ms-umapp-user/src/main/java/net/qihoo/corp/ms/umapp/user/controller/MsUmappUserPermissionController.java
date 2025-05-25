package net.qihoo.corp.ms.umapp.user.controller;

import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.user.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.user.controller
 * @ClassName: MsUmappUserPermissionController
 * @Description:
 * @date 2022-10-14 21:26:38
 */
@RestController
@RequestMapping("/umapp/permission")
@SuppressWarnings("all")
public class MsUmappUserPermissionController {

    @Autowired
    IPermissionService permissionService;

    @GetMapping("/findPermissionsByCodes/{codeStr}")
    public MsUmappBaseResult<List<String>> findPermissionsByCodes(@PathVariable("codeStr") String codeStr) {
        MsUmappBaseResult<List<String>> result = permissionService.findPermissionsByCodes(codeStr);
        return result;
    }
}
