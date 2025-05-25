package net.qihoo.corp.ms.umapp.feign.user.entity.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName:net.qihoo.corp.umapp.auth.model
 * @ClassName:MsUmappUserDetails
 * @Description:
 * @date 2021/8/1 10:16 下午
 */
@Data
public class MsUmappUserDetails implements UserDetails {

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
    Collection<? extends GrantedAuthority> authorities;

    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
