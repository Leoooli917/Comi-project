package net.qihoo.corp.ms.umapp.feign.comi.entity.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ComiStat extends ComiBaseModel{
    private Integer user_num;
    private Integer model_num;
    private Integer act_num;
    private Integer pic_num;
}