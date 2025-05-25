package net.qihoo.corp.umapp.service.comi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.umapp.service.comi.config.ApiConsts;
import net.qihoo.corp.umapp.service.comi.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Api(tags = "图片设置")
@RestController
@RequestMapping(ApiConsts.API_PREFIX + "/pic")
public class ComiPicController extends BaseController {

    @Autowired
    PictureService pictureService;



    @ApiOperation(value="选择某张图为故事中的一张图片")
    @RequestMapping(value = "/setStoryImageForPart", method = {RequestMethod.GET})
    public MsUmappBaseResult<Integer> setStoryImageForPart(@RequestHeader(value = "Authorization") String token,Integer picId,Integer history_id) {
        if (!checkOwnerPic(token,picId)) return MsUmappBaseResult.error("非本人，无权限");
        return MsUmappBaseResult.ok(pictureService.setStoryImageForPart( picId,history_id));
    }
}
    