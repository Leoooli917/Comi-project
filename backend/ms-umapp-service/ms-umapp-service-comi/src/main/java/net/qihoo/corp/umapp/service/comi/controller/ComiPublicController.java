package net.qihoo.corp.umapp.service.comi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.micrometer.core.lang.NonNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.*;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiBannerRes;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiPictureRes;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiStoryRes;
import net.qihoo.corp.umapp.service.comi.config.ApiConsts;
import net.qihoo.corp.umapp.service.comi.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Api(tags = "共有配置和列表-无需token")
@RestController
@RequestMapping(ApiConsts.API_PREFIX_PUBLIC)
public class ComiPublicController extends BaseController {
    @Autowired
    RatioService ratioService;
    @Autowired
    ModelService modelService;
    @Autowired
    ActorServicePublic aPublic;

    @Autowired
    BannerService bannerService;


    @Autowired
    CollectService collectService;
    @Autowired
    StarService starService;

    @Autowired
    ActorServicePrivate actorServicePrivate;


    @ApiOperation("获取Banner")
    @RequestMapping(value = "/getBanner", method = {RequestMethod.GET})
    public MsUmappBaseResult<List<ComiBannerRes>> getBanner() {
        List<ComiBanner> bannerList = bannerService.getBannerList();
        List<ComiBannerRes> resList = new ArrayList<>();
        if (bannerList != null)
            for (int i = 0; i < bannerList.size(); i++) {
                ComiBanner comiBanner = bannerList.get(i);
                ComiBannerRes res = new ComiBannerRes();
                ComiStory comiStory = storyService.get(comiBanner.getStoryId());
                BeanUtils.copyProperties(comiBanner,res);
                res.setStory(comiStory);
                resList.add(res);
            }
        return MsUmappBaseResult.ok(resList);
    }


    @ApiOperation("获取比例列表")
    @RequestMapping(value = "/config/getRadioList", method = {RequestMethod.GET})
    public MsUmappBaseResult<List<ComiRatio>> getRadioList() {
        List<ComiRatio> radioList = ratioService.getRadioList();
        return MsUmappBaseResult.ok(radioList);
    }



    @ApiOperation("获取风格")
    @RequestMapping(value = "/style/getList", method = {RequestMethod.GET})
    public MsUmappBaseResult<List<ComiModel>> getList(){
        List<ComiModel> list = modelService.getList();
        return MsUmappBaseResult.ok(list);
    }


    @ApiOperation("获取优秀故事-排行榜")
    @RequestMapping(value = "/story/getMostPopularStory", method = {RequestMethod.GET})

    public MsUmappBaseResult<List<ComiStory>> getMostPopularStory(@RequestParam(required = false, defaultValue = "7") Integer count) {
        List<ComiStory> allStory = storyService.getMostPopularStory("all", "star",count);
        return MsUmappBaseResult.ok(allStory);
    }


    @ApiOperation(value = "获取所有故事",notes="参数非必传，filterModelName可以传模型（风格）名筛选，orderByWhat排序，可以按照点赞和日期(=star or = date)")
    @RequestMapping(value = "/story/getAllStory", method = {RequestMethod.GET})
    public MsUmappBaseResult<List<ComiStory>> getAllStory(@RequestParam(required = false, defaultValue = "all") String filterModelName,
                                                          @RequestParam(required = false, defaultValue = "date") String orderByWhat,@RequestParam(required = false, defaultValue = "60") Integer count) {
        List<ComiStory> allStory = storyService.getAllStory(filterModelName, orderByWhat,count);
        return MsUmappBaseResult.ok(allStory);
    }
    @ApiOperation(value = "分页获取故事",notes="参数非必传，filterModelName可以传模型（风格）名筛选，orderByWhat排序，可以按照点赞和日期(=star or = date)")
    @RequestMapping(value = "/story/getStoryByPage", method = {RequestMethod.GET})
    public MsUmappBaseResult<IPage<ComiStory>> getStoryByPage(@RequestParam(required = false, defaultValue = "all") String filterModelName,
                                                          @RequestParam(required = false, defaultValue = "date") String orderByWhat,Integer currentPage,Integer pageSize) {
        IPage<ComiStory> pageData = storyService.getStoryByPage(filterModelName, orderByWhat,  currentPage,pageSize);
        return MsUmappBaseResult.ok(pageData);
    }



    @ApiOperation("获取公共角色库")
    @RequestMapping(value = "/actor/getPublicActorList", method = {RequestMethod.GET})
    public MsUmappBaseResult<List<ComiActorPub>> getPublicActorList() {
        List<ComiActorPub> list = aPublic.getList();
        return MsUmappBaseResult.ok(list);
    }


    @ApiOperation("获取故事详情")
    @RequestMapping(value = "/story/getStoryDetail", method = {RequestMethod.GET})
    public MsUmappBaseResult<ComiStoryRes> getStory( Integer storyId) {
        // TODO: 2024/4/23 无需检查
        ComiStory comiStory = storyService.get(storyId);
        ComiStoryRes res = addToReturn(comiStory);
        return MsUmappBaseResult.ok(res);
    }

    private ComiStoryRes addToReturn(@NonNull ComiStory comiStory) {
        ComiStoryRes res = new ComiStoryRes();
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
        return res;
    }

}
    