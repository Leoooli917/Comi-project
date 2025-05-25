package net.qihoo.corp.ms.umapp.feign.comi.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ComiActor extends ComiBaseModel{
   private String modelName;
   private String loraName;
   private String actorName;
   private Integer sex;
   private String myTrigger;
   private String prompt;
   private String ownner;
   private String posterImage;
}
