package net.qihoo.corp.umapp.service.comi.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiBanner;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStat;
import net.qihoo.corp.umapp.service.comi.dao.BannerMapper;
import net.qihoo.corp.umapp.service.comi.dao.StatMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Slf4j
@Service
public class StatImpl extends ServiceImpl<StatMapper, ComiStat> implements StatService {

    @Override
    public ComiStat statUser(String min_date, String max_date, String user, String model) {
//        List<JSON> stat = new ArrayList<>();
        ComiStat data = this.baseMapper.statAll(min_date, max_date, user, model);
//            stat.add(comiStat.getModel_num());
//            stat.add(comiStat.getAct_num());
//            stat.add(comiStat.getPic_num());
        return data;
    }

}
