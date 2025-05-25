package net.qihoo.corp.umapp.service.comi.service.ai;

import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPri;

/**
 * @quthor cnn
 * @date 2024/4/15
 */
public interface CallBackActor {
    void finishOne(ComiActorPri story, AIBaseModel aiBaseModel);
}
