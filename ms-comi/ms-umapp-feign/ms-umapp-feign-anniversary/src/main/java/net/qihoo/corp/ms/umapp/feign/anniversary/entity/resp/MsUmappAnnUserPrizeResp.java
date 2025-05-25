package net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.service.anniversary.entity.resp
 * @ClassName: UmappAnnUserPrizeResp
 * @Description:
 * @date 2021/11/28 11:07 上午
 */
@Data
@Accessors(chain = true)
public class MsUmappAnnUserPrizeResp {
    private Integer status;
    private String prizeName;
}
