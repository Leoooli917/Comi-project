package net.qihoo.corp.ms.umapp.feign.comi.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ComiPicture extends ComiBaseModel{
    private Integer id;
    private Integer historyId;
    private Integer chooseImg;
    private String userName;
    private Integer storyId;
    private String actorId;
    private String content;
    private String prompt;
    private String imgUrl;
    private Integer status;
    private Integer isPoster;
}
