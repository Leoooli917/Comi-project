package net.qihoo.corp.ms.umapp.feign.anniversary.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.model
 * @ClassName: MsUmappAnnDevicePersonsInfo
 * @Description:
 * @date 2021/11/30 1:28 下午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnDevicePersonsInfo {
    private Integer id;
    private Integer inNum;
    private Integer outNum;
    private String reportTime;
    private Date createdTime;
    private Date updatedTime;
}
