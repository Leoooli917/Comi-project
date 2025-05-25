package net.qihoo.corp.ms.umapp.common.login.service;

import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.common.login.entity.req.MsUmappCCWorkCheckSignReq;
import net.qihoo.corp.ms.umapp.common.login.entity.resp.MsUmappUserLoginResp;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.login.service
 * @ClassName: IMsUmappLoginService
 * @Description:
 * @date 2022-10-13 11:46:28
 */
public interface IMsUmappLoginService {

    /**
     * 推推小程序登录
     *
     * @param umappCCWorkCheckSignReq
     * @return
     */
    MsUmappBaseResult<MsUmappUserLoginResp> ttLogin(MsUmappCCWorkCheckSignReq umappCCWorkCheckSignReq);

    /**
     * 外网构造auth
     *
     * @param uid
     * @return
     */
    String  getAuth(String uid);
}
