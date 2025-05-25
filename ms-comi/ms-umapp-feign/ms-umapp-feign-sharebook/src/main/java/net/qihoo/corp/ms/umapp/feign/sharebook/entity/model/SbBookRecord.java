package net.qihoo.corp.umapp.service.sharebook.entity.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cnz
 * @since 2021-11-02
 */
@Setter
@Getter
public class SbBookRecord  {

    /**
     * 索引
     */
    private Integer id;

    /**
     * 0-默认 10-借书人发起借书 20-主人确认借出 30-主人确认收书
     */
    private Integer action;

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

    /**
     * 0-未过期  1-已过期
     */
    private Integer expire;
    private Integer remainCount;

    /**
     * 更新时间C
     */
    private Date modifyTime;

    /**
     * 创建时间
     */
    private Date createTime;
}
