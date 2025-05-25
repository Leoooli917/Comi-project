package net.qihoo.corp.ms.umapp.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysRole;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.service.user.service
 * @ClassName: IRoleService
 * @Description:
 * @date 2021/11/16 4:51 下午
 */
@SuppressWarnings("all")
public interface IRoleService extends IService<SysRole>  {

    /**
     * 根据用户id获取角色code
     * @param userId
     * @return
     */
    MsUmappBaseResult findRoleCodesByUserId(Integer userId);
}
