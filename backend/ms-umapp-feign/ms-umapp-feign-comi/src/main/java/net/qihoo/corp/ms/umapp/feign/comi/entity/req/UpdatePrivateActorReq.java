package net.qihoo.corp.ms.umapp.feign.comi.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdatePrivateActorReq {
    private Integer storyId;
    private String priActorId;
    private String prompt;
    private Integer pubActorId;
    private String actorName;
}
