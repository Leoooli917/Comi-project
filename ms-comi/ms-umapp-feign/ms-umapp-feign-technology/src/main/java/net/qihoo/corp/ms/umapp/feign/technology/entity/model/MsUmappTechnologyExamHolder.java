package net.qihoo.corp.ms.umapp.feign.technology.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

import java.util.Comparator;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyExamHolder  extends MsUmappBaseModel {
    private Integer userId;
    //开始时间
    private Long beginTimestamp;
    //答题结束
    private Long endTimestamp;
    //耗时
    private Long realTime;
    //得分
    private Integer score;
    //日榜标识0 否 其他为排名
    private Integer isDayTop;
    private String dept;

}
