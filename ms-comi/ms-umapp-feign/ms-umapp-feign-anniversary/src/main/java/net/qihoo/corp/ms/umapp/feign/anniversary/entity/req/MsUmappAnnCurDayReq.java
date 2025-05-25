package net.qihoo.corp.ms.umapp.feign.anniversary.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.req
 * @ClassName: MsUmappAnnCurDayReq
 * @Description:
 * @date 2021/11/23 4:15 下午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnCurDayReq extends MsUmappAnnBaseReq {
    private String deviceId;
    private Integer projectId;
}
