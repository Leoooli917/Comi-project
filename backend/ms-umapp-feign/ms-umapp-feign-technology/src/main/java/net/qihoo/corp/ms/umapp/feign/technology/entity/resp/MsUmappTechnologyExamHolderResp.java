package net.qihoo.corp.ms.umapp.feign.technology.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyExamHolderResp {
    private Integer examHolderId;//答题批次ID
    //答题批次ID
    private List<MsUmappTechnologyExamResp> examList;
}
