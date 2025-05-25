package net.qihoo.corp.ms.umapp.feign.anniversary.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.model
 * @ClassName: MsUmappAnnUserInfo
 * @Description:
 * @date 2021/11/24 11:02 上午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnUserInfo extends MsUmappBaseModel {
    private String userName;
    private String password;
}
