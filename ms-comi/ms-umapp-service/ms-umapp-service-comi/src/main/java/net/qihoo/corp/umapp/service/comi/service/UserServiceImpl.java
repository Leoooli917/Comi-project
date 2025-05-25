package net.qihoo.corp.umapp.service.comi.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.common.login.properties.MsUmappLoginAuthProperties;
import net.qihoo.corp.ms.umapp.common.util.data.MsUmappEmptyUtil;
import net.qihoo.corp.ms.umapp.common.util.network.MsUmappHttpClientUtil;
import net.qihoo.corp.ms.umapp.feign.auth.feign.IMsUmappAuthFeign;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiUser;
import net.qihoo.corp.ms.umapp.feign.comi.entity.req.ComiUserLoginReq;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiQihooLoginResp;
import net.qihoo.corp.ms.umapp.feign.user.entity.model.SysUser;
import net.qihoo.corp.ms.umapp.feign.user.feign.IMsUmappUserFeign;
import net.qihoo.corp.umapp.service.comi.constants.ILoginConstant;
import net.qihoo.corp.umapp.service.comi.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @quthor cnn
 * @date 2024/3/25
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, ComiUser> implements UserService {

    @Autowired
    IMsUmappUserFeign umappUserFeign;
    @Autowired
    IMsUmappAuthFeign authFeign;
    @Autowired
    MsUmappLoginAuthProperties umappLoginAuthProperties;


    @Override
    public ComiUser getUserInfo(String uid) {
        QueryWrapper<ComiUser> qWer = new QueryWrapper<>();
        qWer.eq("user_name", uid);
        return this.baseMapper.selectOne(qWer);
    }

    @Override
    public ComiUser getUserWithToken(String token) {
        QueryWrapper<ComiUser> qWer = new QueryWrapper<>();
        qWer.eq("token", token.toLowerCase(Locale.ROOT).replaceAll("bearer ",""));
        return this.baseMapper.selectOne(qWer);    }

    @Override
    public ComiUser insertOrUpdateUser(ComiUser comiUser) {
        QueryWrapper<ComiUser> qWer = new QueryWrapper<>();
        qWer.eq("user_name", comiUser.getUserName());
        ComiUser comiUser_new = this.baseMapper.selectOne(qWer);
        if (comiUser_new != null && comiUser_new.getId() > 0) {
            QueryWrapper<ComiUser> wrapper = new QueryWrapper<>();
            comiUser_new.setToken(comiUser.getToken());
            comiUser_new.setUpdateTime(null);
            comiUser_new.setLoginTime(new Date());
            wrapper.eq("id",comiUser_new.getId());
            this.baseMapper.update(comiUser_new, wrapper);
        }else {
            this.baseMapper.insert(comiUser);
        }
        return  this.baseMapper.selectOne(qWer);
    }

    @Override
    public MsUmappBaseResult<ComiUser> doQihooLogin(ComiUserLoginReq loginReq) {
        Map params = new HashMap();
        params.put("",loginReq.getSid());
        params.put("",loginReq.getRef());
        String result = "";
        try {
            String url = ILoginConstant.LOGIN_URL+"?sid="+loginReq.getSid() + "&ref="+loginReq.getRef();
            result = MsUmappHttpClientUtil.doGet(url);
            if (MsUmappEmptyUtil.isEmpty(result)) {
                return MsUmappBaseResult.error("系统登录异常");
            }
            ComiQihooLoginResp loginResp = JSON.parseObject(result,ComiQihooLoginResp.class);
            if (loginResp != null && !MsUmappEmptyUtil.isEmpty(loginResp.getUser())) {
                MsUmappBaseResult<SysUser> sysUserResult = umappUserFeign.getUserByAccount(loginResp.getUser());
                if (MsUmappEmptyUtil.isEmpty(sysUserResult) || MsUmappEmptyUtil.isEmpty(sysUserResult.getData())) {

                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    // 添加用户
                    SysUser addUser = new SysUser();
                    addUser.setEmail(loginResp.getMail())
                            .setPhone("")
                            .setUserHeader("")
                            .setUserName(loginResp.getUser())
                            .setRealName(loginResp.getDisplay())
                            .setPassword(bCryptPasswordEncoder.encode(umappLoginAuthProperties.getDefaultPassword()))
                            .setSource(1)
                            .setCreatedBy(0)
                            .setUpdatedBy(0);;
                    MsUmappBaseResult<SysUser> addResultUser = umappUserFeign.doAddUser(addUser);
                }
                // 生成token
                SysUser sysUser = sysUserResult.getData();
                Object resultObject = authFeign.getToken(sysUser.getUserName(),umappLoginAuthProperties.getDefaultPassword(), umappLoginAuthProperties.getClientId(), umappLoginAuthProperties.getClientSecret(), umappLoginAuthProperties.getGrantType());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(resultObject);
                String token = jsonObject.getString("access_token");
                ComiUser comiUser = new ComiUser();
                comiUser.setUserName(loginResp.getMail()).setDisplayName(loginResp.getDisplay()).setId(sysUser.getId()).setToken(token).setLoginTime(sysUser.getCreatedTime());
                return MsUmappBaseResult.ok(comiUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MsUmappBaseResult.ok();
    }
}
