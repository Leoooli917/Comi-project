package net.qihoo.corp.ms.umapp.feign.technology.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyConfig extends MsUmappBaseModel {
    private Integer type;//配置类型 1简介 2.活动规则 3.奖品设置
    private String content;//文案内容
}
