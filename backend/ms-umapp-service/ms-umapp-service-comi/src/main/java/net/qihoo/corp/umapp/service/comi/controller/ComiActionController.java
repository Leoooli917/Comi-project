package net.qihoo.corp.umapp.service.comi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiCollect;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStar;
import net.qihoo.corp.umapp.service.comi.config.ApiConsts;
import net.qihoo.corp.umapp.service.comi.service.CollectService;
import net.qihoo.corp.umapp.service.comi.service.StarService;
import net.qihoo.corp.umapp.service.comi.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @quthor cnn
 * @date 2024/4/1
 */
@Api(tags = "点赞收藏")
@RestController
@RequestMapping(ApiConsts.API_PREFIX + "/action")
public class ComiActionController extends BaseController {
    @Autowired
    CollectService collectService;
    @Autowired
    StarService starService;
    @Autowired
    StoryService storyService;




    @ApiOperation("收藏")
    @RequestMapping(value = "/collect", method = {RequestMethod.GET, RequestMethod.POST})

    public MsUmappBaseResult<ComiCollect> collect(@RequestHeader(value = "Authorization") String token, Integer storyId) {
        return MsUmappBaseResult.ok(collectService.collect(getCurrentUser(token).getUserName(), storyId));
    }

    @ApiOperation("收藏-取消")
    @RequestMapping(value = "/collectCancel", method = {RequestMethod.GET, RequestMethod.POST})
    public MsUmappBaseResult<Integer> collectCancel(@RequestHeader(value = "Authorization") String token,Integer storyId) {
        return MsUmappBaseResult.ok(collectService.cancelCollect(getCurrentUser(token).getUserName(), storyId));
    }




    @ApiOperation("点赞")
    @RequestMapping(value = "/star", method = {RequestMethod.GET, RequestMethod.POST})
    public MsUmappBaseResult<ComiStar> star(@RequestHeader(value = "Authorization") String token,Integer storyId) {
        return MsUmappBaseResult.ok(starService.star(getCurrentUser(token).getUserName(), storyId));
    }
    @ApiOperation("点赞-取消")
    @RequestMapping(value = "/starCancel", method = {RequestMethod.GET, RequestMethod.POST})
    public MsUmappBaseResult<Integer> starCancel(@RequestHeader(value = "Authorization") String token,Integer storyId) {
        return MsUmappBaseResult.ok(starService.cancelStar(getCurrentUser(token).getUserName(), storyId));
    }

}
    