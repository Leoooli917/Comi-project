package net.qihoo.corp.umapp.service.comi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStar;
import org.apache.ibatis.annotations.Mapper;


/**
 * @quthor cnn
 * @date 2024/4/1
 */
@Mapper
public interface StarMapper extends BaseMapper<ComiStar> {

}
