package net.qihoo.corp.ms.umapp.feign.technology.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyExamHistoryResp{
    private Long currentTopTime;
    private Integer currentTopScore;
    private Date time;
    private Integer currentPage;
    private Integer pageSize;
    private List<ExamHistoryResp> list;

    @Data
    @Accessors(chain = true)
    public static class ExamHistoryResp {
        private Integer id;
        private Integer userId;
        private Date createdTime;
        //耗时
        private Long realTime;
        //得分
        private Integer score;
    }
}
