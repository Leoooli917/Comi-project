package net.qihoo.corp.ms.umapp.user.controller;

import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.MsUmappAuthUserDetails;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysUser;
import net.qihoo.corp.ms.umapp.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.user.controller
 * @ClassName: MsUmappUserController
 * @Description:
 * @date 2022-10-14 21:22:04
 */
@RestController
@RequestMapping("/ms/umapp/user")
@SuppressWarnings("all")
public class MsUmappUserController {

    @Autowired
    IUserService userService;

    @GetMapping("/getUserByUserId/{userId}")
    public MsUmappBaseResult<SysUser> getUserById(@PathVariable("userId") Integer userId) {
        MsUmappBaseResult<SysUser> result = userService.findUserByUserId(userId);
        return result;
    }

    @GetMapping("/getUserByAccount/{account}")
    public MsUmappBaseResult<SysUser> getUserByAccount(@PathVariable("account") String account) {
        MsUmappBaseResult<SysUser> result = userService.loadUserByName(account);
        return result;
    }

    @GetMapping("/findByUsername/{username}")
    public MsUmappBaseResult<MsUmappAuthUserDetails> findByUserName(@PathVariable("username") String username) {
        MsUmappBaseResult<MsUmappAuthUserDetails> result = userService.findByUsername(username);
        return result;
    }

    /**
     * @Description: 根据域名拼接字符串获取用户集合的方法
     * @Author: zhanglizeng
     * @Param: [username]
     * @Return: net.qihoo.corp.umapp.common.base.results.Result<net.qihoo.corp.umapp.service.api.user.entity.model.AuthUserDetails>
     * @Date: 2021/11/20 22:22
     */
    @PostMapping("/findUserListByUserNames")
    public MsUmappBaseResult<List<SysUser>> findUserListByUserNames(@RequestParam("usernames") String usernames) {
        MsUmappBaseResult<List<SysUser>> result = userService.findUserListByUserNames(usernames);
        return result;
    }

    /**
     * @Description: 根据用户id获取用户信息
     * @Author: zhanglizeng
     * @Param: [username]
     * @Return: net.qihoo.corp.umapp.common.base.results.Result<net.qihoo.corp.umapp.service.api.user.entity.model.AuthUserDetails>
     * @Date: 2021/11/20 22:22
     */
    @GetMapping("/findUserByUserId/{userId}")
    public MsUmappBaseResult<SysUser> findUserByUserId(@PathVariable("userId") Integer userId) {
        MsUmappBaseResult<SysUser> result = userService.findUserByUserId(userId);
        return result;
    }

    /**
     * 添加用户信息， -- feign调用
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/doAddUser")
    public MsUmappBaseResult<SysUser> doAddUser(@RequestBody SysUser sysUser) {
        MsUmappBaseResult<SysUser> result = userService.doAddUser(sysUser);
        return result;
    }
}
