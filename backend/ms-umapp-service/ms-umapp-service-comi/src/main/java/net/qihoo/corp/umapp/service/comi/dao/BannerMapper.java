package net.qihoo.corp.umapp.service.comi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiBanner;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStat;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @quthor cnn
 * @date 2024/3/25
 */
@Mapper
public interface BannerMapper extends BaseMapper<ComiBanner> {

    List<ComiStat> statAll(String minDate, String maxDate, String user, String model);
}
