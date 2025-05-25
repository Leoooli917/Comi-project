package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPub;
import net.qihoo.corp.umapp.service.comi.dao.ActorPublicMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Slf4j
@Service
public class ActorServicePublicImpl extends ServiceImpl<ActorPublicMapper, ComiActorPub> implements ActorServicePublic {


    @Override
    public List<ComiActorPub> getList() {
        QueryWrapper<ComiActorPub> qWer = new QueryWrapper<>();
        List<ComiActorPub> comiActors = this.baseMapper.selectList(qWer);

        return comiActors;
    }

    @Override
    public ComiActorPub getActor(Integer id) {
        QueryWrapper<ComiActorPub> qWer = new QueryWrapper<>();
        qWer.eq("id",id);
        return this.baseMapper.selectOne(qWer);
    }

}
