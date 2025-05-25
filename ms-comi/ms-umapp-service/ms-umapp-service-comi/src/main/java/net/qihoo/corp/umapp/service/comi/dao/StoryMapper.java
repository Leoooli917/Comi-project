package net.qihoo.corp.umapp.service.comi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @quthor cnn
 * @date 2024/3/25
 */
@Mapper
public interface StoryMapper extends BaseMapper<ComiStory> {
    @Select("SELECT comi_story.id, content, comi_story.user_name, poster_image, status, actor_ids, comi_story.update_time, comi_story.create_time, COUNT(comi_star.id) as star_count " +
            "FROM comi_story " +
            "LEFT JOIN comi_star ON comi_story.id = comi_star.story_id " +
            "GROUP BY comi_story.id " +
            "ORDER BY star_count DESC")
    List<ComiStory> getAllStoryOrderByStar(String filterModelName);
}
