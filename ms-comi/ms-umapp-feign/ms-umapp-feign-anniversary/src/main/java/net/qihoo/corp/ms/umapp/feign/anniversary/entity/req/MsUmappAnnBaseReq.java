package net.qihoo.corp.ms.umapp.feign.anniversary.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.req
 * @ClassName: MsUmappAnnBaseReq
 * @Description:
 * @date 2021/11/23 2:21 下午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnBaseReq {

    private Long r;
    private String nonce;
    private String sign;
    private String token;
}
