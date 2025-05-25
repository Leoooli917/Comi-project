package net.qihoo.corp.ms.umapp.feign.todolist.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.feign.todolist.entity.model.MsUmappServiceTodoProject;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhanglizeng
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.service.todolist.model
 * @ClassName: TodoProjectFlag
 * @Description:
 * @date 2021/11/11 22:30
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappServiceTodoProjectFlag implements Serializable {
    // 日期字符串，2021-11-12
    private String dateStr;
    // 是否有待办的标识，0：没有，1：有
    private Integer hasTodoFlag;
    // 有待办的颜色标识， gray：过去日期，bright：将来日期，null:没有待办颜色，今天不做处理
    private String todoColorFlag;
    // 当天完成的数量
    private Integer finishNum;
    // 待办事项集合
    private List<MsUmappServiceTodoProject> todoProjectList;

}
