package net.qihoo.corp.umapp.service.comi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.common.login.service.impl.MsUmappLoginServiceImpl;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiUser;
import net.qihoo.corp.ms.umapp.feign.comi.entity.req.ComiUserLoginReq;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiUserRes;
import net.qihoo.corp.umapp.service.comi.config.ApiConsts;
import net.qihoo.corp.umapp.service.comi.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @quthor cnn
 * @date 2024/3/25
 */
@Api(tags = "用户")
@RestController
@RequestMapping(ApiConsts.API_PREFIX + "/user")
public class ComiUserController extends BaseController{

    @Autowired
    UserService userService;
    @Autowired
    MsUmappLoginServiceImpl umappLoginService;

    @ApiOperation("获取个人信息")
    @RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST})

    public MsUmappBaseResult<ComiUserRes> getUserInfo(@RequestHeader(value = "Authorization", required = true) String token){
        ComiUser currentUser =  this.getCurrentUser(token);
        ComiUserRes comiUserRes = new ComiUserRes();
        BeanUtils.copyProperties(currentUser, comiUserRes);
        return MsUmappBaseResult.ok(comiUserRes);
    }

    @ApiOperation("登录接口")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public MsUmappBaseResult<ComiUser> login(@RequestBody ComiUserLoginReq userLoginReq){
        MsUmappBaseResult<ComiUser> comiUserMsUmappBaseResult = userService.doQihooLogin(userLoginReq);
        ComiUser data = comiUserMsUmappBaseResult.getData();
        if (data!=null){
            userService.insertOrUpdateUser(data);
        }
        return comiUserMsUmappBaseResult;
    }


}
    