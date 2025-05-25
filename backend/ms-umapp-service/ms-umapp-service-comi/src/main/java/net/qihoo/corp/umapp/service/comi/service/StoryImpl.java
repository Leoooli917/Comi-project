package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiPicture;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStory;
import net.qihoo.corp.umapp.service.comi.config.Configs;
import net.qihoo.corp.umapp.service.comi.dao.StoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Slf4j
@Service
public class StoryImpl extends ServiceImpl<StoryMapper, ComiStory> implements StoryService {

    @Autowired
    PictureImpl pictureServe;
    @Override
    public ComiStory insertStory(ComiStory comiStory) {
//        AIBaseModel aiBaseModel =    HTTP_AI_Request.getInstance().doRequest("counterfeitxl_v25", "venti_(genshin_impact),,best quality,跑步", "genshin_all");
        Integer result  =this.baseMapper.insert(comiStory);
        if (result == 1) {
            return comiStory;
        }
        return null;
    }

    @Override
    public ComiStory updateStoryTitle(Integer storyId, String title) {
        ComiStory comiStory = get(storyId);
        if(null!=comiStory){
            QueryWrapper<ComiStory> qWer = new QueryWrapper<>();
            qWer.eq("id",storyId);
            comiStory.setTitle(title);
            this.baseMapper.update(comiStory,qWer);
            return comiStory;
        }
        return null;
    }

    @Override
    public ComiStory updateStory(ComiStory comiStory) {
        QueryWrapper<ComiStory> qWer = new QueryWrapper<>();
        qWer.eq("id",comiStory.getId());
        int update = this.baseMapper.update(comiStory, qWer);
        return comiStory;
    }

    @Override
    public ComiStory get(Integer storyId) {
        QueryWrapper<ComiStory> qWer = new QueryWrapper<>();
        qWer.eq("id",storyId);
        return this.baseMapper.selectOne(qWer);
    }

    @Override
    public ComiStory updateState(Integer storyId, Integer state) {
        ComiStory comiStory = get(storyId);
        if(null!=comiStory){
            QueryWrapper<ComiStory> qWer = new QueryWrapper<>();
            qWer.eq("id",storyId);
            comiStory.setStatus(state);
            this.baseMapper.update(comiStory,qWer);
            return comiStory;
        }
        return null;
    }

    @Override
    public Integer updateStarCount(Integer storyId, Integer count) {
        ComiStory comiStory = get(storyId);
        if(null!=comiStory){
            QueryWrapper<ComiStory> qWer = new QueryWrapper<>();
            qWer.eq("id",storyId);
            comiStory.setStarCount(count);
            int update = this.baseMapper.update(comiStory, qWer);
            return update;
        }
        return null;
    }

//    @Override
//    public List<ComiStory> getAllStory(String filterModelName, String orderByWhat) {
//        return this.baseMapper.getAllStoryOrderByStar(filterModelName);
//    }

    @Override
    public List<ComiStory> getAllStory(String filterModelName, String orderByWhat,Integer count) {
        QueryWrapper<ComiStory> qWer = new QueryWrapper<>();
        qWer.eq("status", Configs.STORY_STATUS_FINISH_PUBLISH);
        qWer.last("LIMIT " + count);

        return getComiStories(filterModelName, orderByWhat, qWer);
    }

    @Override
    public List<ComiStory> getMostPopularStory(String filterModelName, String orderByWhat,Integer count) {
        QueryWrapper<ComiStory> qWer = new QueryWrapper<>();
        qWer.eq("status",Configs.STORY_STATUS_FINISH_PUBLISH);
        qWer.last("LIMIT " + count);
        return getComiStories(filterModelName, orderByWhat, qWer);
    }

    @Override
    public IPage<ComiStory> getStoryByPage(String filterModelName, String orderByWhat,Integer current,Integer pageSize) {
        QueryWrapper<ComiStory> qWer = new QueryWrapper<>();
        if (!StringUtil.isBlank(filterModelName)&&!filterModelName.equals("all")){
            qWer.eq("model_name",filterModelName);
        }
        qWer.eq("status", Configs.STORY_STATUS_FINISH_PUBLISH);
        if ("date".equals(orderByWhat)) {
            qWer.lambda().orderByDesc(ComiStory::getUpdateTime);
        } else if ("star".equals(orderByWhat)) {
            qWer.orderByDesc("star_count");
        }  else {
            qWer.lambda().orderByDesc(ComiStory::getUpdateTime);
        }
        Page<ComiStory> page = new Page<>(current, pageSize);

        IPage<ComiStory> comiStories = this.baseMapper.selectPage(page,qWer);

        return comiStories;
    }

    private List<ComiStory> getComiStories(String filterModelName, String orderByWhat, QueryWrapper<ComiStory> qWer) {
        if (!StringUtil.isBlank(filterModelName)&&!filterModelName.equals("all")){
            qWer.eq("model_name",filterModelName);
        }
        if ("date".equals(orderByWhat)) {
            qWer.lambda().orderByDesc(ComiStory::getUpdateTime);
        } else if ("star".equals(orderByWhat)) {
            qWer.orderByDesc("star_count");
        }  else {
            qWer.lambda().orderByDesc(ComiStory::getUpdateTime);
        }
        List<ComiStory> comiStories = this.baseMapper.selectList(qWer);
        return comiStories;
    }

    @Override
    public List<ComiStory> getMyStory(Integer type, String userName, String filterModelName, String orderByWhat,List<Integer> idsIN) {
        QueryWrapper<ComiStory> qWer = new QueryWrapper<>();
        qWer.eq("user_name",userName);
//        type 1=已发布，2= 草稿 3=收藏 4= 点赞
        switch (type){
            case 1:
                qWer.eq("status", Configs.STORY_STATUS_FINISH_PUBLISH);
                break;
            case 2:
                qWer.ne("status", Configs.STORY_STATUS_FINISH_PUBLISH);
                break;
            case 3:
            case 4:
                qWer.in("id", idsIN);
                break;
        }
        return getComiStories(filterModelName, orderByWhat, qWer);
    }

    @Override
    public Integer setPosterImg(Integer picId,Integer history_id) {
        //设置图片为首页
        ComiPicture picture =  pictureServe.get(picId);

            //图像表设置
            pictureServe.setPicPosterImg(picId,history_id);

            //更新故事表的封面图片
            QueryWrapper<ComiStory> qWer = new QueryWrapper<>();
            ComiStory comiStory = get(picture.getStoryId());
            comiStory.setPosterImage(picture.getImgUrl());
            qWer.eq("id", comiStory.getId());

            int result  = baseMapper.update(comiStory,qWer);
            return result;



    }


}
