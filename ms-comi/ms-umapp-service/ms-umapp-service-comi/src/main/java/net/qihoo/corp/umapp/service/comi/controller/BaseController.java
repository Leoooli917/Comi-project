package net.qihoo.corp.umapp.service.comi.controller;

import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.common.util.redis.MsUmappRedisUtil;
import net.qihoo.corp.ms.umapp.feign.auth.feign.IMsUmappAuthFeign;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiPicture;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStory;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiUser;
import net.qihoo.corp.ms.umapp.feign.user.feign.IMsUmappUserFeign;
import net.qihoo.corp.umapp.service.comi.service.PictureService;
import net.qihoo.corp.umapp.service.comi.service.StoryService;
import net.qihoo.corp.umapp.service.comi.service.UserService;
import net.qihoo.corp.umapp.service.comi.service.ai.HttpAIService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @quthor cnn
 * @date 2024/3/25
 */
@Slf4j
public class BaseController {
    @Autowired
    MsUmappRedisUtil umappRedisUtil;
    private static final String REDIS_KEY_USER = "redis_key_user";
    @Autowired
    IMsUmappUserFeign umappUserFeign;
    @Autowired
    IMsUmappAuthFeign umappAuthFeign;
    @Autowired
    UserService userService;
    @Autowired
    StoryService storyService;
    @Autowired
    PictureService pictureService;
    @Autowired
    HttpAIService aiService;

    public ComiUser getCurrentUser(String token) {
        ComiUser userWithToken = userService.getUserWithToken(token);
        if (userWithToken!=null)return userWithToken;
//        MsUmappBaseResult userResult = umappAuthFeign.getCurrentUserId();
//        if (userResult != null) {
//            MsUmappBaseResult userByUserId = umappUserFeign.getUserByUserId((Integer) userResult.getData());
//            LinkedHashMap map = (LinkedHashMap) userByUserId.getData();
//            return userService.getUserInfo((String) map.get("mail"));
//        }
        return null;
    }


    public boolean checkOwner(String token,Integer storyId){
        if(storyId==null||storyId<1||token.isEmpty()){
            return false;
        }

        ComiStory comiStory = storyService.get(storyId);
        String userName = comiStory.getUserName();
        String userNameSelf = getCurrentUser(token).getUserName();
        if (userName!=null && userNameSelf!=null&&userName.equals(userNameSelf))return true;
        return false;
    }
    public  boolean checkStorySatus(Integer storyId,int stauts){
        if(storyId==null||storyId<1){return false;}
        ComiStory comiStory = storyService.get(storyId);
        if(comiStory==null){return false;}
        if(comiStory.getStatus()==stauts){
            return true;
        }
        return false;


    }


    public boolean checkOwnerPic(String token,Integer picId){
        ComiPicture comiPicture = pictureService.get(picId);
        String userName = comiPicture.getUserName();
        String userNameSelf = getCurrentUser(token).getUserName();
        if (userName!=null && userNameSelf!=null&&userName.equals(userNameSelf))return true;
        return false;
    }
}
