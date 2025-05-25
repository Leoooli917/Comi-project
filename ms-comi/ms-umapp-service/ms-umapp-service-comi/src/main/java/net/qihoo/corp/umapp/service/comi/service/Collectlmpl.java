package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiCollect;
import net.qihoo.corp.umapp.service.comi.dao.CollectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @quthor cnn
 * @date 2024/4/1
 */
@Slf4j
@Service
public class Collectlmpl extends ServiceImpl<CollectMapper, ComiCollect> implements CollectService {


    @Override
    public ComiCollect collect(String userName, Integer storyId) {
        QueryWrapper<ComiCollect> qWer = new QueryWrapper<>();
        qWer.eq("story_id", storyId);
        qWer.eq("user_name", userName);
        List<ComiCollect> comiStars = this.baseMapper.selectList(qWer);
        if (comiStars!=null&&comiStars.size()>0)return comiStars.get(0);
        ComiCollect comiCollect = new ComiCollect();
        comiCollect.setUserName(userName);
        comiCollect.setStoryId(storyId);
        this.baseMapper.insert(comiCollect);
        return comiCollect;
    }

    @Override
    public Integer cancelCollect(String userName, Integer storyId) {
        QueryWrapper<ComiCollect> qWer = new QueryWrapper<>();
        qWer.eq("story_id", storyId);
        qWer.eq("user_name", userName);
        List<ComiCollect> comiCollects = this.baseMapper.selectList(qWer);
        if (comiCollects!=null&&comiCollects.size()>0){
            QueryWrapper<ComiCollect> qWer2 = new QueryWrapper<>();
            qWer2.eq("id",comiCollects.get(0).getId());
            int delete = this.baseMapper.delete(qWer2);
            return delete;
        }
        return 0;
    }

    @Override
    public List<Integer> getMyStory(String userName) {
        QueryWrapper<ComiCollect> qWer = new QueryWrapper<>();
        qWer.eq("user_name", userName);
        List<ComiCollect> comiStars = this.baseMapper.selectList(qWer);
        List<Integer> idList = new ArrayList<>();
        for (ComiCollect comiCollect : comiStars) {
            Integer id = comiCollect.getStoryId();
            idList.add(id);
        }
        return idList;
    }

    @Override
    public boolean isCollectStory(Integer storyId) {
        QueryWrapper<ComiCollect> qWer = new QueryWrapper<>();
//        qWer.eq("user_name", userName);
        qWer.eq("story_id", storyId);
        ComiCollect comiStar = this.baseMapper.selectOne(qWer);
        return null != comiStar;
    }
}
