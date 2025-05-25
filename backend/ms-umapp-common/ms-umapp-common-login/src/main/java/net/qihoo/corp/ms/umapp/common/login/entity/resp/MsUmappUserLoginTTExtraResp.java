package net.qihoo.corp.ms.umapp.common.login.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.login.entity.resp
 * @ClassName: MsUmappUserLoginTTExtraResp
 * @Description:
 * @date 2022-10-13 11:40:37
 */
@Data
@Accessors(chain = true)
public class MsUmappUserLoginTTExtraResp {
    private String ttUserId;      //推推用户主键id
    private String headPicUrl;    //推推用户头像
    private Integer userId;       //小程序用户主键id
}
