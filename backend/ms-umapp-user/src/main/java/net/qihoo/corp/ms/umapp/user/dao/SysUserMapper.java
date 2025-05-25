package net.qihoo.corp.ms.umapp.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.MsUmappAuthUserDetails;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author wangchuanhai
 * @since 2021-07-30
 */
@Mapper
@SuppressWarnings("all")
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名获取用户，登录事件
     *
     * @param userName
     * @return
     */
    MsUmappAuthUserDetails findByUserName(@Param("userName") String userName);

    /**
     * 根据手机号获取用户信息
     *
     * @param phone
     * @return
     */
    SysUser loadUserByPhone(@Param("phone") String phone);

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    SysUser loadUserByName(@Param("username") String username);

    /**
     * 获取系统中的所有用户列表
     *
     * @return
     */
    List<SysUser> getAllUsers();

    /**
     * 批量删除用户信息
     *
     * @param userList
     * @return
     */
    int deleteUserList(@Param("userList") List<SysUser> userList);

    /**
     * 批量添加用户
     *
     * @param userList
     * @return
     */
    int addUserByList(@Param("userList") List<SysUser> userList);

    List<SysUser> findUserListByUserNames(@Param("userListParams") List<SysUser> userListParams);

    SysUser findUserByUserId(@Param("userId") Integer userId);
}
