package net.qihoo.corp.ms.umapp.feign.technology.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyExamHolderWithPosition extends MsUmappTechnologyExamHolder {

    private Integer rowNum;

}
