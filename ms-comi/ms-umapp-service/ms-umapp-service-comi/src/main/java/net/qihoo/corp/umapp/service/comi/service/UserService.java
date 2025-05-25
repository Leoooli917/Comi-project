package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.common.core.results.MsUmappBaseResult;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiUser;
import net.qihoo.corp.ms.umapp.feign.comi.entity.req.ComiUserLoginReq;


/**
 * @quthor cnn
 * @date 2024/3/25
 */
public interface UserService extends IService<ComiUser> {

    /**
     * 个人信息
     * @param uid
     */
    ComiUser getUserInfo(String uid);
    ComiUser getUserWithToken(String token);
    ComiUser insertOrUpdateUser(ComiUser comiUser);
    MsUmappBaseResult<ComiUser> doQihooLogin(ComiUserLoginReq loginReq);
}
