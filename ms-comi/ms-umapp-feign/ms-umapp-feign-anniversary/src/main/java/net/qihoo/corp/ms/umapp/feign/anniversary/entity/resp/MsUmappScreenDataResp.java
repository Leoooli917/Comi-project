package net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp
 * @ClassName: MsUmappScreenDataResp
 * @Description:
 * @date 2021/12/1 2:56 上午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappScreenDataResp {
    private Integer areaNum;
    private Integer productNum;
    private Integer passedUserNum;
    private long currentUserNum;
    private Integer joinedNum;
    private Integer awardedUserNum;
}
