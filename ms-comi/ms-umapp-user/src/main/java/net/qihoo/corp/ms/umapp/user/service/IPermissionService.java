package net.qihoo.corp.ms.umapp.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysPermission;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.user.service
 * @ClassName: IPermissionService
 * @Description:
 * @date 2021/11/16 4:51 下午
 */
@SuppressWarnings("all")
public interface IPermissionService extends IService<SysPermission> {

    /**
     * 根据用户角色列表获取角色code
     *
     * @param roleCodes
     * @return
     */
    MsUmappBaseResult findPermissionsByCodes(String codeStr);
}
