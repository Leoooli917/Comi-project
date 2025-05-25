package net.qihoo.corp.umapp.service.sharebook.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
public class SbApply {

    private Integer id;
    // 0表示申请中
    //1表示同意
    //2表示拒绝
    private Integer status;
    //申请人ID
    private  Long applyUserId;
    //审批人ID
    private  Long approverId;
    private Date modifyTime;

    private Date createTime;
}
