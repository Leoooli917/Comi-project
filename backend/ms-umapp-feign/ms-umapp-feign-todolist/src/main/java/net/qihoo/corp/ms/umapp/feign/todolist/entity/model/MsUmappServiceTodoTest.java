package net.qihoo.corp.ms.umapp.feign.todolist.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * @author zhanglizeng
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.todolist.entity.model
 * @ClassName: MsUmappServiceTodoTest
 * @Description:
 * @date 2021/10/14 14:10
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappServiceTodoTest extends MsUmappBaseModel {

    /**
     * @Description: name字段
     * @Author: zhanglizeng
     * @Param:
     * @Return:
     * @Date: 2021/10/14 14:12
     */
    private String name;

    /**
     * @Description: age字段
     * @Author: zhanglizeng
     * @Param:
     * @Return:
     * @Date: 2021/10/14 14:12
     */
    private Integer age;

    /**
     * @Description: sex字段
     * @Author: zhanglizeng
     * @Param:
     * @Return:
     * @Date: 2021/10/14 14:12
     */
    private String sex;


}
