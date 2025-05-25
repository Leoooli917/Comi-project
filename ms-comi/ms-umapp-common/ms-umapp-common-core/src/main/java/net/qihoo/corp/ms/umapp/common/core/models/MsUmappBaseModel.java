package net.qihoo.corp.ms.umapp.common.core.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.core.models
 * @ClassName: MsUmappBaseModel
 * @Description:
 * @date 2022-10-11 17:04:54
 */
@Data
@Accessors(chain = true)
public class MsUmappBaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer createdBy;
    private Date createdTime;
    private Integer updatedBy;
    private Date updatedTime;
}
