package net.qihoo.corp.ms.umapp.common.login.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.login.entity.req
 * @ClassName: MsUmappCCWorkCheckSignReq
 * @Description:
 * @date 2022-10-13 11:37:41
 */
@Data
@Accessors(chain = true)
public class MsUmappCCWorkCheckSignReq {
    private String msgSignature;
    private String timeStamp;
    private String encrypt;
    private String nonce;
    private String ticket;
    private String appKey;
    private String token;
}
