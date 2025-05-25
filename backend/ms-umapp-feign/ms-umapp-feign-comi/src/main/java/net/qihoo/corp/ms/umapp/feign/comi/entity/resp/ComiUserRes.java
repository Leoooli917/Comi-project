package net.qihoo.corp.ms.umapp.feign.comi.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiUser;

@Data
@Accessors(chain = true)
public class ComiUserRes extends ComiUser {

    private Integer test =0;
}