package net.qihoo.corp.ms.umapp.feign.user.fallback;

import net.qihoo.corp.ms.umapp.common.feign.config.IMsUmappServiceConstants;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.MsUmappAuthUserDetails;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysUser;
import net.qihoo.corp.ms.umapp.feign.user.feign.IMsUmappUserFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.user.fallback
 * @ClassName: MsUmappUserFeignFallback
 * @Description:
 * @date 2022-10-12 14:56:37
 */
@Component
@SuppressWarnings("all")
public class MsUmappUserFeignFallback implements IMsUmappUserFeign {

    @Override
    public MsUmappBaseResult getUserByUserId(Integer userId) {
        return MsUmappBaseResult.hystrixError(IMsUmappServiceConstants.MS_UMAPP_USER,"getUserByUserId");
    }

    @Override
    public MsUmappBaseResult<SysUser> getUserByAccount(String account) {
        return MsUmappBaseResult.hystrixError(IMsUmappServiceConstants.MS_UMAPP_USER,"getUserByAccount");
    }

    @Override
    public MsUmappBaseResult<MsUmappAuthUserDetails> findByUsername(String username) {
        return MsUmappBaseResult.hystrixError(IMsUmappServiceConstants.MS_UMAPP_USER,"findByUsername");
    }

    @Override
    public MsUmappBaseResult<SysUser> findUserByUserId(Integer userId) {
        return MsUmappBaseResult.hystrixError(IMsUmappServiceConstants.MS_UMAPP_USER,"findUserByUserId");
    }

    @Override
    public MsUmappBaseResult<List<SysUser>> findUserListByUserNames(String usernames) {
        return MsUmappBaseResult.hystrixError(IMsUmappServiceConstants.MS_UMAPP_USER,"findUserListByUserNames");
    }

    @Override
    public MsUmappBaseResult<SysUser> doAddUser(SysUser sysUser) {
        return MsUmappBaseResult.hystrixError(IMsUmappServiceConstants.MS_UMAPP_USER,"doAddUser");
    }
}
