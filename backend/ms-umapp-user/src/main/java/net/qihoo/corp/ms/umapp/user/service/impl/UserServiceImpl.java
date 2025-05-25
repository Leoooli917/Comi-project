package net.qihoo.corp.ms.umapp.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.MsUmappAuthUserDetails;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysUser;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysUserRole;
import net.qihoo.corp.ms.umapp.user.dao.SysUserMapper;
import net.qihoo.corp.ms.umapp.user.dao.SysUserRoleMapper;
import net.qihoo.corp.ms.umapp.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.auth.service.impl
 * @ClassName: UserServiceImpl
 * @Description:
 * @date 2021/9/15 1:21 下午
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements IUserService {

    @Autowired
    SysUserRoleMapper userRoleMapper;

    @Override
    public MsUmappBaseResult<SysUser> loadUserByName(String username) {
        if (StringUtils.isEmpty(username)) {
            log.info("用户名为空！");
            return MsUmappBaseResult.error("用户名为空!");
        }
        SysUser user = this.baseMapper.loadUserByName(username);
        return MsUmappBaseResult.ok(user);
    }

    @Override
    public MsUmappBaseResult<MsUmappAuthUserDetails> findByUsername(String username) {
        MsUmappAuthUserDetails userDetails = this.baseMapper.findByUserName(username);
        if (userDetails != null && !StringUtils.isEmpty(userDetails.getUsername())) {
            return MsUmappBaseResult.ok(userDetails);
        }
        return MsUmappBaseResult.error("不存在的用户！");
    }

    /**
     * @Description: 根据域名拼接字符串获取用户集合的方法
     * @Author: zhanglizeng
     * @Param: [username]
     * @Return: net.qihoo.corp.umapp.common.base.results.Result<net.qihoo.corp.umapp.service.api.user.entity.model.AuthUserDetails>
     * @Date: 2021/11/20 22:22
     */
    public MsUmappBaseResult<List<SysUser>> findUserListByUserNames(String usernames) {
        try {
            if (StringUtils.isEmpty(usernames)) {
                return MsUmappBaseResult.ok(null);
            }
            String[] userNameArr = usernames.split(",");
            List<SysUser> userListParams = new ArrayList<SysUser>();
            for (int i = 0; i < userNameArr.length; i++) {
                SysUser sysUser = new SysUser();
                sysUser.setUserName(userNameArr[i]);
                userListParams.add(sysUser);
            }
            List<SysUser> userList = this.baseMapper.findUserListByUserNames(userListParams);
            return MsUmappBaseResult.ok(userList);
        } catch (Exception e) {
            e.printStackTrace();
            return MsUmappBaseResult.error("服务器异常，请联系管理员或稍后重试");
        }
    }

    /**
     * @Description: 根据用户id获取用户信息
     * @Author: zhanglizeng
     * @Param: [username]
     * @Return: net.qihoo.corp.umapp.common.base.results.Result<net.qihoo.corp.umapp.service.api.user.entity.model.AuthUserDetails>
     * @Date: 2021/11/20 22:22
     */
    public MsUmappBaseResult<SysUser> findUserByUserId(Integer userId) {
        try {
            if (null == userId) {
                return MsUmappBaseResult.ok(null);
            }
            SysUser sysUser = this.baseMapper.findUserByUserId(userId);
            return MsUmappBaseResult.ok(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
            return MsUmappBaseResult.error("服务器异常，请联系管理员或稍后重试");
        }
    }

    @Transactional
    @Override
    public MsUmappBaseResult<SysUser> doAddUser(SysUser sysUser) {
        if (sysUser == null || StringUtils.isEmpty(sysUser.getUserName())) {
            log.info("用户数据为空 {}", sysUser);
            return MsUmappBaseResult.error("用户数据为空");
        }
        int result = this.baseMapper.insert(sysUser);
        if (result > 0) {
            // 添加用户角色关系
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getId()).setRoleId(2).setCreatedBy(0).setUpdatedBy(0);
            userRoleMapper.insert(sysUserRole);
        }
        return MsUmappBaseResult.ok(sysUser);
    }

}
