package net.qihoo.corp.ms.umapp.feign.comi.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPri;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiRatio;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStory;

import java.util.List;


@Data
@Accessors(chain = true)
public class ComiStoryRes extends ComiStory {
    private List<List<ComiPictureRes>> pictureListSub;
    private List<ComiActorPri> actorPris;
    private ComiRatio ratio;
    private boolean isStar;
    private boolean isCollect;
}
