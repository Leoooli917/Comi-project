package net.qihoo.corp.ms.umapp.feign.todolist.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * @author 张立增[zhanglizeng] Tel：18860126570
 * @Description 待办子事项
 * @email 1472052711@qq.com
 * @date 2021-11-10 22:39:30
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappServiceTodoItem extends MsUmappBaseModel {
    // 待办事项主键id
    private Integer projectId;
    // 子事项集合
    private String itemContent;

}
