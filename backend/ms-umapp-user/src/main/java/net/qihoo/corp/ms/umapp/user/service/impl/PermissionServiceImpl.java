package net.qihoo.corp.ms.umapp.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysPermission;
import net.qihoo.corp.ms.umapp.user.dao.SysPermissionMapper;
import net.qihoo.corp.ms.umapp.user.service.IPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
public class PermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements IPermissionService {

    @Override
    public MsUmappBaseResult findPermissionsByCodes(String codeStr) {
        if (StringUtils.isEmpty(codeStr)) {
            return MsUmappBaseResult.error("角色列表为空");
        }
        String[] codeArr = codeStr.split(",");
        List<String> codeList = Arrays.stream(codeArr).collect(Collectors.toList());
        List<String> authorities = this.baseMapper.findPermissionsByCodes(codeList);
        if (!CollectionUtils.isEmpty(authorities)) {
            return MsUmappBaseResult.ok(authorities);
        }
        return MsUmappBaseResult.error("权限列表为空！");
    }
}
