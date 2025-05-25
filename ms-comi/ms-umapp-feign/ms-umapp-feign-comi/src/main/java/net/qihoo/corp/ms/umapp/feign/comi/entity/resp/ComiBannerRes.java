package net.qihoo.corp.ms.umapp.feign.comi.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiBanner;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStory;


@Data
@Accessors(chain = true)
public class ComiBannerRes extends ComiBanner {
    private ComiStory story;
}
