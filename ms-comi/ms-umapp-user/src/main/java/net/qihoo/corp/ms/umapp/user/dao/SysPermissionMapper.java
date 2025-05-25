package net.qihoo.corp.ms.umapp.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 系统权限表 Mapper 接口
 * </p>
 *
 * @author wangchuanhai
 * @since 2021-07-30
 */
@Mapper
@SuppressWarnings("all")
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据角色列表获取权限列表
     *
     * @param roleCodes
     * @return
     */
    List<String> findPermissionsByCodes(List<String> roleCodes);
}
