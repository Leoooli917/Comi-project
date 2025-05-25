package net.qihoo.corp.ms.umapp.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.MsUmappAuthUserDetails;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysUser;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.service.usercenter.service
 * @ClassName: IUserService
 * @Description:
 * @date 2021/9/15 1:20 下午
 */
@SuppressWarnings("all")
public interface IUserService extends IService<SysUser> {

    /**
     * 根据用户名获取用户信息 -- 小程序登录
     *
     * @param username
     * @return
     */
    MsUmappBaseResult<SysUser> loadUserByName(String username);

    /**
     * 根据username获取用户信息，oauth授权使用
     *
     * @param username
     * @return
     */
    MsUmappBaseResult<MsUmappAuthUserDetails> findByUsername(String username);

    MsUmappBaseResult<List<SysUser>> findUserListByUserNames(String usernames);

    MsUmappBaseResult<SysUser> findUserByUserId(Integer userId);

    /**
     * 添加用户 -- feign调用
     *
     * @param sysUser
     * @return
     */
    MsUmappBaseResult<SysUser> doAddUser(SysUser sysUser);
}
