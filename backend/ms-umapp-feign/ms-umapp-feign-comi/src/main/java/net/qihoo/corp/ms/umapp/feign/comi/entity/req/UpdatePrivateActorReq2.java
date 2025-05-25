package net.qihoo.corp.ms.umapp.feign.comi.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdatePrivateActorReq2 {
    private Integer storyId;
    private String prompt;
    private Integer pubActorId;
}
