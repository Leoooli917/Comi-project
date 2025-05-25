package net.qihoo.corp.ms.umapp.feign.anniversary.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.req
 * @ClassName: MsUmappAnnLotterySyncReq
 * @Description:
 * @date 2021/12/2 3:57 下午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnLotterySyncReq {
    private String domainId;
    private String prizeName;
    private String winningTime;
    private String sign;
}
