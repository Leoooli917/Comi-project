package net.qihoo.corp.ms.umapp.feign.user.entity.model;

import lombok.Data;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName:net.qihoo.corp.umapp.auth.model
 * @ClassName:MsUmappAuthUserDetails
 * @Description:
 * @date 2021/8/1 10:16 下午
 */
@Data
public class MsUmappAuthUserDetails {

    /**
     * 用户id查询角色用
     */
    Integer id;
    /**
     * 用户名
     */
    String username;
    /**
     * 密码
     */
    String password;
    /**
     * 账号是否可用
     */
    boolean enabled;
    /**
     * 用户权限集合
     */
    List<String> authorities;
}
