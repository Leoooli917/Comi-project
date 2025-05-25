package net.qihoo.corp.ms.umapp.feign.anniversary.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.model
 * @ClassName: MsUmappAnnUserSign
 * @Description:
 * @date 2021/11/28 8:59 上午
 */
@Data
@Accessors(chain = true)
public class MsUmappAnnUserSign {
    private Integer id;
    private Integer userId;
    private Date signTime;
    private Integer signType;
    private Integer signSource;
}
