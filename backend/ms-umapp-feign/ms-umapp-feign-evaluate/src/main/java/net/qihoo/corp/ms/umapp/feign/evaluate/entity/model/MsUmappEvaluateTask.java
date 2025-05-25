package net.qihoo.corp.ms.umapp.feign.evaluate.entity.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 评价任务表
 *
 * @author libin15
 * @version 1.0.0
 * @date 2024-01-16 03:58:12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MsUmappEvaluateTask extends MsUmappBaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务标题
     */
    private String taskTitle;

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
     * 极库云项目id
     */
    private String projectId;

    /**
     * 极库云项目名称
     */
    private String projectName;

    /**
     * 项目模式（1：普通，2：高级）
     */
    private String templateType;

    /**
     * 负责人域账号
     */
    private String manageUserName;

    /**
     * 负责人真实姓名
     */
    private String manageRealName;

    /**
     * 负责人真实姓名
     */
    private String manageMail;

    /**
     * 评价开始日期
     */
    private LocalDate evaStartDate;

    /**
     * 评价结束日期
     */
    private LocalDate evaEndDate;

    /**
     * 评价状态
     */
    private Integer evaStatus;


}