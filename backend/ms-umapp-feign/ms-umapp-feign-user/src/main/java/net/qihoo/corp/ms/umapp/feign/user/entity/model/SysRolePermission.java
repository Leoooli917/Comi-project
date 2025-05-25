package net.qihoo.corp.ms.umapp.feign.user.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author wangchuanhai
 * @since 2021-07-30
 */
@Data
@Accessors(chain = true)
public class SysRolePermission extends MsUmappBaseModel {

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限ID
     */
    private Integer permissonId;

    /**
     * 角色状态，0-无效 1-有效
     */
    private Integer status;

}
