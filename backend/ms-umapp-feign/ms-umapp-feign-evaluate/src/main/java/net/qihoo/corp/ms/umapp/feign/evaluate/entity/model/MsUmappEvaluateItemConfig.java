package net.qihoo.corp.ms.umapp.feign.evaluate.entity.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 评价项配置表
 *
 * @author libin15
 * @version 1.0.0
 * @date 2024-01-16 03:58:12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MsUmappEvaluateItemConfig extends MsUmappBaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评价项编码
     */
    private String evaItemCode;

    /**
     * 评价项名称
     */
    private String evaItemName;

    /**
     * 权重
     */
    private BigDecimal weight;

}