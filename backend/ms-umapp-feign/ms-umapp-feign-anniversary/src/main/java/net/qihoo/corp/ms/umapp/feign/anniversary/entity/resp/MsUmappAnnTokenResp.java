package net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp
 * @ClassName: MsUmappAnnTokenResp
 * @Description:
 * @date 2021/11/23 2:22 下午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnTokenResp {
    private String access_token;
    private int expires_in;
}
