package net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.service.anniversary.entity.resp
 * @ClassName: UmappAnnDeptResp
 * @Description:
 * @date 2021/11/28 11:30 上午
 */
@Data
@Accessors(chain = true)
public class MsUmappAnnDeptResp {
    private List<MsUmappAnnDeptInfoResp> deptList;
    private boolean prized;
    private boolean opportunitied;
}
