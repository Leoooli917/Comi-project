package net.qihoo.corp.ms.umapp.feign.comi.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
public class ComiStoryReq2 {
    private Integer storyId;
    private List<String> actorIds;
}
