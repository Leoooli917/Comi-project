package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStar;
import net.qihoo.corp.umapp.service.comi.dao.StarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @quthor cnn
 * @date 2024/4/1
 */
@Slf4j
@Service
public class Starlmpl extends ServiceImpl<StarMapper, ComiStar> implements StarService {
    @Autowired
    StoryService storyService;


    @Override
    public ComiStar star(String userName,Integer storyId) {
        QueryWrapper<ComiStar> qWer = new QueryWrapper<>();
        qWer.eq("story_id", storyId);
        qWer.eq("user_name", userName);
        List<ComiStar> comiStars = this.baseMapper.selectList(qWer);
        if (comiStars!=null&&comiStars.size()>0)return comiStars.get(0);
        ComiStar comiStar = new ComiStar();
        comiStar.setUserName(userName);
        comiStar.setStoryId(storyId);
        this.baseMapper.insert(comiStar);
        updateCount(storyId);
        return comiStar;
    }

    private void updateCount(Integer storyId) {
        QueryWrapper<ComiStar> qWer2 = new QueryWrapper<>();
        qWer2.eq("story_id", storyId);
        Integer count = this.baseMapper.selectCount(qWer2);
        storyService.updateStarCount(storyId,count);
    }

    @Override
    public Integer cancelStar(String userName, Integer storyId) {
        QueryWrapper<ComiStar> qWer = new QueryWrapper<>();
        qWer.eq("story_id", storyId);
        qWer.eq("user_name", userName);
        List<ComiStar> comiStars = this.baseMapper.selectList(qWer);
        if (comiStars!=null&&comiStars.size()>0){
            QueryWrapper<ComiStar> qWer2 = new QueryWrapper<>();
            qWer2.eq("id",comiStars.get(0).getId());
            int delete = this.baseMapper.delete(qWer2);
            updateCount(storyId);
            return delete;
        }
        return 0;
    }

    @Override
    public List<Integer> getMyStory(String userName) {
        QueryWrapper<ComiStar> qWer = new QueryWrapper<>();
        qWer.eq("user_name", userName);
        List<ComiStar> comiStars = this.baseMapper.selectList(qWer);
        List<Integer> idList = new ArrayList<>();
        for (ComiStar comiStar : comiStars) {
            Integer id = comiStar.getStoryId();
            idList.add(id);
        }
        return idList;
    }

    @Override
    public boolean isStarStory( Integer storyId) {
        QueryWrapper<ComiStar> qWer = new QueryWrapper<>();
//        qWer.eq("user_name", userName);
        qWer.eq("story_id", storyId);
        ComiStar comiStar = this.baseMapper.selectOne(qWer);
        return null != comiStar;
    }
}
