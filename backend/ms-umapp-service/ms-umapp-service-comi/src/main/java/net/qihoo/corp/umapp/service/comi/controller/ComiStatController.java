package net.qihoo.corp.umapp.service.comi.controller;

import com.alibaba.fastjson.JSON;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Api(tags = "统计-无需token")
@RestController
@RequestMapping(ApiConsts.API_PREFIX_PUBLIC+"/stat")
public class ComiStatController extends BaseController {
    @Autowired
    StatService statService;

    @ApiOperation("统计借口")
    @RequestMapping(value = "/stat/user", method = {RequestMethod.GET})
    public MsUmappBaseResult<ComiStat> getStory( String min_date,  String max_date,  String user,  String model) {
        //这里该怎么写
        ComiStat jsons = statService.statUser(min_date, max_date, user, model);
        return MsUmappBaseResult.ok(jsons);
    }

}
    