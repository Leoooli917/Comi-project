package net.qihoo.corp.ms.umapp.feign.todolist.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * @author zhanglizeng
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.service.gamevote.model
 * @ClassName: TodoHeadPic
 * @Description:
 * @date 2021/11/17 21:41
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappServiceTodoHeadPic extends MsUmappBaseModel {

    // 用户id
    private Integer userId;
    // 用户头像url
    private String headPicUrl;
    // 推推用户主键id
    private String ttUserId;


}
