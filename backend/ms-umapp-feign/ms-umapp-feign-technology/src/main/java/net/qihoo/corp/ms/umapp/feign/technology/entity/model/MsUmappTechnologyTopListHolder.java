package net.qihoo.corp.ms.umapp.feign.technology.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyTopListHolder extends MsUmappBaseModel {
//    private Integer id;//榜单批次id
    private Integer type;//1 日榜  type2 幸运奖
    private Integer questionId;//试题ID
    private Integer userId;//用户id
    private Integer answerId;//试题ID
}
