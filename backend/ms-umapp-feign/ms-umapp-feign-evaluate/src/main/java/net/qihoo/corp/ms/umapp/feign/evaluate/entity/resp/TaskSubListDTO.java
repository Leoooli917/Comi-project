package net.qihoo.corp.ms.umapp.feign.evaluate.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 任务列表明细
 *
 * @author LBin
 * @date 2024-01-18 15:05:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskSubListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数量
     */
    private Long total;

    /**
     * 任务集合
     */
    private List<TaskInfo> taskList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskInfo implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 任务id
         */
        private Long id;

        /**
         * 任务名称
         */
        private String title;

        /**
         * 任务状态
         */
        private String status;

        /**
         * 需求id
         */
        private Long demand_id;

        /**
         * 迭代id
         */
        private Long iteration_id;

        /**
         * 优先级
         */
        private String priority;

        /**
         * 标签id
         */
        private Long tag_id;

        /**
         * 模块id
         */
        private Long module_id;

        /**
         * 过程id
         */
        private Long process;

        /**
         * 预估工时
         */
        private Long labor_hour;

        /**
         * 实际工时
         */
        private String real_hour;

        /**
         * 负责人
         */
        private Integer leader;

        /**
         * 创建人时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date create_time;

        /**
         * 更新时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date update_time;

        /**
         * 开始时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date start_time;

        /**
         * 结束时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date end_time;

        /**
         * 任务内容
         */
        private String task_content;

        /**
         * 项目id
         */
        private Long sub_id;

        /**
         * 项目名称
         */
        private String sub_name;

        /**
         * 创建人
         */
        private Long creator;

        /**
         * 任务状态
         */
        private Long state;

        /**
         * 实际开始时间
         */
        private String real_start_time;

        /**
         * 实际结束时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date finish_time;

        /**
         * 编辑类型
         */
        private String editor_type;

        /**
         * 内容页
         */
        private String content_html;

        /**
         * 是否置灰 0否1是
         */
        private Long is_edit;

        /**
         * 标签名称
         */
        private String tag_name;

        /**
         * 模块名称
         */
        private String module_name;
    }

}
