package net.qihoo.corp.umapp.service.comi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiModel;
import net.qihoo.corp.umapp.service.comi.dao.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @quthor cnn
 * @date 2024/3/29
 */
@Slf4j
@Service
public class Modellmpl extends ServiceImpl<ModelMapper, ComiModel> implements ModelService {


    @Override
    public List<ComiModel> getList() {
        QueryWrapper<ComiModel> qWer = new QueryWrapper<>();
        List<ComiModel> list = this.baseMapper.selectList(qWer);
        return list;
    }
}
