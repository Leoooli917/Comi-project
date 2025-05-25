package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiCollect;

import java.util.List;


/**
 * @quthor cnn
 * @date 2024/4/1
 */
public interface CollectService extends IService<ComiCollect> {

    /**
     * 获取列表
     */
    ComiCollect collect(String userName, Integer storyId);
    Integer cancelCollect(String userName,Integer storyId);

    List<Integer> getMyStory(String userName);

    boolean isCollectStory(Integer storyId);

}
