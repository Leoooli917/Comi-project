package net.qihoo.corp.ms.umapp.feign.evaluate.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 签名基础类
 *
 * @author LBin
 * @date 2024-01-16 18:26:59
 */
@Data
@Accessors(chain = true)
public class SignModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 时间戳
     */
    private Integer t;

    /**
     * 用户邮箱
     */
    private String user_mail;

    /**
     * 签名
     */
    private String sign;
}
