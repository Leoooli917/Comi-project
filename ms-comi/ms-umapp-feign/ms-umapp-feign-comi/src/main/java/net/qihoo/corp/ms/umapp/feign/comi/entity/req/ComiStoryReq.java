package net.qihoo.corp.ms.umapp.feign.comi.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ComiStoryReq {
    //是否使用GPT
    private Integer isFromGPT;
    private String content;


}
