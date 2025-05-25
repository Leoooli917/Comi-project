package net.qihoo.corp.ms.umapp.feign.anniversary.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.model
 * @ClassName: MsUmappAnnUserDeptAction
 * @Description:
 * @date 2021/11/28 9:00 上午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnUserDeptAction extends MsUmappBaseModel {
    private Integer userId;
    private Integer deptId;
    private Integer status;
    private Integer deptSource;
}
