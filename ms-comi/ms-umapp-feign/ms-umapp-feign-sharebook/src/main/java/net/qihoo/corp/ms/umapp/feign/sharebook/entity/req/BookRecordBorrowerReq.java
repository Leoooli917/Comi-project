package net.qihoo.corp.umapp.service.sharebook.entity.req;

import lombok.Data;

/**
 * @description: 借书人
 * @created: 2021/11/03 16:01
 */
@Data
public class BookRecordBorrowerReq {

    /**
     * 借书人id
     */
    private Long borrowId;

    /**
     * 借书人名
     */
    private String borrowName;
    /**
     * 1表示超期
     */
    private  int expire;


}
    