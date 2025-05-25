package net.qihoo.corp.ms.umapp.common.login.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.common.login.business.IMsUmappCCWorkBusiness;
import net.qihoo.corp.ms.umapp.common.login.entity.req.MsUmappCCWorkCheckSignReq;
import net.qihoo.corp.ms.umapp.common.login.entity.resp.MsUmappCCWorkCheckSignResp;
import net.qihoo.corp.ms.umapp.common.login.properties.MsUmappCCWorkSignProperties;
import net.qihoo.corp.ms.umapp.common.util.network.MsUmappHttpClientUtil;
import net.qihoo.corp.ms.umapp.common.util.safety.MsUmappAESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.login.service.impl
 * @ClassName: MsUmappCCWorkBusinessImpl
 * @Description:
 * @date 2022-10-13 11:49:58
 */
@Service
@Slf4j
public class MsUmappCCWorkBusinessImpl implements IMsUmappCCWorkBusiness {

    @Autowired
    MsUmappCCWorkSignProperties umappCCWorkSignProperties;

    @Override
    public MsUmappBaseResult<MsUmappCCWorkCheckSignResp> getAccount(MsUmappCCWorkCheckSignReq checkSignReq) {
        // 校验参数
        if (checkSignReq == null) {
            log.info("请求参数 {}", checkSignReq);
            return MsUmappBaseResult.error("请求参数异常~");
        }
        if (StringUtils.isEmpty(checkSignReq.getAppKey())) {
            log.info("AppKey异常 {}", checkSignReq.getAppKey());
            return MsUmappBaseResult.error("appkey为空");
        }
        if (StringUtils.isEmpty(checkSignReq.getNonce())) {
            log.info("nonce异常 {}", checkSignReq.getNonce());
            return MsUmappBaseResult.error("nonce为空");
        }
        if (StringUtils.isEmpty(checkSignReq.getEncrypt())) {
            log.info("Encrypt异常 {}", checkSignReq.getEncrypt());
            return MsUmappBaseResult.error("encrypt为空");
        }
        if (StringUtils.isEmpty(checkSignReq.getMsgSignature())) {
            log.info("msgSignature异常 {}", checkSignReq.getMsgSignature());
            return MsUmappBaseResult.error("msgSignature为空");
        }
        if (StringUtils.isEmpty(checkSignReq.getTimeStamp())) {
            log.info("timeStamp异常 {}", checkSignReq.getTimeStamp());
            return MsUmappBaseResult.error("timeStamp为空");
        }
        MsUmappCCWorkCheckSignResp checkSignResp = this.checkSign(checkSignReq);
        if (checkSignResp == null) {
            log.info("小程序校验签名失败");
            return MsUmappBaseResult.error("小程序校验签名失败");
        }
        if (StringUtils.isEmpty(checkSignResp.getAccount())) {
            log.info("不存在的用户 {}", checkSignResp.getAccount());
            return MsUmappBaseResult.error("不存在的用户");
        }
        return MsUmappBaseResult.ok(checkSignResp);
    }

    public MsUmappCCWorkCheckSignResp checkSign(MsUmappCCWorkCheckSignReq checkSignReq) {
        String ticket = "";
        try {
            String decrypt = MsUmappAESUtil.decrypt(checkSignReq.getAppKey(), checkSignReq.getToken(), checkSignReq.getMsgSignature(), checkSignReq.getTimeStamp(), checkSignReq.getNonce(), checkSignReq.getEncrypt());
            log.info("decrypt: {}", decrypt);
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(decrypt)) {
                JSONObject jsonObject = JSONObject.parseObject(decrypt);
                if (jsonObject.containsKey("ticket")) {
                    ticket = jsonObject.getString("ticket");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(ticket)) {
            return null;
        }
        log.info("ticket: {}", ticket);
        MsUmappCCWorkCheckSignResp checkSignResp = null;
        MsUmappCCWorkCheckSignReq checkSign = new MsUmappCCWorkCheckSignReq();
        checkSign.setTicket(ticket);
        checkSign.setNonce(checkSignReq.getNonce());
        String req = JSON.toJSONString(checkSign);
        log.info("req: {}", req);
        String result = "";
        try {
            String url = getCheckSignUrl();
            log.info("url: {}", url);
            result = MsUmappHttpClientUtil.doPost(url, req);
            checkSignResp = JSONObject.parseObject(result, MsUmappCCWorkCheckSignResp.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.info("req:{},resp:{}", req, result);
        return checkSignResp;
    }

    private String getCheckSignUrl() {
        return umappCCWorkSignProperties.getCheckSignDomain() + umappCCWorkSignProperties.getCheckSignUrl();
    }
}
