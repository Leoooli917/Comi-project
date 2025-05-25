package net.qihoo.corp.ms.umapp.feign.evaluate.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 项目列表明细
 *
 * @author LBin
 * @date 2024-01-17 15:43:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSubListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数量
     */
    private Long count;

    /**
     * 项目集合
     */
    private List<ProjectInfo> list;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectInfo {

        /**
         * 项目id
         */
        private Long id;

        /**
         * 项目名称
         */
        private String name;

        /**
         * 拼音名称
         */
        private String pinyin_name;

        /**
         * uuid
         */
        private String uuid;

        /**
         * 更新时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date update_time;

        /**
         * 创建时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date create_time;

        /**
         * 部门名称
         */
        private String deptName;

        /**
         * 部门id
         */
        private String deptId;

        /**
         * 部门描述
         */
        private String description;

        /**
         * 所属用户id
         */
        private String owner_user_id;

        /**
         * 最后的活动时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date last_activity_at;

        /**
         * 活动级别
         */
        private String activity_level;

        /**
         * 模板类型
         */
        private String template_type;

        /**
         * 是否归档
         */
        private String is_archive;

        /**
         * 所有者
         */
        private UserBase owner;
    }
}
