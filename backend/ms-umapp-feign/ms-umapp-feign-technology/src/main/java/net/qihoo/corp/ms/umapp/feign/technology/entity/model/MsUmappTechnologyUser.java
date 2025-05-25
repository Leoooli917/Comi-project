package net.qihoo.corp.ms.umapp.feign.technology.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.common.core.models.MsUmappBaseModel;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyUser extends MsUmappBaseModel {
    private Integer userId;
    private String userName;
    private String realName;
    private String userPhoto;//用户头像
    private String userDepartment;//部门
}
