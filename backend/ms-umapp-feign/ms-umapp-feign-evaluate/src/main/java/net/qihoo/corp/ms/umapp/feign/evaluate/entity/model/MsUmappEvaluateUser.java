package net.qihoo.corp.ms.umapp.feign.evaluate.entity.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

import java.io.Serializable;

/**
 * 人员评价表
 *
 * @author libin15
 * @version 1.0.0
 * @date 2024-01-16 03:58:12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MsUmappEvaluateUser extends MsUmappBaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户域账号
     */
    private String userName;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 用户邮箱
     */
    private String userMail;

    /**
     * 用户头像
     */
    private String userPhoto;

    /**
     * 极库云个人token
     */
    private String personToken;

    /**
     * 用户所属部门
     */
    private String deptName;

    /**
     * 用户状态（A:有效,I:无效）
     */
    private String deptId;

    /**
     * 用户所属部门id
     */
    private String userStatus;


}