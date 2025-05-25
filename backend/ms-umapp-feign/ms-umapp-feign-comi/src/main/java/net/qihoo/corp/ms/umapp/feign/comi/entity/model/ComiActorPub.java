package net.qihoo.corp.ms.umapp.feign.comi.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ComiActorPub extends ComiActor{
    private Integer id;
}
