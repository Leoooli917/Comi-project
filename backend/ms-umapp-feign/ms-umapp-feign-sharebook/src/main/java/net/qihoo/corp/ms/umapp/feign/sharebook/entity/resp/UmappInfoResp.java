package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.service.api.entity.resp
 * @ClassName: UmappDetailResp
 * @Description:
 * @date 2021/8/20 2:34 下午
 */
@Data
@Accessors(chain = true)
public class UmappInfoResp {
    private Integer umappInfoId;
    private String umappInfoName;
    private String appId;
    private String umappDomain;
    private String umappIcon;
    private String umappDesc;
    private Integer commonStatus;
    private Integer praiseNum;
    private Integer myPraiseNum;
}
