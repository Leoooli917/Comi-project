package net.qihoo.corp.umapp.service.comi.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPri;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPub;
import net.qihoo.corp.ms.umapp.feign.comi.entity.req.ComiActorPriReq;
import net.qihoo.corp.ms.umapp.feign.comi.entity.req.UpdatePrivateActorReq;
import net.qihoo.corp.umapp.service.comi.config.ApiConsts;
import net.qihoo.corp.umapp.service.comi.config.Configs;
import net.qihoo.corp.umapp.service.comi.service.ActorServicePrivate;
import net.qihoo.corp.umapp.service.comi.service.ActorServicePublic;
import net.qihoo.corp.umapp.service.comi.service.bus.BusBaseItem;
import net.qihoo.corp.umapp.service.comi.service.bus.MyMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Api(tags = "角色")
@RestController
@RequestMapping(ApiConsts.API_PREFIX + "/actor")
public class ComiActorController extends BaseController {
    @Autowired
    ActorServicePrivate aPrivate;

    @Autowired
    ActorServicePublic aPublic;
    @Autowired
    MyMessageProducer messageSender;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;



    @ApiOperation("获取私有角色库")
    @GetMapping("/getPrivateActorList" )
    public MsUmappBaseResult<List<ComiActorPri>> getPrivateActorList(@RequestHeader(value = "Authorization") String token,Integer storyId) {
        if (!checkOwner(token,storyId)) return MsUmappBaseResult.error("非本人，无权限");
        return MsUmappBaseResult.ok(aPrivate.getList(storyId,getCurrentUser(token).getUserName()));
    }

    @ApiOperation("另存为私有角色")
    @PostMapping("/savePrivateActor")
    public MsUmappBaseResult<ComiActorPri> savePrivateActor(@RequestHeader(value = "Authorization") String token,

                                                            @RequestBody  UpdatePrivateActorReq updatePrivateActorReq2

    ) {
        Integer pubActorId = updatePrivateActorReq2.getPubActorId();
        Integer storyId = updatePrivateActorReq2.getStoryId();
        String prompt = updatePrivateActorReq2.getPrompt();
        String actorName = updatePrivateActorReq2.getActorName();
        if (!checkOwner(token,storyId)) return MsUmappBaseResult.error("非本人，无权限");
        ComiActorPriReq req = new ComiActorPriReq();
        req.setActorName(actorName);
        req.setPrompt(prompt);
        ComiActorPub actor = aPublic.getActor(pubActorId);
        ComiActorPri comiActorPri = aPrivate.savePrivateActor(true,storyId, getCurrentUser(token).getUserName(), actor, req);
        Gson gson = new Gson();
        BusBaseItem busBaseItem = new BusBaseItem();
        String json = gson.toJson(comiActorPri);
        messageSender.sendMessage(gson.toJson(busBaseItem.setGroupId(groupId).setType(Configs.BUS_ACTOR).setMessage(json)));
        return MsUmappBaseResult.ok(comiActorPri);
    }


    @ApiOperation("查看私有角色")
    @GetMapping("/checkActor")
    public MsUmappBaseResult<ComiActorPri> checkActor(@RequestHeader(value = "Authorization") String token, String priActorId) {
        ComiActorPri comiActorPri1 = aPrivate.get(priActorId);
        return MsUmappBaseResult.ok(comiActorPri1);
    }


    @ApiOperation("删除私有角色")
    @GetMapping("/deletePrivateActor")
    public MsUmappBaseResult<Integer> deletePrivateActor(@RequestHeader(value = "Authorization") String token,Integer storyId,String priActorId) {
        if (!checkOwner(token,storyId)) return MsUmappBaseResult.error("非本人，无权限");
        Integer integer = aPrivate.deletePrivateActor(getCurrentUser(token).getUserName(), storyId, priActorId);
        if (integer>0) return MsUmappBaseResult.ok(integer);
        return MsUmappBaseResult.error("失败");
    }

    @ApiOperation("更新私有角色")
    @PostMapping("/updatePrivateActor")
    public MsUmappBaseResult<ComiActorPri> updatePrivateActor(@RequestHeader(value = "Authorization") String token,@RequestBody UpdatePrivateActorReq req) {
        if (!checkOwner(token,req.getStoryId())) return MsUmappBaseResult.error("非本人，无权限");
        ComiActorPub actor = aPublic.getActor(req.getPubActorId());
        String myTrigger = actor.getMyTrigger();
        String loraName = actor.getLoraName();
        Integer sex = actor.getSex();
        ComiActorPri comiActorPri = aPrivate.updatePrivateActor(req.getPubActorId(),req.getActorName(),sex,loraName,myTrigger,req.getStoryId(), req.getPriActorId(), getCurrentUser(token).getUserName(), req.getPrompt());

        Gson gson = new Gson();
        BusBaseItem busBaseItem = new BusBaseItem();
        String json = gson.toJson(comiActorPri);
        messageSender.sendMessage(gson.toJson(busBaseItem.setGroupId(groupId).setType(Configs.BUS_ACTOR).setMessage(json)));

        if (null!=comiActorPri) return MsUmappBaseResult.ok(comiActorPri);
        return MsUmappBaseResult.error("失败");
    }
}
    