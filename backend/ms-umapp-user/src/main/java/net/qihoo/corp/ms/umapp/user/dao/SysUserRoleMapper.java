package net.qihoo.corp.ms.umapp.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysUser;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author wangchuanhai
 * @since 2021-07-30
 */
@Mapper
@SuppressWarnings("all")
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户ID批量删除用户关系
     *
     * @param removeList
     * @return
     */
    int deleteByUserId(@Param("removeList") List<SysUser> removeList);

    /**
     * 批量添加用户角色关系
     *
     * @param userRoleList
     * @return
     */
    int addUserRoleByList(@Param("userRoleList") List<SysUserRole> userRoleList);

}
