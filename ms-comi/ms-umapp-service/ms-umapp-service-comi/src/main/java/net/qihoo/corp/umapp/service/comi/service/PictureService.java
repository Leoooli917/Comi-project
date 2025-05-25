package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiPicture;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiPictureRes;

import java.util.List;


/**
 * @quthor cnn
 * @date 2024/3/29
 */
public interface PictureService extends IService<ComiPicture> {

    /**
     * 插入
     */
    ComiPicture insertStory(Integer storyId,String userName,String actorId,List<String> contents);
    /**
     * 更新
     */
    ComiPicture updatePicture(ComiPicture comiPicture);
    ComiPicture updatePictureToHistory(String actorId,Integer picId,String content);

    /**
     * 获取列表
     */
    List<ComiPicture> getList(Integer storyId);
    List<List<ComiPictureRes>> getListWithSub(Integer storyId);

    ComiPicture get(Integer picId);

    ComiPicture get(Integer picId,Integer historyId);

    List<ComiPicture> updateState(Integer storyId, Integer state);
    ComiPicture updateStateForOne(Integer picId, Integer state);

    Integer setPicPosterImg(Integer picId,Integer history_id);
    Integer setStoryImageForPart(Integer picId,Integer history_id);

}
