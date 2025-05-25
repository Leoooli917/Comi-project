package net.qihoo.corp.ms.umapp.feign.comi.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ComiStory extends ComiBaseModel{
    private Integer id;
    private String content;
    private String userName;
    private  String title;
    private String posterImage;
    private Integer status;
    private String actorIds;
    private Integer starCount;
    private String modelName;
    private Integer ratioId;
    private Integer isGpt;


}
