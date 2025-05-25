package net.qihoo.corp.umapp.service.sharebook.entity.req;

import lombok.Data;

@Data
public class ApplyReq {
    private  Long id;
    private  int status;
    //申请人ID
    private  Long applyUserId;
    //审批人ID
    private  Long approverId;
}
