package net.qihoo.corp.ms.umapp.common.login.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.login.entity.resp
 * @ClassName: MsUmappUserLoginResp
 * @Description:
 * @date 2022-10-13 11:39:24
 */
@Data
@Accessors(chain = true)
public class MsUmappUserLoginResp<T> {
    private String userName;
    private String realName;
    private String accessToken;
    private String refreshToken;
    private String headPicUrl;
    private T extra;
}
