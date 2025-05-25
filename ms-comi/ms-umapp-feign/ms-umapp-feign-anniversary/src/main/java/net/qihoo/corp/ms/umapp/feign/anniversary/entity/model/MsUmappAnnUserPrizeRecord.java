package net.qihoo.corp.ms.umapp.feign.anniversary.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.model
 * @ClassName: MsUmappAnnUserPrizeRecord
 * @Description:
 * @date 2021/11/28 8:57 上午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnUserPrizeRecord {

    private Integer id;
    private Integer userId;
    private Date createdTime;
    private Integer prizeSource;
}
