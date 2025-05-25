package net.qihoo.corp.ms.umapp.feign.comi.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @quthor cnn
 * @date 2024/4/3
 */
@Data
@Accessors(chain = true)
public class ComiBaseModel {
    private Date updateTime;
    private Date createTime;
}
