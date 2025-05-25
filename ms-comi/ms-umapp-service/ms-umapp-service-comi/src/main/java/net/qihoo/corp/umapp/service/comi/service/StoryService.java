package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStory;

import java.util.List;


/**
 * @quthor cnn
 * @date 2024/3/29
 */
public interface StoryService extends IService<ComiStory> {

    /**
     * 插入
     */
    ComiStory insertStory(ComiStory comiStory);
    /**
     * 更新
     */
    ComiStory updateStory(ComiStory comiStory);
    ComiStory updateStoryTitle(Integer storyId,String title);
    ComiStory get(Integer storyId);
    ComiStory updateState(Integer storyId,Integer state);
    Integer updateStarCount(Integer storyId,Integer count);

    List<ComiStory> getAllStory(String filterModelName, String orderByWhat,Integer count);

    IPage<ComiStory> getStoryByPage(String filterModelName, String orderByWhat,Integer currentPage,Integer pageSize);

    List<ComiStory> getMostPopularStory(String filterModelName, String orderByWhat,Integer count);

    List<ComiStory> getMyStory(Integer type, String userName, String filterModelName, String orderByWhat,List<Integer> idsIN);
    Integer setPosterImg(Integer picId,Integer history_id);

}
