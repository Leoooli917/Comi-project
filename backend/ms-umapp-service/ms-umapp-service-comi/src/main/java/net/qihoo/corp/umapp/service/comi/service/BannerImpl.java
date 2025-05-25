package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiBanner;
import net.qihoo.corp.umapp.service.comi.dao.BannerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Slf4j
@Service
public class BannerImpl extends ServiceImpl<BannerMapper, ComiBanner> implements BannerService {

    @Override
    public List<ComiBanner> getBannerList() {
        QueryWrapper<ComiBanner> wrapper = new QueryWrapper<>();
        List<ComiBanner> comiBanners = this.baseMapper.selectList(wrapper);

        return comiBanners;
    }
}
