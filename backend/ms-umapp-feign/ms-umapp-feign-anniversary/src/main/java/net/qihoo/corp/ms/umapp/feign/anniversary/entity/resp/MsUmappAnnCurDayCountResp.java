package net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp
 * @ClassName: MsUmappAnnCurDayCountResp
 * @Description:
 * @date 2021/11/23 4:04 下午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnCurDayCountResp {
    private long inNum;
    private long outNum;
    private float inRate;
    private float outRate;
}
