package net.qihoo.corp.ms.umapp.feign.user.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author wangchuanhai
 * @since 2021-07-30
 */
@Data
@Accessors(chain = true)
public class SysUser extends MsUmappBaseModel {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户头像
     */
    private String userHeader;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 状态，0-无效 1-有效
     */
    private Integer status;

    /**
     * 来源，0-自行注册 1-EHR 2-其他
     */
    private Integer source;

}
