package net.qihoo.corp.ms.umapp.feign.anniversary.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.req
 * @ClassName: MsUmappAnnQueryPeopleReq
 * @Description:
 * @date 2021/11/23 5:18 下午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnQueryPeopleReq extends MsUmappAnnBaseReq {
    private int pageNum;
    private int pageSize;
    private long startTime;
    private long endTime;
    private int alarmType;
    private String keyword;
    private List<String> aiDevList;
    private List<String> devList;
    private long projectId;
}
