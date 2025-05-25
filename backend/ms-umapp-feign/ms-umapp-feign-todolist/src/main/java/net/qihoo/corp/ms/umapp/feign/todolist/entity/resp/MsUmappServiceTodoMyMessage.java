package net.qihoo.corp.ms.umapp.feign.todolist.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zhanglizeng
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.service.todolist.model
 * @ClassName: MyMessage
 * @Description:
 * @date 2021/11/20 19:21
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappServiceTodoMyMessage implements Serializable {
    // 日期
    private String dateStr;
    // 标题
    private String title;
    // 描述
    private String descMessage;
}
