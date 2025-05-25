package net.qihoo.corp.ms.umapp.feign.technology.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

import java.util.Date;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyExam extends MsUmappBaseModel {
    private Integer examHolderId;//答题批次ID
    private Integer questionId;//试题ID
    private String title;
    private String chooseA;
    private String chooseB;
    private String chooseC;
    private String chooseD;
    private Integer answerId;//试题ID
    private Integer chooseId;//选中的答案--提交时更新
    private String dept;
}
