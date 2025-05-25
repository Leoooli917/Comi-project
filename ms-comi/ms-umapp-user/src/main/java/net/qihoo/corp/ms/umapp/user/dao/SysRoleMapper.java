package net.qihoo.corp.ms.umapp.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysRole;
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
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id获取用户角色列表
     *
     * @param userId
     * @return
     */
    List<String> findRoleCodesByUserId(@Param("userId") Integer userId);

}
