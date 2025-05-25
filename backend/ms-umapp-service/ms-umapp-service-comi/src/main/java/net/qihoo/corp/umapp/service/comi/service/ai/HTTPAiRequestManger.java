package net.qihoo.corp.umapp.service.comi.service.ai;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPri;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiAiBase;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiPicture;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiPictureRes;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiStoryRes;
import net.qihoo.corp.umapp.service.comi.dao.AiBaseMapper;
import net.qihoo.corp.umapp.service.comi.utils.ComiUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static net.qihoo.corp.umapp.service.comi.config.Configs.MAX_ACTOR_SIZE;

@Slf4j
@Service
public class HTTPAiRequestManger extends ServiceImpl<AiBaseMapper, ComiAiBase> implements HttpAIService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${gpt.API_TXT2IMG}")
    private String API_TXT2IMG;
    @Value("${gpt.bingo_access}")
    private String bingo_access;
    @Value("${gpt.bingo_secret}")
    private String bingo_secret;
    @Value("${gpt.secret_gpt}")
    private String secret_gpt;
    @Value("${gpt.negative_prompt}")
    private String negative_prompt;


    @Override
    public void doRequest(ComiActorPri actorPri, CallBackActor callBack) {
        String myTrigger = actorPri.getMyTrigger();
        String modelName = actorPri.getModelName();
        String actorPrompt = actorPri.getPrompt();
        boolean isGirl = actorPri.getSex() == 0;
        String con = (isGirl ? "1girl," : "1boy,") + myTrigger + "," + actorPrompt + ",";
        Integer[] ratio = new Integer[]{1, 1};
        AIBaseModel aiBaseModel = doRequest(modelName, con, actorPri.getLoraName(), ratio, -1);
        if (callBack != null)
            callBack.finishOne(actorPri, aiBaseModel);
    }

    @Override
    public void doRequest(ComiStoryRes story, Integer historyId, CallBack callBack) {
        String ratio_value = story.getRatio().getValue();
        Integer[] ratioArr = new Integer[]{1, 1};
        try {
            String[] split = ratio_value.split(":");
            ratioArr[0] = Integer.parseInt(split[0]);
            ratioArr[1] = Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String modelName = story.getModelName();
        List<List<ComiPictureRes>> pictureListSub = story.getPictureListSub();
        for (List<ComiPictureRes> pList : pictureListSub) {
            ComiPictureRes p = null;
            if (historyId >= 0) {
                for (ComiPictureRes one : pList) {
                    Integer historyId1 = one.getHistoryId();
                    if (historyId.equals(historyId1)) {
                        p = one;
                    }
                }
            } else p = pList.get(0);
            if (p != null) {
                ComiActorPri comiActorPri = p.getActorPri();
                String myTrigger = comiActorPri.getMyTrigger();
                String actorPrompt = comiActorPri.getPrompt();
                boolean isGirl = comiActorPri.getSex() == 0;
                String con = (isGirl ? "1girl," : "1boy,") + myTrigger + "," + actorPrompt + ',' + p.getContent() + ",";
                AIBaseModel aiBaseModel = doRequest(modelName, con, comiActorPri.getLoraName(), ratioArr, -1);
                if (callBack != null)
                    callBack.finishOne(story, p, aiBaseModel);
            }
        }
        if (callBack != null)
            callBack.finishAll();
    }

    private AIBaseModel doRequest(String model, String content, String lora, Integer[] ratioArr, Integer seed) {
        Integer widthRatio = ratioArr[0];
        Integer heightRatio = ratioArr[1];
        boolean isWidthBig = widthRatio >= heightRatio;
        float maxPix = 1024;
        int width = (int) (isWidthBig ? maxPix : (int) (maxPix * widthRatio  /heightRatio ));
        int height = (int) (isWidthBig ? (int) (maxPix *  heightRatio/ widthRatio ) : maxPix);
        Map<String, Object> body = new HashMap<>();
        body.put("content", content); // Replace with actual content
        body.put("base_name", model); // Replace with actual model name
        body.put("scheduler", "DPM++ 2M Karras");
        body.put("width", width);
        body.put("height", height);
        body.put("count", 1);
        body.put("num_inference_steps", 20);
        body.put("negative_prompt", negative_prompt);
        body.put("hr_scale", 1);
        body.put("seed", seed); // Seed value, replace with actual value
        body.put("lora_name", lora); // Replace with actual lora name
        String url = API_TXT2IMG;
        try {
            String authorization = generateAuthorization(url, body);
            Header[] headers = {
                    new BasicHeader("Authorization", authorization)
            };
            Gson gson = new Gson();
            String jsonString = gson.toJson(body);
            System.out.println("请求参数" + jsonString);
            String messageResult = doPostWithHeader(url, jsonString, headers);
            AIBaseModel aiBaseModel = gson.fromJson(messageResult, AIBaseModel.class);
            System.out.println("获取成功" + gson.toJson(aiBaseModel));
            return aiBaseModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<String> chatGptGetActions(String content) {
        int size = 8;
        String sizeStr = String.valueOf(size);
        System.out.println(sizeStr);
        String content_new = "帮我将以下内容分成" + sizeStr + "个场景，内容如下：" + content;
        String resultGPT = askGPT(content_new);
        String flex = "\n";
        if (resultGPT.contains("场景一") || resultGPT.contains("场景1")) {
            flex = "\n场景";
        }

        String[] array = resultGPT.split(flex);

        List<String> finalResult = new ArrayList<>();
        int arr_len = array.length;
        int oneArrayContain = arr_len / size;
        for (int i = 0; i < size; i++) {
            StringBuilder oneRT = new StringBuilder();
            for (int j = i * oneArrayContain; j < (i * oneArrayContain) + oneArrayContain; j++) {
                String result = array[j];
                String cnText;
                try {
                    cnText = result.split("\\.")[1];
                } catch (Exception e1) {
                    try {
                        cnText = result.split(":")[1];
                    } catch (Exception e2) {
                        cnText = result;
                    }
                }
                oneRT.append(cnText);
            }

          String value = ComiUtils.cutSceneNoNeedStr(oneRT.toString());
            finalResult.add(value);
        }

        return finalResult;
    }

    public List<String> chatGptGetRoles(String content) {

        String content_new = "帮我从以下内容，提取内容中出现的角色名称，并且已数组形式返回，内容如下：" + content;
        String resultGPT = askGPT(content_new);
        System.out.println("问询结果："+resultGPT);
        //角色名称数组为：["刘备", "关羽", "张飞", "吕布", "袁绍"]
        List<String> finalResult = new ArrayList<>();
        try {
            if (!resultGPT.isEmpty()) {
                String spite ="：";

                if(resultGPT.contains(spite)){
                    String[] arr =  resultGPT.split(spite);
                    if(arr.length == 2){
                        resultGPT = arr[1];;

                    }
                    if(arr.length == 1){
                        resultGPT = arr[0];;

                    }

                }

                if(resultGPT.contains("\n")){
                    String[] arr = resultGPT.split("\n");
                    String roleNames="";

                    for(int i=0;i<arr.length;i++){
                        roleNames =roleNames+ComiUtils.getRoleName(arr[i])+",";

                    }
                    resultGPT =roleNames;
                }
                resultGPT = resultGPT.replace("[", "");
                resultGPT = resultGPT.replace("]", "");
                resultGPT = resultGPT.replace("人名：", "");

                resultGPT = resultGPT.replace(" ", "");
                resultGPT = resultGPT.replace(",", "、");
                resultGPT = resultGPT.replace("，", "、");
                String[] arr = resultGPT.split("、");
                for (String a : arr) {
                    String rt = a.replaceAll("\"", "").replaceAll("]", "");
                    if (finalResult.size() < MAX_ACTOR_SIZE) {
                        finalResult.add(rt);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return finalResult;
    }

    @Override
    public String getOneRoleMockPrompt(String name) {
        String content_new = "已知一个角色名称是：" + name+"，请帮我完善角色描述，包括衣着，衣服颜色，身高，性别等等外貌信息，请不要超过50字，尽量精简";
        String resultGPT = askGPT(content_new);
        return resultGPT;
    }


    private String askGPT(String content) {
        String url = "https://api.360.cn/v1/chat/completions";
        String jsonData = "{\"model\":\"gpt-3.5-turbo-16k\",\"messages\":[{\"role\":\"user\",\"content\":\"" + content + "\"}],\"stream\":false,\"temperature\":0.9,\"max_tokens\":2048,\"top_p\":0.7,\"top_k\":0,\"repetition_penalty\":1.1,\"num_beams\":1,\"user\":\"andy\",\"content_filter\":false}";
        Header[] headers = {
                new BasicHeader("Content-Type", "application/json"),
                new BasicHeader("Authorization", secret_gpt)
        };
        String result = doPostWithHeader(url, jsonData, headers);
        System.out.println("Response: " + result);
        JSONObject jsonResult = new JSONObject(result);
        JSONArray choices = jsonResult.getJSONArray("choices");
        JSONObject firstChoice = choices.getJSONObject(0);
        JSONObject message = firstChoice.getJSONObject("message");
        return message.getStr("content");

    }

    private String doPostWithHeader(String url, String jsonData, Header[] headers) {
        int defaultTimeOutLong = 30 * 1000;
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(defaultTimeOutLong).setConnectTimeout(defaultTimeOutLong).setConnectionRequestTimeout(defaultTimeOutLong).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        HttpPost httpPost = new HttpPost(url);
        if (headers != null && headers.length > 0) {
            for (Header header : headers) {
                httpPost.addHeader(header.getName(), header.getValue());
            }
        }
        try {
            httpPost.setEntity(new StringEntity(jsonData, ContentType.APPLICATION_JSON));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            StringBuilder result = new StringBuilder();
            try {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } finally {
                response.close();
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private String sortSearch(Map<String, Object> obj, String prefix) {
        ArrayList<String> result = new ArrayList<>();

        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                // 递归处理嵌套对象
                String subPrefix = prefix.isEmpty() ? key : prefix + "[" + key + "]";
                result.add(sortSearch((Map<String, Object>) value, subPrefix));
            } else if (value instanceof ArrayList) {
                // 处理数组
                ArrayList<Object> list = (ArrayList<Object>) value;
                for (int i = 0; i < list.size(); i++) {
                    String subKey = key + "[" + i + "]";
                    Object subValue = list.get(i);
                    result.add(subKey + "=" + subValue);
                }
            } else {
                // 处理普通键值对
                String keyValue = prefix.isEmpty() ? key + "=" + value : prefix + "[" + key + "]=" + value;
                result.add(keyValue);
            }
        }

        Collections.sort(result);
        return String.join("&", result);
    }


    private final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public String generateAuthorization(String url, Map body) {
        try {
            String path = new URI(url).getPath();
            long expireTime = System.currentTimeMillis();
            String dataToSign = bingo_secret + expireTime + path + sortSearch(body, "");
            String signature = calculateSignature(dataToSign);
            String authorization = "Access=" + bingo_access + ",ExpireTime=" + expireTime + ",Signature=" + signature;
            return authorization;

        } catch (Exception e) {
            return "";
        }
    }


    private String calculateSignature(String data) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(bingo_secret.getBytes(), "HmacSHA256");
        hmac.init(secretKeySpec);
        byte[] hmacBytes = hmac.doFinal(data.getBytes());
        String hmacHex = bytesToHex(hmacBytes);
        return hmacHex;
    }


    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }


    /**
     * 暂时默认 取第一个
     * 可能需要问智脑
     *
     * @param pic
     * @param actors
     */
    @Override
    public ComiActorPri chooseOneActor(ComiPicture pic, List<ComiActorPri> actors) {
        String content = pic.getContent();

        return actors.get(0);

    }


}
