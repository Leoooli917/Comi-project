package net.qihoo.corp.ms.umapp.feign.user.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.user.entity.resp
 * @ClassName: MsUmappUserLoginResp
 * @Description:
 * @date 2022-10-12 14:35:38
 */
@Data
@Accessors(chain = true)
public class MsUmappUserLoginResp {
    private String userName;
    private String accessToken;
    private String refreshToken;
}
