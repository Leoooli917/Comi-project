package net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp
 * @ClassName: MsUmappAnnOuterLotteryBaseResp
 * @Description:
 * @date 2021/12/6 3:18 下午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnOuterLotteryBaseResp {
    private boolean success;
    private Integer responseStatus;
    private Integer errorNo;
    private List<MsUmappAnnOuterLotteryResp> data;
    private Integer loginStatus;
}
