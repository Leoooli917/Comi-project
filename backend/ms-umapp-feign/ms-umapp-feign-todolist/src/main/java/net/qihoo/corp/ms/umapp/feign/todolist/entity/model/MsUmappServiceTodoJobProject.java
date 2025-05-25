package net.qihoo.corp.ms.umapp.feign.todolist.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

import java.util.Date;

/**
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @Description 待办清单与定时器任务主键关联表
 * @email 1472052711@qq.com
 * @date 2021-11-10 22:39:30
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappServiceTodoJobProject extends MsUmappBaseModel {

    // job任务的id
    private Integer jobId;
    // 待办清单的id
    private Integer projectId;
    // 定时时间
    private Date alarmTime;

}
