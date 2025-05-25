package net.qihoo.corp.ms.umapp.common.login.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.common.login.business.IMsUmappCCWorkBusiness;
import net.qihoo.corp.ms.umapp.common.login.entity.req.MsUmappCCWorkCheckSignReq;
import net.qihoo.corp.ms.umapp.common.login.entity.resp.MsUmappCCWorkCheckSignResp;
import net.qihoo.corp.ms.umapp.common.login.entity.resp.MsUmappUserLoginResp;
import net.qihoo.corp.ms.umapp.common.login.entity.resp.MsUmappUserLoginTTExtraResp;
import net.qihoo.corp.ms.umapp.common.login.properties.MsUmappLoginAuthProperties;
import net.qihoo.corp.ms.umapp.common.login.service.IMsUmappLoginService;
import net.qihoo.corp.ms.umapp.feign.auth.feign.IMsUmappAuthFeign;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysUser;
import net.qihoo.corp.ms.umapp.feign.user.feign.IMsUmappUserFeign;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.login.service.impl
 * @ClassName: MsUmappLoginServiceImpl
 * @Description:
 * @date 2022-10-13 12:26:29
 */
@Service
@Slf4j
public class MsUmappLoginServiceImpl implements IMsUmappLoginService {
    @Autowired
    IMsUmappCCWorkBusiness umappCCWorkBusiness;
    @Autowired
    IMsUmappUserFeign umappUserFeign;
    @Autowired
    IMsUmappAuthFeign umappAuthFeign;
    @Autowired
    MsUmappLoginAuthProperties umappLoginAuthProperties;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public MsUmappBaseResult<MsUmappUserLoginResp> ttLogin(MsUmappCCWorkCheckSignReq umappCCWorkCheckSignReq) {
        log.info("推推小程序登录参数信息=========== {}", umappCCWorkCheckSignReq.toString());
        MsUmappBaseResult<MsUmappCCWorkCheckSignResp> checkSignRespResult = umappCCWorkBusiness.getAccount(umappCCWorkCheckSignReq);
        if (checkSignRespResult != null && checkSignRespResult.getErrCode() == 0) {
            MsUmappCCWorkCheckSignResp checkSignResp = checkSignRespResult.getData();
            String account = checkSignResp.getAccount();
            String key = "login_user_info:" + account;   //存在redis中的缓存值
            Object object = redisTemplate.opsForValue().get(key);
            SysUser user = null;
            if (null == object) {   //缓存中没有此用户的值
                String lockKey = "key_user_login:" + account;    //使用用户域账号进行加锁
                RLock lock = redissonClient.getLock(lockKey);
                try {
                    boolean b = lock.tryLock(60, TimeUnit.SECONDS);  //尝试最大60秒获取锁
                    if (b) {   //获取到锁
                        object = redisTemplate.opsForValue().get(key);  //防止并发情况，重新获取下值
                        if (null == object) {  //依然没有获取到缓存中的值
                            MsUmappBaseResult<SysUser> userResult = umappUserFeign.getUserByAccount(account);
                            Gson gson = new Gson();
                            log.info("测试用户登录问题->account->" + account);
                            if (userResult != null && userResult.getData() != null) {
                                log.info("测试用户登录问题->userResult.getData()->" + userResult.getData().toString());
                                user = gson.fromJson(gson.toJson(userResult.getData()), SysUser.class);
                                log.info("测试用户登录问题->user" + user);
                            }
                            if (user == null) {  //数据库中查询不到此用户，则添加到数据库中
                                log.info("系统中不存在该用户，先添加用户===== {}", userResult);
                                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                                SysUser sysUser = new SysUser();
                                sysUser.setUserName(checkSignResp.getAccount())
                                        .setPhone(StringUtils.isEmpty(checkSignResp.getMobile()) ? "" : checkSignResp.getMobile())
                                        .setPassword(bCryptPasswordEncoder.encode("l1111111"))
                                        .setEmail(checkSignResp.getEmail())
                                        .setRealName(checkSignResp.getName())
                                        .setUserHeader(checkSignResp.getAvatarUrl())
                                        .setSource(1)
                                        .setCreatedBy(0)
                                        .setUpdatedBy(0);
                                MsUmappBaseResult<SysUser> addResult = umappUserFeign.doAddUser(sysUser);

                                if (addResult.getErrCode() == 0) {
                                    user = addResult.getData();
                                }
                            }
                            //把用户信息添加到缓存中，可能会存在离职的情况，所以缓存值的有效期为今天
                            Long secondsNextEarlyMorning = getSecondsNextEarlyMorning(); //获取当前时间到第二天凌晨的秒数，用于设置redis失效时间为当天
                            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(user), secondsNextEarlyMorning, TimeUnit.SECONDS);
                        } else {  //缓存中获取到值
                            user = JSONObject.parseObject(object.toString(), SysUser.class);
                        }
                    } else { //没有获取到锁，说明lockKey资源正在被其他线程操作
                        return MsUmappBaseResult.error("服务器繁忙，请稍后重试，登录失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return MsUmappBaseResult.error("登录失败");
                } finally {
                    if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            } else {
                user = JSONObject.parseObject(object.toString(), SysUser.class);
            }
            // 构造返回登录信息的结果
            return makeReturnResult(user, checkSignResp);
        } else {
            return MsUmappBaseResult.error("参数解析异常，登录失败！");
        }
    }

    @Override
    public String getAuth(String uid) {
        return makeReturnResult(uid);
    }

    private String makeReturnResult(String user) {
        try {
            SysUser sysUser = new SysUser();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            sysUser.setUserName(user)
                    .setPhone("")
                    .setPassword(bCryptPasswordEncoder.encode("l1111111"))
                    .setEmail("")
                    .setRealName(user)
                    .setUserHeader("")
                    .setSource(1)
                    .setCreatedBy(0)
                    .setUpdatedBy(0);
            umappUserFeign.doAddUser(sysUser);
            Object resultObject = umappAuthFeign.getToken(user, umappLoginAuthProperties.getDefaultPassword(), umappLoginAuthProperties.getClientId(), umappLoginAuthProperties.getClientSecret(), umappLoginAuthProperties.getGrantType());
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(resultObject);
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



    /**
     * @Description: 构造返回登录信息的结果
     * @Author: zhanglizeng
     * @Param: [user, checkSignResp]
     * @Return: net.qihoo.corp.umapp.common.base.results.Result<net.qihoo.corp.umapp.common.login.entity.resp.UmappUserLoginResp>
     * @Date: 2021/12/24 15:35
     */
    private MsUmappBaseResult<MsUmappUserLoginResp> makeReturnResult(SysUser user, MsUmappCCWorkCheckSignResp checkSignResp) {
        try {
            Object resultObject = umappAuthFeign.getToken(user.getUserName(), umappLoginAuthProperties.getDefaultPassword(), umappLoginAuthProperties.getClientId(), umappLoginAuthProperties.getClientSecret(), umappLoginAuthProperties.getGrantType());
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(resultObject);
            MsUmappUserLoginResp<MsUmappUserLoginTTExtraResp> loginResp = new MsUmappUserLoginResp<>();
            MsUmappUserLoginTTExtraResp umappUserLoginTTExtra = new MsUmappUserLoginTTExtraResp();
            umappUserLoginTTExtra.setTtUserId(checkSignResp.getUid()).setHeadPicUrl(checkSignResp.getAvatarUrl()).setUserId(user.getId());
            loginResp.setUserName(user.getUserName()).setAccessToken(jsonObject.getString("access_token")).setRefreshToken(jsonObject.getString("refresh_token")).setRealName(user.getRealName()).setExtra(umappUserLoginTTExtra);
            return MsUmappBaseResult.ok(loginResp);
        } catch (Exception e) {
            e.printStackTrace();
            return MsUmappBaseResult.error("结果参数构造错误，登录失败！");
        }
    }

    /**
     * @Description: 获取当前时间到第二天凌晨的秒数，用于设置redis失效时间为当天
     * @Author: zhanglizeng
     * @Param: []
     * @Return: java.lang.Long
     * @Date: 2021/12/24 15:38
     */
    public Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }
}
