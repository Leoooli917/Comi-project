package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPri;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPub;
import net.qihoo.corp.ms.umapp.feign.comi.entity.req.ComiActorPriReq;

import java.util.List;


/**
 * @quthor cnn
 * @date 2024/3/29
 */
public interface ActorServicePrivate extends IService<ComiActorPri> {


    ComiActorPri get(String id);
    ComiActorPri updateOne(ComiActorPri actorPri);

    /**
     * 获取列表
     */
    List<ComiActorPri> getList(Integer storyId,String userName);
    /**
     * 另存为私有角色
     */
    ComiActorPri savePrivateActor(boolean needGetNewPic,Integer storyId, String userName, ComiActorPub actor, ComiActorPriReq req);
    Integer deletePrivateActor(String userName,Integer storyId,String priActorId);
    /**
     * 更新私有角色
     */
    ComiActorPri updatePrivateActor(Integer pubActorId, String actorName, Integer sex, String loraName, String myTrigger, Integer storyId, String priActorId, String userName, String prompt);


    List<ComiActorPri> getActorsFromIds(Integer storyId,String actorIds);
}
