package net.qihoo.corp.umapp.service.sharebook.entity.req;

import lombok.Data;

/**
 * @description: 借还red
 * @created: 2021/11/02 17:07
 */
@Data
public class BookRecordAddReq {
    /**
     * 0-默认 10-借书人发起借书 20-主人确认借出 30-主人确认收书
     */

    /**
     * 主人id
     */
    private Long ownerId;

    /**
     * 主人名
     */
    private String ownerName;

    /**
     * 借书人id
     */
    private Long borrowId;

    /**
     * 借书人名
     */
    private String borrowName;

    /**
     * 书籍id
     */
    private Integer bookId;

    /**
     * 书名
     */
    private String bookName;

}
    