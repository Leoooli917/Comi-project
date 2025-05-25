package net.qihoo.corp.ms.umapp.feign.technology.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyExamWithAnswerResp {
    Integer id;
    Integer score;
    Long time;
    List<ExamWithAnswerRes> examWithAnswerList;

    @Data
    @Accessors(chain = true)
    public static class ExamWithAnswerRes {
        private String title;
        private String answerAnalysis;//
        private String chooseA;
        private String chooseB;
        private String chooseC;
        private String chooseD;
        private Integer answerId;
        private Integer chooseId;
    }
}
