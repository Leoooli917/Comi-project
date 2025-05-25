package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiRatio;
import net.qihoo.corp.umapp.service.comi.dao.RatioMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Slf4j
@Service
public class RatioImpl extends ServiceImpl<RatioMapper, ComiRatio> implements RatioService {


    @Override
    public List<ComiRatio> getRadioList() {
        QueryWrapper<ComiRatio> qWer = new QueryWrapper<>();
        return this.baseMapper.selectList(qWer);
    }

    @Override
    public ComiRatio getRadio(Integer id) {
        QueryWrapper<ComiRatio> qWer = new QueryWrapper<>();
        qWer.eq("id",id);

        return this.baseMapper.selectOne(qWer);
    }
}
