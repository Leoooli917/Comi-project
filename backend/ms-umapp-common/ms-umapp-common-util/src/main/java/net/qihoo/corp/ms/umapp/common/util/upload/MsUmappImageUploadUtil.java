package net.qihoo.corp.ms.umapp.common.util.upload;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.qihoo.corp.ms.umapp.common.util.data.MsUmappGenerateCodeUtil;
import net.qihoo.corp.ms.umapp.common.util.network.MsUmappHttpClientUtil;
import net.qihoo.corp.ms.umapp.common.util.safety.MsUmappTextEncodeUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.util.upload
 * @ClassName: MsUmappImageUploadUtil
 * @Description:
 * @date 2022-10-11 17:33:17
 */
public class MsUmappImageUploadUtil {

    private static final String UPLOAD_URL = "http://api.v3.picasso.qhimg.com/v3/app/UmappCloud/tasks/";
    private static final String TASK_URL = "http://api.v3.picasso.qhimg.com/v3/app/UmappCloud/query_tasks/?GROUPID=";

    public static Map<String, Object> doUpload(MultipartFile[] files, String uploadUrl, String taskUrl, String uploadToken) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        List<String> uploadList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            byte[] fileBytes = files[i].getBytes();
            uploadList.add(MsUmappTextEncodeUtil.urlEncode(fileBytes));
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("APP", uploadToken);
        paramMap.put("VER", "4.0.0.0");
        paramMap.put("RULES", "{\"demo0\":[]}");
        paramMap.put("IMGSTREAM", JSON.toJSONString(uploadList));
        paramMap.put("SIGN", MsUmappGenerateCodeUtil.generateUUID());
        paramMap.put("TYPE", "1");
        paramMap.put("NOTIFYOPT", "[]");
        paramMap.put("FILTER", "[]");
        paramMap.put("EFFECTIVE", "[]");
        paramMap.put("PRIORITY", "[]");

        String uplaod_result = MsUmappHttpClientUtil.fetchContentByPostMethod(uploadUrl, paramMap);
        JSONObject uplaod_jsonObject = JSONObject.parseObject(uplaod_result);
        String strTaskId = uplaod_jsonObject.getString("DATA");
        int tryCount = 0; //读取taskId尝试的次数
        resultList = getPicAssoUrlList(strTaskId, tryCount, taskUrl, resultList);

        if (CollectionUtils.isEmpty(resultList)) {
            resultMap.put("sendStatus", "fail");
        } else {
            resultMap.put("sendStatus", "success");
        }
        resultMap.put("picAssoUrlList", resultList);
        return resultMap;
    }

    /**
     * @Description: 前端发送的文件为base64字节码的情况，后台调用方直接处理成需要上传的文件字符串，则调用此方法
     * @Author: zhanglizeng
     * @Param: [uploadList, uploadUrl, taskUrl, uploadToken]
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Date: 2021/11/10 14:50
     */
    public static Map<String, Object> doUploadByFileByteString(List<String> uploadList, String uploadUrl, String taskUrl, String uploadToken) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> resultList = new ArrayList<>();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("APP", uploadToken);
        paramMap.put("VER", "4.0.0.0");
        paramMap.put("RULES", "{\"demo0\":[]}");
        paramMap.put("IMGSTREAM", JSON.toJSONString(uploadList));
        paramMap.put("SIGN", MsUmappGenerateCodeUtil.generateUUID());
        paramMap.put("TYPE", "1");
        paramMap.put("NOTIFYOPT", "[]");
        paramMap.put("FILTER", "[]");
        paramMap.put("EFFECTIVE", "[]");
        paramMap.put("PRIORITY", "[]");

        String uplaod_result = MsUmappHttpClientUtil.fetchContentByPostMethod(uploadUrl, paramMap);

        JSONObject uplaod_jsonObject = JSONObject.parseObject(uplaod_result);
        String strTaskId = uplaod_jsonObject.getString("DATA");
        int tryCount = 0; //读取taskId尝试的次数
        resultList = getPicAssoUrlList(strTaskId, tryCount, taskUrl, resultList);

        if (CollectionUtils.isEmpty(resultList)) {
            resultMap.put("sendStatus", "fail");
        } else {
            resultMap.put("sendStatus", "success");
        }
        resultMap.put("picAssoUrlList", resultList);
        return resultMap;
    }

    private static List<String> getPicAssoUrlList(String strTaskId, int tryCount, String taskUrl, List<String> picAssoUrlList) throws Exception {
        //重新链接10次，如果没有返回结果，则退出
        if (tryCount > 200) {
            return picAssoUrlList;
        }
        String return_result = MsUmappHttpClientUtil.doGet(taskUrl + strTaskId);
        JSONArray return_jsonObject = JSONArray.parseArray(return_result);
        if (null != return_jsonObject && return_jsonObject.size() > 0) {
            for (int i = 0; i < return_jsonObject.size(); i++) {
                JSONObject object = (JSONObject) return_jsonObject.get(i);
                if ("SUCC".equals(object.getString("STATUS")) && "success".equals(object.getString("ERRORMSG"))) {
                    JSONObject dataObject = object.getJSONObject("DATA");
                    if (null != dataObject) {
                        JSONObject demoObject = (JSONObject) dataObject.get("demo0");
                        if (null != demoObject) {
                            JSONArray urlArray = demoObject.getJSONArray("URL");
                            picAssoUrlList.add((String) urlArray.get(0));
                        }
                    }
                }
            }
        } else { //继续读取结果
            Thread.sleep(1000);//等待2秒
            tryCount++;
            getPicAssoUrlList(strTaskId, tryCount, taskUrl, picAssoUrlList);
        }
        return picAssoUrlList;
    }
}
