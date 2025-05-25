package net.qihoo.corp.umapp.service.comi.service.ai;

import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiPictureRes;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiStoryRes;

/**
 * @quthor cnn
 * @date 2024/4/15
 */
public interface CallBack {
    void finishOne(ComiStoryRes story, ComiPictureRes p, AIBaseModel aiBaseModel);
    void finishAll();
}
