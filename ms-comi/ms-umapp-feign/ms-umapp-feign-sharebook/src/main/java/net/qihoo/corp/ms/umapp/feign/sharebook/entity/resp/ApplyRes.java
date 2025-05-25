package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import lombok.Data;
import net.qihoo.corp.umapp.service.sharebook.entity.model.SbApply;
import net.qihoo.corp.umapp.service.sharebook.entity.model.SbUser;

@Data
public class ApplyRes extends SbApply {
   private  SbUserResBean  applyUser;
   private SbUserResBean   approveUser;
}
