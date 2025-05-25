package net.qihoo.corp.ms.umapp.feign.technology.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyQuestion  extends MsUmappBaseModel {
//    private Integer    id;
    private String title;//题目
    private String chooseA;//选项1
    private String chooseB;//选项2
    private String chooseC;//选项3
    private String chooseD;//选项4
    private Integer tagType;//
    private Integer answerId;//正确答案id 分别为 1 2 3 4 对应 choose_1/choose_2/choose_3/choose_4
    private String answerAnalysis;//答案解析
}
