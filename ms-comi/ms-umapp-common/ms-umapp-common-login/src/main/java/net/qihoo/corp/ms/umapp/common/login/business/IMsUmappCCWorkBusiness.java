package net.qihoo.corp.ms.umapp.common.login.business;

import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.common.login.entity.req.MsUmappCCWorkCheckSignReq;
import net.qihoo.corp.ms.umapp.common.login.entity.resp.MsUmappCCWorkCheckSignResp;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.login.service
 * @ClassName: IMsUmappCCWorkBusiness
 * @Description:
 * @date 2022-10-13 11:45:09
 */
public interface IMsUmappCCWorkBusiness {

    /**
     * 获取系统用户手机号
     *
     * @param umappCCWorkCheckSignReq
     * @return
     */
    MsUmappBaseResult<MsUmappCCWorkCheckSignResp> getAccount(MsUmappCCWorkCheckSignReq umappCCWorkCheckSignReq);
}
