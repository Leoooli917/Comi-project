package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import lombok.Data;
import net.qihoo.corp.umapp.service.sharebook.entity.model.SbBook;

/**
 * @description: 个人接入/借出书籍列表
 * @created: 2021/11/04 10:32
 */
@Data
public class PersonBookRecordResp extends SbBook {

    /**
     * 0-默认 10-借书人发起借书 20-主人确认借出 30-主人确认收书
     */
    private Integer action;

    /**
     * 0-未过期  1-已过期
     */
    private Integer expire;

    /**
     * 借书人id
     */
    private Long borrowId;

    /**
     * 借书人名
     */
    private String borrowName;
    private Integer recordId;
}
    