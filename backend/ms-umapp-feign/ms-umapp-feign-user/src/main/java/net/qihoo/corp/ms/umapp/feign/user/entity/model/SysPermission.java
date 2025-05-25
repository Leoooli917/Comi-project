package net.qihoo.corp.ms.umapp.feign.user.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * <p>
 * 系统权限表
 * </p>
 *
 * @author wangchuanhai
 * @since 2021-07-30
 */
@Data
@Accessors(chain = true)
public class SysPermission extends MsUmappBaseModel {

    /**
     * 上级权限ID
     */
    private Integer parentId;

    /**
     * 所有上级权限ID
     */
    private String parentIds;

    /**
     * 是否叶子节点
     */
    private Integer isLeaf;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限唯一CODE代码
     */
    private String code;

    /**
     * 权限类别，1-菜单 2-按钮 3-其他
     */
    private Integer category;

    /**
     * 请求的URL，可以填正则表达式
     */
    private String url;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 状态，0-无效 1-有效
     */
    private Integer status;

}
