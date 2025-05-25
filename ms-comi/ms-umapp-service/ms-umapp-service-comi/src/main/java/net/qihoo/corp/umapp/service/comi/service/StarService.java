package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStar;

import java.util.List;


/**
 * @quthor cnn
 * @date 2024/4/1
 */
public interface StarService extends IService<ComiStar> {

    /**
     *
     */
    ComiStar star(String userName,Integer storyId);
    Integer cancelStar(String userName,Integer storyId);

    List<Integer> getMyStory(String userName);
//    是否点赞
    boolean isStarStory( Integer storyId);
}
