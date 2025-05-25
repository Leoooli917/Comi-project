package net.qihoo.corp.ms.umapp.feign.technology.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyExamHolderReq {
    private Integer examHolderId;//答题批次ID
    private List<MsUmappTechnologyExamCommit> answerList;

    @Data
    @Accessors(chain = true)
    public static class MsUmappTechnologyExamCommit {
        private Integer id;
        private Integer chooseId;
    }
}
