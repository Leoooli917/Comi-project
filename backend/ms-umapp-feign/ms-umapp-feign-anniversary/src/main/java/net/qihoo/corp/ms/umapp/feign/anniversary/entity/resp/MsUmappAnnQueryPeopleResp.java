package net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp
 * @ClassName: MsUmappAnnQueryPeopleResp
 * @Description:
 * @date 2021/11/23 5:24 下午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnQueryPeopleResp {
    private long id;
    private String reportDate;
    private long psId;
    private String psPicUrl;
    private String deviceId;
    private String updateDate;
    private int alarmName;
    private long inNum;
    private long outNum;
    private String roiName;
    private long projectId;
    private String projectName;
    private long companyId;
    private String companyName;
    private String dNickname;
}
