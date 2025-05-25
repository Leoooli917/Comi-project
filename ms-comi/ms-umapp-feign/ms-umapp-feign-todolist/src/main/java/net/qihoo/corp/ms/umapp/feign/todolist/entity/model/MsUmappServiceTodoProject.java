package net.qihoo.corp.ms.umapp.feign.todolist.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

import java.util.Date;
import java.util.List;

/**
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @Description 待办列表
 * @email 1472052711@qq.com
 * @date 2021-11-10 22:39:29
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappServiceTodoProject extends MsUmappBaseModel {

    // 事项标题
    private String todoTitle;
    // 背景信息
    private String backMessage;
    // 待办开始时间
    private Date startTime;
    // 待办结束时间
    private Date endTime;
    // 是否提醒，0：不提醒，1：提醒
    private Integer noticeFlag;
    // 完成状态（0：未完成，1：完成，2：已延期）
    private Integer todoStatus;
    // 创建人id
    private Integer userId;
    // 创建人域账号
    private String userCode;
    // 创建人名字
    private String userName;
    // 推推用户主键id
    private String ttUserId;
    // 完成时间
    private Date finishTime;
    // 日期提示
    private String dateTip;
    // 文件集合
    private List<MsUmappServiceTodoFile> fileList;
    // 协办人集合
    private List<MsUmappServiceTodoAssistPerson> assistPersonList;
    // 子事项集合
    private List<MsUmappServiceTodoItem> itemList;
    // 完成或者截止时间等提示信息
    private String finishTip;
    // 自己的头像地址
    private String myHeadPicUrl;
    // 是否属于我的项目标识，0不属于，1属于，属于才可以进行编辑等操作
    private Integer belongMyFlag;


}
