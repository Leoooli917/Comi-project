package net.qihoo.corp.ms.umapp.feign.comi.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.comi.entity.req
 * @ClassName: ComiUserLoginReq
 * @Description: TODO
 * @date 2024/4/24 14:19
 */
@Data
@Accessors(chain = true)
public class ComiUserLoginReq {
    private String sid;
    private String ref;
    private String from;
}
