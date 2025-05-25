package net.qihoo.corp.ms.umapp.feign.comi.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ComiActorPri extends ComiActor{
    private String id;
    private Integer storyId;
    private Integer fromPubId;
    private String status;
}
