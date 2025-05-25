package net.qihoo.corp.ms.umapp.feign.anniversary.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.model
 * @ClassName: MsUmappAnnDeptInfo
 * @Description:
 * @date 2021/11/28 8:55 上午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnDeptInfo extends MsUmappBaseModel {

    private String deptName;
    private String deptNo;
    private Integer status;
}
