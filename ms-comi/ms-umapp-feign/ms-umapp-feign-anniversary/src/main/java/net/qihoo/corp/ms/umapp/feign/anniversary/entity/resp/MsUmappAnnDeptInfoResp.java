package net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp
 * @ClassName: MsUmappAnnDeptInfoResp
 * @Description:
 * @date 2021/11/28 9:55 上午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnDeptInfoResp {
    private Integer id;
    private String deptName;
    private String deptNo;
    private boolean actived;
}
