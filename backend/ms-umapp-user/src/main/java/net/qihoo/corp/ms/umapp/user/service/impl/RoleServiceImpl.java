package net.qihoo.corp.ms.umapp.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysRole;
import net.qihoo.corp.ms.umapp.user.dao.SysRoleMapper;
import net.qihoo.corp.ms.umapp.user.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.service.user.service.impl
 * @ClassName: RoleServiceImpl
 * @Description:
 * @date 2021/11/16 4:53 下午
 */
@Service
@SuppressWarnings("all")
public class RoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements IRoleService {

    @Override
    public MsUmappBaseResult<List<String>> findRoleCodesByUserId(Integer userId) {
        List<String> roleCodes = this.baseMapper.findRoleCodesByUserId(userId);
        if (!CollectionUtils.isEmpty(roleCodes)) {
            return MsUmappBaseResult.ok(roleCodes);
        }
        return MsUmappBaseResult.error("角色列表为空！");
    }
}
