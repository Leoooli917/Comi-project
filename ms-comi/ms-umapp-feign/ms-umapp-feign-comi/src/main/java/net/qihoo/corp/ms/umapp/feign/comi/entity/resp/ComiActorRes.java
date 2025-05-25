package net.qihoo.corp.ms.umapp.feign.comi.entity.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActor;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiUser;


@Data
@Accessors(chain = true)
public class ComiActorRes extends ComiActor {
}
