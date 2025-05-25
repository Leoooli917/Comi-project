package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPri;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPub;
import net.qihoo.corp.ms.umapp.feign.comi.entity.req.ComiActorPriReq;
import net.qihoo.corp.umapp.service.comi.config.Configs;
import net.qihoo.corp.umapp.service.comi.dao.ActorPrivateMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Slf4j
@Service
public class ActorServicePrivateImpl extends ServiceImpl<ActorPrivateMapper, ComiActorPri> implements ActorServicePrivate {


    @Override
    public ComiActorPri get(String id) {
        QueryWrapper<ComiActorPri> qWer = new QueryWrapper<>();
        qWer.eq("id",id);
        return this.baseMapper.selectOne(qWer);
    }

    @Override
    public ComiActorPri updateOne(ComiActorPri actorPri) {
        QueryWrapper<ComiActorPri> qWer = new QueryWrapper<>();
        qWer.eq("id",actorPri.getId());
        this.baseMapper.update(actorPri,qWer);
        return actorPri;
    }

    @Override
    public List<ComiActorPri> getList(Integer storyId,String userName) {
        QueryWrapper<ComiActorPri> qWer = new QueryWrapper<>();
        qWer.eq("ownner",userName);
        qWer.eq("story_id",storyId);
        return this.baseMapper.selectList(qWer);
    }

    @Override
    public ComiActorPri savePrivateActor(boolean needGetNewPic,Integer storyId, String userName, ComiActorPub actor, ComiActorPriReq req) {
        ComiActorPri comiActorPri = new ComiActorPri();
        BeanUtils.copyProperties(actor,comiActorPri);
        comiActorPri.setId(null);
        comiActorPri.setOwnner(userName);
        comiActorPri.setStoryId(storyId);
        comiActorPri.setFromPubId(actor.getId());
        if (needGetNewPic){
            comiActorPri.setStatus(Configs.ACTOR_PIC_STATUS_BEGIN);
        }
        // 生成一个随机的UUID
        UUID uuid = UUID.randomUUID();
        // 将UUID转换为字符串
        String uuidString = uuid.toString();
        comiActorPri.setId(uuidString);
        comiActorPri.setActorName(req.getActorName());
        comiActorPri.setPrompt(req.getPrompt());
        int insert = this.baseMapper.insert(comiActorPri);
        if (insert>0){
            return comiActorPri;
        }
        return null;
    }


    @Override
    public Integer deletePrivateActor(String userName,Integer storyId, String priActorId) {
        QueryWrapper<ComiActorPri> qWer = new QueryWrapper<>();
        qWer.eq("id",priActorId);
        qWer.eq("story_id",storyId);
        qWer.eq("ownner",userName);
        return this.baseMapper.delete(qWer);
    }

    @Override
    public ComiActorPri updatePrivateActor(Integer pubActorId, String actorName, Integer sex, String loraName, String myTrigger, Integer storyId, String priActorId, String userName, String prompt) {
        QueryWrapper<ComiActorPri> qWer = new QueryWrapper<>();
        qWer.eq("id",priActorId);
        qWer.eq("story_id",storyId);
        qWer.eq("ownner",userName);

        ComiActorPri comiActorPri = this.baseMapper.selectOne(qWer);
        if (null != comiActorPri){
            comiActorPri.setPrompt(prompt);
            comiActorPri.setSex(sex);
            comiActorPri.setActorName(actorName);
            comiActorPri.setLoraName(loraName);
            comiActorPri.setMyTrigger(myTrigger);
            comiActorPri.setFromPubId(pubActorId);
            comiActorPri.setStatus(Configs.ACTOR_PIC_STATUS_BEGIN);
            QueryWrapper<ComiActorPri> qWer2 = new QueryWrapper<>();
            qWer2.eq("id",priActorId);
            qWer.eq("story_id",storyId);
            qWer.eq("ownner",userName);
            int update = this.baseMapper.update(comiActorPri, qWer2);
            return update>=0?comiActorPri:null;
        }
        return null;
    }

    @Override
    public List<ComiActorPri> getActorsFromIds(Integer storyId,String actorIds) {
        QueryWrapper<ComiActorPri> qWer = new QueryWrapper<>();
        qWer.eq("story_id",storyId);
        String value = actorIds.replace("[","").replace("]","");
        value =value.replace(" ","");
        String[] parts = value.split(","); // 使用逗号分割字符串
        List<String> list = new ArrayList<>(Arrays.asList(parts));
        qWer.in("id",list);
        List<ComiActorPri> comiActorPris = this.baseMapper.selectList(qWer);
        return comiActorPris;
    }

}
