package net.qihoo.corp.ms.umapp.feign.comi.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class ComiUser extends ComiBaseModel{
    private Integer id;
    private String userName;
    private String displayName;
    private String token;
    private Date loginTime;
}
