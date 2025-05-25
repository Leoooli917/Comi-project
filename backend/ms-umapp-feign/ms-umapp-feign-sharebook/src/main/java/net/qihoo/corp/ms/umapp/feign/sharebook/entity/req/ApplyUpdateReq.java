package net.qihoo.corp.umapp.service.sharebook.entity.req;

import lombok.Data;

@Data
public class ApplyUpdateReq {
    //1表示同意
    //2表示拒绝
    private Integer status;
}
