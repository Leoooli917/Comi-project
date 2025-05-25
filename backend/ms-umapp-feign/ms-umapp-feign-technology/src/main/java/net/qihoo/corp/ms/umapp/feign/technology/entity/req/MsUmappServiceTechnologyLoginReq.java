package net.qihoo.corp.ms.umapp.feign.technology.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappServiceTechnologyLoginReq {
    private String msgSignature;
    private String timeStamp;
    private String encrypt;
    private String nonce;
    private String appid;
}
