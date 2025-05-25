package net.qihoo.corp.ms.umapp.common.util.message;

import com.alibaba.fastjson.JSONObject;
import net.qihoo.corp.ms.umapp.common.util.network.MsUmappHttpClientUtil;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.util.message
 * @ClassName: MsUmappRebotMessageUtil
 * @Description:
 * @date 2022-10-11 17:37:35
 */
public class MsUmappRebotMessageUtil {

    private static final String accessTokenUrl = "https://alarm.im.qihoo.net/token?appid=655008907&secret=f704e59f7deb4844c0e1a6a9e76c031e83ea5978";
    private static final String sendMessageUrl = "https://alarm.im.qihoo.net/message/custom/send?access_token=ec7c753229a4c258085d8e0093826659&trans_id=acd6278955721c51b5465e4ec437bd1c";

    /**
     * @Description: 获取调用机器人的token信息
     * @Author: zhanglizeng
     * @Param: [url, appid, secret]
     * @Return: java.lang.String
     * @Date: 2021/11/16 14:16
     */
    public static Map<String, Object> getRobotToken(String url, String appid, String secret) {
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appid", appid);
            paramMap.put("secret", secret);
            String resultToken = MsUmappHttpClientUtil.fetchContentByGetMethod(url, paramMap);
            JSONObject jsonObject = JSONObject.parseObject(resultToken);
            String transId = jsonObject.getString("trans_id");
            String accessToken = jsonObject.getString("access_token");
            if (StringUtils.isEmpty(transId) || StringUtils.isEmpty(accessToken)) {
                return null;
            }
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("transId", transId);
            resultMap.put("accessToken", accessToken);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: 机器人发送消息
     * @Author: zhanglizeng
     * @Param: [url, jsonData]
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Date: 2021/11/16 14:28
     */
    public static Map<String, Object> robotSendMessage(String url, String jsonData) {

        try {
            String messageResult = MsUmappHttpClientUtil.doPost(url, jsonData);
            JSONObject jsonObject = JSONObject.parseObject(messageResult);
            String errcode = jsonObject.getString("errcode");
            String msgids = jsonObject.getString("msgids");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("errcode", errcode);
            resultMap.put("msgids", msgids);

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
