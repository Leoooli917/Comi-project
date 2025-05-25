package net.qihoo.corp.ms.umapp.feign.comi.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ComiStoryRePicture {
    private Integer picId;
    private String content;
    private String actorId;
}
