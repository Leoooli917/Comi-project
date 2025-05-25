package net.qihoo.corp.umapp.service.comi.service.ai;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPri;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiAiBase;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiPicture;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiStoryRes;

import java.util.List;


/**
 * @quthor cnn
 * @date 2024/3/29
 */
public interface HttpAIService extends IService<ComiAiBase> {
     void doRequest(ComiActorPri actorPri, CallBackActor callBack);
     void doRequest(ComiStoryRes story, Integer historyId, CallBack callBack);
     List<String> chatGptGetActions(String content);
     ComiActorPri chooseOneActor(ComiPicture pic, List<ComiActorPri> actors);

     List<String> chatGptGetRoles(String content);

    String getOneRoleMockPrompt(String name);
}
