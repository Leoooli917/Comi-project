package net.qihoo.corp.ms.umapp.feign.technology.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyUserResp {
    private Integer userId;
    private String userPhoto;//用户头像
    private String accessToken ;
    private String userName;//
    private String realName;//用户名称

}
