package net.qihoo.corp.ms.umapp.feign.technology.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyUserWithOutTokenResp {
    private Integer userId;
    private String userPhoto;
    private String userName;
    private String realName;
}
