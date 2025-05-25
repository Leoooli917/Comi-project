package net.qihoo.corp.umapp.service.sharebook.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cnn
 */
@Data
@Accessors(chain = true)
public class ShareBookCCWorkCheckSignReq {
    private String msgSignature;
    private String timeStamp;
    private String encrypt;
    private String nonce;
    private String appid;

    @Override
    public String toString() {
        return "ShareBookCCWorkCheckSignReq{" +
                "msgSignature='" + msgSignature + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", nonce='" + nonce + '\'' +
                ", appid='" + appid + '\'' +
                '}';
    }
}
