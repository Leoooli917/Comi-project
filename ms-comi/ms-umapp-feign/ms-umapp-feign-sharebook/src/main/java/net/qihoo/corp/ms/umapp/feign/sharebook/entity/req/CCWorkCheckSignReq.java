package net.qihoo.corp.umapp.service.sharebook.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cnn
 */
@Data
@Accessors(chain = true)
public class CCWorkCheckSignReq {
    private String nonce;
    private String ticket;


    @Override
    public String toString() {
        return "CCWorkCheckSignReq{" +
                "nonce='" + nonce + '\'' +
                ", ticket='" + ticket + '\'' +
                '}';
    }
}
