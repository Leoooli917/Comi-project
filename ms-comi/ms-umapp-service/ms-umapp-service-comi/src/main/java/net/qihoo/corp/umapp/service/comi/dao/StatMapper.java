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
public interface StatMapper extends BaseMapper<ComiStat> {
    @Select("SELECT (SELECT COUNT(*) FROM comi_user WHERE create_time > #{min_date} AND create_time < #{max_date}) AS user_num, " +
            "(SELECT COUNT(DISTINCT model_name) FROM comi_model WHERE create_time > #{min_date} AND create_time < #{max_date}) AS model_num, (SELECT COUNT(DISTINCT actor_name) " +
            "FROM comi_actor_pri WHERE owner LIKE CONCAT('%', #{user}, '%') AND model_name LIKE CONCAT('%', #{model}, '%') " +
            "AND create_time > #{min_date} AND create_time < #{max_date}) AS act_num, (SELECT COUNT(*) " +
            "FROM comi_picture WHERE user_name LIKE CONCAT('%', #{user}, '%') AND create_time > #{min_date} AND create_time < #{max_date}) AS pic_num"
    )
    ComiStat statAll(String min_date, String max_date, String user, String model);
}
