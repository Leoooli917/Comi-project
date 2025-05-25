package net.qihoo.corp.ms.umapp.feign.comi.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
public class ComiStoryRes1  {
    private Integer storyId;
    private List<String> scenes;
}
