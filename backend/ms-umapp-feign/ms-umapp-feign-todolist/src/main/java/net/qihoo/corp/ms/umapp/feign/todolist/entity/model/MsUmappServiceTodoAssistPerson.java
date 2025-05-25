package net.qihoo.corp.ms.umapp.feign.todolist.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @Description 待办清单协作人员
 * @email 1472052711@qq.com
 * @date 2021-11-10 22:39:30
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappServiceTodoAssistPerson extends MsUmappBaseModel {
    // 投票主键
    private Integer projectId;
    // 协办人员主键
    private Integer userId;
    // 用户域账号
    private String userCode;
    // 用户姓名
    private String userName;
    // 协办人员头像地址
    private String headPicUrl;
    // 推推系统的用户id
    private String ttUserId;


}
