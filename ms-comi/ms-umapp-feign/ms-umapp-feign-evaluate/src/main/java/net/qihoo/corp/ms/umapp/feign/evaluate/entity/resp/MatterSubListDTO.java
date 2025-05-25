package net.qihoo.corp.ms.umapp.feign.evaluate.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工作项列表
 *
 * @author LBin
 * @date 2024-01-19 14:07:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatterSubListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数量
     */
    private Long total;

    /**
     * 工作项集合
     */
    private List<MatterInfo> matterList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatterInfo implements Serializable {

        private static final long serialVersionUID = 1L;

        private String executor_name;
        private String iteration_name;
        private List<String> ccuser_name;
        private String ahour;
        private Integer ahour_isDefine;
        private String ccuser;
        private Integer ccuser_isDefine;
        private List<String> children;
        private Integer children_all_num;
        private Integer children_finish_num;
        private String citeration;
        private Integer citeration_isDefine;
        private String content;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date create_time;
        private String creator;
        private String customkey_69;
        private Integer customkey_69_isDefine;
        private String customkey_75;
        private Integer customkey_75_isDefine;
        private Integer delay_days;
        private Integer double_sync;
        private String editor_type;
        private Boolean exceed;
        private Long executor;
        private Integer executor_isDefine;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date finish_time;
        private String icon_name;
        private Integer id;
        private String jira_id;
        private String label;
        private Integer label_isDefine;
        private String matter_status;
        private String mendtime;
        private Integer mendtime_isDefine;
        private String mhour;
        private Integer mhour_isDefine;
        private String mkd_content;
        private String module;
        private Integer module_isDefine;
        private String mstarttime;
        private Integer mstarttime_isDefine;
        private String path;
        private Integer pid;
        private String priority;
        private Integer priority_isDefine;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date start_time;
        private Integer state;
        private String status;
        private Integer status_isDefine;
        private Integer sub_id;
        private String title;
        private String type_id;
        private String type_name;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date update_time;
    }
}
