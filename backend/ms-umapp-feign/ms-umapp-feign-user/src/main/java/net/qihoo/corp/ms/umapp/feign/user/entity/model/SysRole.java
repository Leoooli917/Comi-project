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
public class SysRole extends MsUmappBaseModel {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色唯一CODE代码
     */
    private String code;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色状态，0-无效 1-有效
     */
    private Integer status;

}
