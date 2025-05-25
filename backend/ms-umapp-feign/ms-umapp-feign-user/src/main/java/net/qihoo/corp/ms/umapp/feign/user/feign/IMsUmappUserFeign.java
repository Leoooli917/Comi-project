package net.qihoo.corp.ms.umapp.feign.user.feign;

import net.qihoo.corp.ms.umapp.common.feign.config.IMsUmappServiceConstants;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.MsUmappAuthUserDetails;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysUser;
import net.qihoo.corp.ms.umapp.feign.user.fallback.MsUmappUserFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.user.feign
 * @ClassName: IMsUmappUserFeign
 * @Description:
 * @date 2022-10-12 14:56:20
 */
@SuppressWarnings("all")
@FeignClient(name = IMsUmappServiceConstants.MS_UMAPP_USER, fallback = MsUmappUserFeignFallback.class)
public interface IMsUmappUserFeign {

    @GetMapping("/ms/umapp/user/getUserByUserId/{userId}")
    MsUmappBaseResult getUserByUserId(@PathVariable Integer userId);

    @GetMapping("/ms/umapp/user/getUserByAccount/{account}")
    MsUmappBaseResult<SysUser> getUserByAccount(@PathVariable("account") String account);

    @GetMapping("/ms/umapp/user/findByUsername/{username}")
    MsUmappBaseResult<MsUmappAuthUserDetails> findByUsername(@PathVariable("username") String username);

    @GetMapping("/ms/umapp/user/findUserByUserId/{userId}")
    MsUmappBaseResult<SysUser> findUserByUserId(@PathVariable("userId") Integer userId);

    @PostMapping("/ms/umapp/user/findUserListByUserNames")
    MsUmappBaseResult<List<SysUser>> findUserListByUserNames(@RequestParam("usernames") String usernames);

    @PostMapping("/ms/umapp/user/doAddUser")
    MsUmappBaseResult<SysUser> doAddUser(@RequestBody SysUser sysUser);
}
