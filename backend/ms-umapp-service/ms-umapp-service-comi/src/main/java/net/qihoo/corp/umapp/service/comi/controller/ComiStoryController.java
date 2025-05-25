package net.qihoo.corp.umapp.service.comi.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.*;
import net.qihoo.corp.ms.umapp.feign.comi.entity.req.*;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiPictureRes;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiStoryRes;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiStoryRes1;
import net.qihoo.corp.umapp.service.comi.config.ApiConsts;
import net.qihoo.corp.umapp.service.comi.config.Configs;
import net.qihoo.corp.umapp.service.comi.service.*;
import net.qihoo.corp.umapp.service.comi.service.bus.BusBaseItem;
import net.qihoo.corp.umapp.service.comi.service.bus.MyMessageProducer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Api(tags = "故事")
@RestController
@RequestMapping(ApiConsts.API_PREFIX + "/story")
public class ComiStoryController extends BaseController {
    @Autowired
    CollectService collectService;
    @Autowired
    StarService starService;

    @Autowired
    ActorServicePrivate actorServicePrivate;

    @Autowired
    MyMessageProducer messageSender;
    @Autowired
    RatioService ratioService;

    @Autowired
    ActorServicePublic aPublic;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    /**
     * 1需要先插入表 story
     * 2.需要生成场景 picture
     * 3.这时候状态为未执行 0
     *
     * @param comiStory
     * @return
     */
    @ApiOperation("1提交故事-首次")
    @PostMapping("/commitStory")
    public MsUmappBaseResult<ComiStoryRes1> commitStory(@RequestHeader(value = "Authorization") String token,@RequestBody ComiStoryReq comiStory) {

        //获取内容
        String content = comiStory.getContent();

        //查看不能为空
        if(content.isEmpty()){
            return MsUmappBaseResult.error("故事能容不能为空");
        }


        boolean isGPT = comiStory.getIsFromGPT() == 1;
        ComiStory c = new ComiStory();
        c.setContent(comiStory.getContent());
        String title= "";
       if(content.length()<12){
           title = content;

       }else {
           title = content.substring(0, 12);
       }

       c.setTitle(title);
       c.setIsGpt(comiStory.getIsFromGPT());

       //这是什么
        ComiUser currentUser = getCurrentUser(token);
        c.setUserName(currentUser.getUserName());
        ComiStory comiStory1 = storyService.insertStory(c);
//        生成pic 场景列表，但不包含图片
        List<String> strings;
        if (isGPT){
            strings = aiService.chatGptGetActions(comiStory.getContent());
        }else {
            if (!comiStory.getContent().contains("\n")){
                return MsUmappBaseResult.error("请至少2个场景以上，每个场景以回车符分隔");
            }
             strings =  Arrays.asList(comiStory.getContent().split("\n"));
        }
//        最后更新状态
        updateStoryStatus(comiStory1.getId(), Configs.STORY_STATUS_FIRST_COMMIT);

        ComiStoryRes1 comiStoryRes1 = new ComiStoryRes1();
        comiStoryRes1.setStoryId(comiStory1.getId());
        comiStoryRes1.setScenes(strings);
        return MsUmappBaseResult.ok(comiStoryRes1);
    }

    @ApiOperation("更新故事名称")
    @RequestMapping(value = "/updateStoryTitle", method = {RequestMethod.POST})
    public MsUmappBaseResult<ComiStory> updateStoryTitle(@RequestHeader(value = "Authorization") String token,@RequestBody ComiStoryUpdateTitleReq req){
         if(!checkOwner(token,req.getStoryId())){
             return MsUmappBaseResult.error("非本人，无权限");
         }
       ComiStory  comiStory=  storyService.updateStoryTitle(req.getStoryId(),req.getTitle());
        return MsUmappBaseResult.ok(comiStory);
    }
    @ApiOperation("获取故事名称")
    @RequestMapping(value = "/getStoryTitle", method = {RequestMethod.GET})
    public MsUmappBaseResult<String> getStoryTitle(@RequestHeader(value = "Authorization") String token,Integer storyId){
        ComiStory  comiStory=  storyService.get(storyId);
        if (comiStory!=null){
            return MsUmappBaseResult.ok(comiStory.getTitle());
        }else return MsUmappBaseResult.error("");
    }
    private void updateStoryStatus(Integer storyId, Integer status) {
        storyService.updateState(storyId, status);
        pictureService.updateState(storyId, status);
    }
    //parse xml file
    public  static   void paresXml(){

    }


    @ApiOperation("2提交故事-2次-拆分场景")
    @PostMapping("/commitStoryWithScene")
    public MsUmappBaseResult<ComiStoryRes> commitStoryWithScene(@RequestHeader(value = "Authorization", required = true) String token,
                                                                @RequestBody ComiStoryReq1 comiStoryReq1) {

        Integer storyId = comiStoryReq1.getStoryId();
        Integer ratioId = comiStoryReq1.getRatioId();
        String modelName = comiStoryReq1.getModelName();
        List<String> scenes = comiStoryReq1.getScenes();
        if (!checkOwner(token,storyId)) return MsUmappBaseResult.error("非本人，无权限");

        ComiStory comiStory = storyService.get(storyId);

        comiStory.setRatioId(ratioId);
        comiStory.setModelName(modelName);
        ComiStory comiStory1 = storyService.updateStory(comiStory);

//        这里需要提取故事的角色信息，包括名字
        String sceneValues="";
        for (int i = 0; i < scenes.size(); i++) {
            sceneValues =sceneValues+scenes.get(i).replace("\n","");
        }
        List<String> roles = aiService.chatGptGetRoles(sceneValues);
       List<ComiActorPub> pubList= aPublic.getList();
        ComiActorPub pub = null;
        if(pubList.size()>0){
            for (int i = 0; i < roles.size(); i++) {
                pub = pubList.get(i);
                ComiActorPriReq priReq = new ComiActorPriReq();
                priReq.setActorName(roles.get(i));
                priReq.setPrompt(aiService.getOneRoleMockPrompt(roles.get(i)));
                actorServicePrivate.savePrivateActor(false,storyId,comiStory.getUserName(),pub,priReq);
            }
        }
        pictureService.insertStory(storyId,getCurrentUser(token).getUserName(),"",scenes);
        ComiStoryRes comiStoryRes = addToReturn(comiStory1);
        updateStoryStatus(storyId, Configs.STORY_STATUS_FIRST_COMMIT_SCENE);
        return MsUmappBaseResult.ok(comiStoryRes);
        
    }



    @ApiOperation("3提交故事-3次-提交角色---这里就会开始生图")
    @PostMapping("/commitStoryWithActor")
    public MsUmappBaseResult<ComiStoryRes> commitStoryWithActor(@RequestHeader(value = "Authorization") String token,@RequestBody ComiStoryReq2 comiStoryReq2) {
        Integer storyId = comiStoryReq2.getStoryId();
        List<String> actorIds = comiStoryReq2.getActorIds();
        if (!checkOwner(token,storyId)) return MsUmappBaseResult.error("非本人，无权限");

        ComiStory comiStory = storyService.get(storyId);
        comiStory.setActorIds(String.valueOf(actorIds));

        List<ComiActorPri> actors = actorServicePrivate.getActorsFromIds(storyId, String.valueOf(actorIds));
//        ac.get;
        if (actors==null || actors.size()==0)return MsUmappBaseResult.error("角色不存在");
        List<ComiPicture> list = pictureService.getList(storyId);
        for (ComiPicture pic: list) {
            ComiActorPri actorPriChoose = aiService.chooseOneActor(pic, actors);
            if (actorPriChoose==null) return MsUmappBaseResult.error("角色不存在");

            pic.setActorId(actorPriChoose.getId());
            pictureService.updatePicture(pic);
        }
        ComiStory comiStory1 = storyService.updateStory(comiStory);
        ComiStoryRes comiStoryRes = addToReturn(comiStory1);
        updateStoryStatus(storyId, Configs.STORY_STATUS_FIRST_COMMIT_ACTOR);
        BusBaseItem busBaseItem = new BusBaseItem();
//        返回数据
        Gson gson = new Gson();
        ComiStoryRes comiStoryResNew = addToReturn(comiStory);
        String json = gson.toJson(comiStoryResNew);
        messageSender.sendMessage(gson.toJson(busBaseItem.setGroupId(groupId).setType(Configs.BUS_STORY).setMessage(json)));
        return MsUmappBaseResult.ok(comiStoryRes);

    }


    @ApiOperation("4提交故事-最后一步发布")
    @RequestMapping(value = "/commitStoryPublish", method = {RequestMethod.GET, RequestMethod.POST})

    public MsUmappBaseResult<ComiStoryRes> commitStoryPublish(@RequestHeader(value = "Authorization") String token,Integer storyId) {
        if (!checkOwner(token,storyId)) return MsUmappBaseResult.error("非本人，无权限");
        if(!checkStorySatus(storyId,Configs.STORY_STATUS_FINISH)){
            return MsUmappBaseResult.error("故事尚未生成，请稍后发布");
        }
        updateStoryStatus(storyId, Configs.STORY_STATUS_FINISH_PUBLISH);
        return checkGenerate(token,storyId);
    }


    @ApiOperation(value = "单张图重新生成",notes="")
    @PostMapping("/reGenerate")
    public MsUmappBaseResult<Integer> reGenerate(@RequestHeader(value = "Authorization") String token,@RequestBody ComiStoryRePicture comiStoryRePicture) {
        if (!checkOwnerPic(token,comiStoryRePicture.getPicId())) return MsUmappBaseResult.error("非本人，无权限");
//        首先置为历史，并插入新的
        ComiPicture comiPicture = pictureService.updatePictureToHistory(comiStoryRePicture.getActorId(), comiStoryRePicture.getPicId(), comiStoryRePicture.getContent());
        Integer storyId = comiPicture.getStoryId();
//       通知kafka 生成新的
        ComiStory comiStory = storyService.get(storyId);
        ComiStoryRes comiStoryResNew = addToReturn(comiStory);
        BusBaseItem busBaseItem = new BusBaseItem();
        Gson gson = new Gson();
        String json = gson.toJson(comiStoryResNew);
        messageSender.sendMessage(gson.toJson(busBaseItem.setGroupId(groupId).setType(Configs.BUS_PIC).setMessage(json).setExtraId(comiPicture.getHistoryId())));
        return MsUmappBaseResult.ok(0);
    }

    @ApiOperation("检查生成结果")
    @RequestMapping(value = "/checkGenerate", method = {RequestMethod.GET, RequestMethod.POST})

    public MsUmappBaseResult<ComiStoryRes> checkGenerate(@RequestHeader(value = "Authorization") String token,Integer storyId) {
        if (!checkOwner(token,storyId)) return MsUmappBaseResult.error("非本人，无权限");
//        逻辑生成不卡进度，直接插入，通过check接口检查生成结果
        ComiStory comiStory = storyService.get(storyId);
        ComiStoryRes res = addToReturn(comiStory);
        return MsUmappBaseResult.ok(res);
    }

    private ComiStoryRes addToReturn(@NonNull ComiStory comiStory) {
        ComiStoryRes res = new ComiStoryRes();
        if (comiStory !=null) {
            Integer storyId = comiStory.getId();
            String actorIds = comiStory.getActorIds();
            List<ComiActorPri> actorsFromIds = actorServicePrivate.getActorsFromIds(storyId, actorIds);
            List<List<ComiPictureRes>> listWithSub = pictureService.getListWithSub(storyId);
            BeanUtils.copyProperties(comiStory, res);
//        1 列表
            res.setPictureListSub(listWithSub);
//        2 id
            res.setActorPris(actorsFromIds);
//        3 比例
            res.setRatio(ratioService.getRadio(comiStory.getRatioId()));
//        点赞
            boolean starStory = starService.isStarStory( storyId);
            res.setStar(starStory);
//       收藏
            boolean collectStory = collectService.isCollectStory( storyId);
            res.setCollect(collectStory);
        }
        return res;
    }


    @ApiOperation(value = "获取我的故事", notes = "type 1=已发布，2= 草稿 3=收藏 4= 点赞")
    @RequestMapping(value = "/getMyStory", method = {RequestMethod.GET, RequestMethod.POST})

    public MsUmappBaseResult<List<ComiStory>> getMyStory(@RequestHeader(value = "Authorization") String token,@RequestParam(required = false, defaultValue = "1") Integer type,@RequestParam(required = false, defaultValue = "all") String filterModelName,
                                      @RequestParam(required = false, defaultValue = "date") String orderByWhat) {
        List<ComiStory> myStory=null;
        String userName = getCurrentUser(token).getUserName();
        if (type==1||type==2){
            myStory= storyService.getMyStory(type, userName, filterModelName, orderByWhat,null);
        }else if (type==3){
            List<Integer> myStoryIDS = collectService.getMyStory(userName);
             myStory = storyService.getMyStory(type, userName, filterModelName, orderByWhat, myStoryIDS);
        }else if (type==4){
            List<Integer> myStoryIDS = starService.getMyStory(userName);
             myStory = storyService.getMyStory(type, userName, filterModelName, orderByWhat, myStoryIDS);
        }
        return MsUmappBaseResult.ok(myStory);
    }


    @ApiOperation(value="设置为封面 ")
    @RequestMapping(value = "/setPosterImg", method = {RequestMethod.GET, RequestMethod.POST})
    public MsUmappBaseResult<Integer> setPosterImg(@RequestHeader(value = "Authorization") String token,Integer picId,Integer history_id) {

        return MsUmappBaseResult.ok(storyService.setPosterImg( picId,history_id));

    }

}
    