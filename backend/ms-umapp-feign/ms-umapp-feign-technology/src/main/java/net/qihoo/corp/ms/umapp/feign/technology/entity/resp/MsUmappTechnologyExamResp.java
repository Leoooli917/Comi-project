package net.qihoo.corp.ms.umapp.feign.technology.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyExamResp {
    private Integer id;
//    private Integer examHolderId;//答题批次ID
    private String title;
    private String chooseA;
    private String chooseB;
    private String chooseC;
    private String chooseD;
}
