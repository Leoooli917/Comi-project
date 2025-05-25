package net.qihoo.corp.ms.umapp.feign.comi.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ComiModel extends ComiBaseModel{
    private Integer id;
    private String modelName;
    private String displayName;
    private Integer isActive;
    private String posterImage;
    private String prompt;
    private String negativePrompt;
}
