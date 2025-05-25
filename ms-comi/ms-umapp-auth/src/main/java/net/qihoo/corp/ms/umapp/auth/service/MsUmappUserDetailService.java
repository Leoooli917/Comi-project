package net.qihoo.corp.ms.umapp.auth.service;

import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.MsUmappAuthUserDetails;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.MsUmappUserDetails;
import net.qihoo.corp.ms.umapp.feign.user.feign.IMsUmappPermissionFeign;
import net.qihoo.corp.ms.umapp.feign.user.feign.IMsUmappRoleFeign;
import net.qihoo.corp.ms.umapp.feign.user.feign.IMsUmappUserFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.auth.service
 * @ClassName: MsUmappUserDetailService
 * @Description:
 * @date 2022-10-12 14:21:15
 */
@Component
@SuppressWarnings("all")
public class MsUmappUserDetailService implements UserDetailsService {

    @Autowired
    IMsUmappUserFeign umappUserFeign;
    @Autowired
    IMsUmappRoleFeign umappRoleFeign;
    @Autowired
    IMsUmappPermissionFeign umappPermissionFeign;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 用户基础数据加载
        MsUmappBaseResult<MsUmappAuthUserDetails> userDetailsResult = umappUserFeign.findByUsername(username);
        MsUmappAuthUserDetails authUserDetails = userDetailsResult.getData();
        MsUmappUserDetails userDetails = new MsUmappUserDetails();
        BeanUtils.copyProperties(authUserDetails, userDetails);
        if (userDetails == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 用户角色列表
        MsUmappBaseResult<List<String>> roleResult = umappRoleFeign.findRoleCodesByUserId(userDetails.getId());
        if (roleResult == null || roleResult.getErrCode() != 0) {
            throw new UsernameNotFoundException("用户角色不存在");
        }
        List<String> roleCodes = (List<String>) roleResult.getData();
        String codeStr = "";
        if (!CollectionUtils.isEmpty(roleCodes)) {
            codeStr = roleCodes.stream().collect(Collectors.joining(","));
        }

        // 当前用户拥有的权限
//        MsUmappBaseResult<List<String>> authoritiesResult = umappPermissionFeign.findPermissionsByCodes(codeStr);
//        if (authoritiesResult == null || authoritiesResult.getErrCode() != 0) {
//            throw new UsernameNotFoundException("角色权限不存在");
//        }
//        List<String> authorities = (List<String>) authoritiesResult.getData();

//        List<String> roleCodeList = (List<String>) roleCodes;
//        roleCodeList = roleCodes.stream()
//                .map(rc -> "ROLE_" + rc)
//                .collect(Collectors.toList());

        List<String> roleList = new ArrayList<>();
        roleList.add("ROLE_ADMIN");
//        authorities.addAll(roleList);
        userDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", roleList)));
        return userDetails;
    }
}
