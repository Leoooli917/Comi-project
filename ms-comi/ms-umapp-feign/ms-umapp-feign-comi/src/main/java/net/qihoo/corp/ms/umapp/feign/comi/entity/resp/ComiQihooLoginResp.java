package net.qihoo.corp.ms.umapp.feign.comi.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.comi.entity.resp
 * @ClassName: ComiQihooLoginResp
 * @Description: TODO
 * @date 2024/4/24 15:38
 */
@Data
@Accessors(chain = true)
public class ComiQihooLoginResp {
    private String mail;
    private String memberOf;
    private String user;
    private String display;
}
