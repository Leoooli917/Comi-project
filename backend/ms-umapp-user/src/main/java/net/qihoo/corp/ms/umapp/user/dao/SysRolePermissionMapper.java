package net.qihoo.corp.ms.umapp.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;

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
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {


}
