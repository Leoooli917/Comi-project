package net.qihoo.corp.ms.umapp.feign.todolist.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhanglizeng
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.service.api.entity.resp
 * @ClassName: UmappTodoUserLoginResp
 * @Description: 待办清单的用户登录返回实体
 * @date 2021/12/1 20:31
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappServiceTodoUserLoginResp {
    private String userName;
    private String realName;
    private String accessToken;
    private String refreshToken;
    // 推推用户头像
    private String headPicUrl;

}
