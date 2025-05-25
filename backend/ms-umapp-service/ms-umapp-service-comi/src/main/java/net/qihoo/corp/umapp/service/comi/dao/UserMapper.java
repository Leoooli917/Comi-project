package net.qihoo.corp.umapp.service.comi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiUser;
import org.apache.ibatis.annotations.Mapper;


/**
 * @quthor cnn
 * @date 2024/3/25
 */
@Mapper
public interface UserMapper extends BaseMapper<ComiUser> {

}
