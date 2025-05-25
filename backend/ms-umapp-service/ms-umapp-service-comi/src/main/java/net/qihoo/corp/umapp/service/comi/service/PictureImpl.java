package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiPicture;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiPictureRes;
import net.qihoo.corp.umapp.service.comi.config.Configs;
import net.qihoo.corp.umapp.service.comi.dao.PictureMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Slf4j
@Service
public class PictureImpl extends ServiceImpl<PictureMapper, ComiPicture> implements PictureService {

    @Autowired
    ActorServicePrivate actorServicePrivate;

    @Override
    public ComiPicture insertStory(Integer storyId, String userName, String actorId, List<String> contents) {
        for (String one : contents) {
            ComiPicture comiPicture = new ComiPicture();
            comiPicture.setContent(one).setUserName(userName).setStoryId(storyId).setActorId(actorId).setIsPoster(0).setStatus(Configs.STORY_STATUS_FIRST_COMMIT);
            this.baseMapper.insert(comiPicture);
        }

        return null;
    }

    @Override
    public ComiPicture updatePicture(ComiPicture comiPicture) {
        QueryWrapper<ComiPicture> qWer = new QueryWrapper<>();
        qWer.eq("id", comiPicture.getId());
        qWer.eq("history_id", comiPicture.getHistoryId());
        int update = this.baseMapper.update(comiPicture, qWer);
        if (update >= 0) {
            return comiPicture;
        }

        return null;
    }


//    bearer f92e955d-39e7-4247-9966-1be27725d177
//    {
//        "actorIds": [
//        "8b7292bd-cd5a-4e20-b536-b3aa66af5ddc"
//  ],
//        "storyId": 115
//    }


    @Override
    public ComiPicture updatePictureToHistory(String actorId, Integer picId, String content) {
        QueryWrapper<ComiPicture> qWer = new QueryWrapper<>();
        qWer.eq("id", picId);
        List<ComiPicture> comiPictures = this.baseMapper.selectList(qWer);
        Integer storyId = -1;
        String userName = "";
        if (comiPictures.size() > 0) {
            ComiPicture comiPicture = comiPictures.get(0);
            storyId = comiPicture.getStoryId();
            userName = comiPicture.getUserName();

        }
        ComiPicture comiPicture = new ComiPicture();
        comiPicture.setHistoryId(comiPictures.size()).setId(picId).setContent(content).setUserName(userName).setStoryId(storyId).setActorId(actorId).setIsPoster(0).setChooseImg(Configs.STORY_PICTURE_IS_NOT_CHOOSE).setStatus(Configs.STORY_STATUS_FIRST_COMMIT);
        this.baseMapper.insert(comiPicture);
        return comiPicture;
    }

    @Override
    public List<ComiPicture> getList(Integer storyId) {
        QueryWrapper<ComiPicture> qWer = new QueryWrapper<>();
        qWer.eq("story_id", storyId);
        List<ComiPicture> allList = this.baseMapper.selectList(qWer);
        return allList;
    }

    @Override
    public List<List<ComiPictureRes>> getListWithSub(Integer storyId) {
        QueryWrapper<ComiPicture> qWer = new QueryWrapper<>();
        qWer.eq("story_id", storyId);
        qWer.eq("choose_img", Configs.STORY_PICTURE_CHOOSE);
        List<ComiPicture> allList = this.baseMapper.selectList(qWer);
        List<List<ComiPictureRes>> allListWithSub = new ArrayList<>();
        int sizeCount = 0;
        for (int i = 0; i < allList.size(); i++) {
            ComiPicture p = allList.get(i);
            QueryWrapper<ComiPicture> qWer2 = new QueryWrapper<>();
            qWer2.eq("id", p.getId());
            List<ComiPicture> comiPictures = this.baseMapper.selectList(qWer2);
            allListWithSub.add(getResPic(comiPictures));
            sizeCount += comiPictures.size();
        }
        return allListWithSub;
    }

    private List<ComiPictureRes> getResPic(List<ComiPicture> comiPictures) {
        List<ComiPictureRes> res = new ArrayList<>();
        for (ComiPicture c :
                comiPictures) {
            ComiPictureRes comiPictureRes = new ComiPictureRes();
            BeanUtils.copyProperties(c, comiPictureRes);
            comiPictureRes.setActorPri(actorServicePrivate.get(c.getActorId()));
            res.add(comiPictureRes);
        }
        return res;
    }

    public ComiPicture get(Integer picId) {
        return get(picId, 0);
    }

    @Override
    public ComiPicture get(Integer picId, Integer historyId) {
        QueryWrapper<ComiPicture> qWer = new QueryWrapper<>();
        qWer.eq("id", picId);
        qWer.eq("history_id", historyId);
        return this.baseMapper.selectOne(qWer);
    }

    @Override
    public List<ComiPicture> updateState(Integer storyId, Integer state) {
        List<ComiPicture> list = getList(storyId);
        if (null != list && list.size() > 0) {
            for (ComiPicture c :
                    list) {
                QueryWrapper<ComiPicture> qWer = new QueryWrapper<>();
                qWer.eq("id", c.getId());
                qWer.eq("history_id", c.getHistoryId());
                qWer.eq("choose_img", Configs.STORY_PICTURE_CHOOSE);
                c.setStatus(state);
                this.baseMapper.update(c, qWer);
            }
        }
        return list;
    }

    @Override
    public ComiPicture updateStateForOne(Integer picId, Integer state) {
        ComiPicture comiPicture = get(picId);
        if (null != comiPicture) {
            comiPicture.setStatus(state);
            QueryWrapper<ComiPicture> qWer = new QueryWrapper<>();
            qWer.eq("id", picId);
            this.baseMapper.update(comiPicture, qWer);
            return comiPicture;
        }
        return null;
    }

    //是设置封面的pic表操作的步骤
    @Override
    public Integer setPicPosterImg(Integer picId, Integer history_id) {
        //封面图片
        ComiPicture posterPicture = get(picId);

        //更新当前故事的封面
        QueryWrapper<ComiPicture> qWer1 = new QueryWrapper<>();
        qWer1.eq("story_id", posterPicture.getStoryId());
        qWer1.eq("is_poster", 1);

        List<ComiPicture> list = baseMapper.selectList(qWer1);
        //将原来图片设置成非封面图片
        if (list != null && list.size() > 0) {
            for (ComiPicture picture : list) {
                picture.setIsPoster(0);
                QueryWrapper<ComiPicture> qwer2 = new QueryWrapper<>();
                qwer2.eq("id", picture.getId());
                qwer2.eq("history_id", picture.getHistoryId());
                this.baseMapper.update(picture, qwer2);
            }
        }

        posterPicture.setIsPoster(1);
        QueryWrapper<ComiPicture> qWer3 = new QueryWrapper<>();
        qWer3.eq("id", picId);
        qWer3.eq("history_id", history_id);
        int result = baseMapper.update(posterPicture, qWer3);

        return result;
    }

    @Override
    public Integer setStoryImageForPart(Integer picId, Integer history_id) {

        int result = 0;
        QueryWrapper<ComiPicture> oldQwer = new QueryWrapper<>();
        oldQwer.eq("id", picId);
//        将选中 更新为不选中
        oldQwer.eq("choose_img", Configs.STORY_PICTURE_CHOOSE);
        List<ComiPicture> list = baseMapper.selectList(oldQwer);
        if (list != null && list.size() > 0) {
            for (ComiPicture picture : list) {
                picture.setChooseImg(Configs.STORY_PICTURE_IS_NOT_CHOOSE);
                QueryWrapper<ComiPicture> qWer = new QueryWrapper<>();
                qWer.eq("id", picId);
                qWer.eq("history_id", picture.getHistoryId());
                this.baseMapper.update(picture, qWer);

            }

        }

        ComiPicture comiPicture = get(picId, history_id);

        if (null != comiPicture) {
            comiPicture.setChooseImg(Configs.STORY_PICTURE_CHOOSE);
            QueryWrapper<ComiPicture> qWer = new QueryWrapper<>();
            qWer.eq("id", picId);
            qWer.eq("history_id", comiPicture.getHistoryId());
            result = this.baseMapper.update(comiPicture, qWer);
        }

        return result;
    }
}
