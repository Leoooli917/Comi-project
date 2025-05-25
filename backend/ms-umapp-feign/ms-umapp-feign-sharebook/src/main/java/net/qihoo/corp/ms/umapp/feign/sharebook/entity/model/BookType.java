package net.qihoo.corp.umapp.service.sharebook.entity.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @description: 书籍类型
 * @created: 2021/11/01 16:47
 */
@Getter
@Setter
public class BookType {

    private Integer id;

//    private Integer ptypeId;

    private Integer typeId;

    /**名字*/
    private String name;

    /**描述*/
    private String sub;

    private Date modifyTime;

    private Date createTime;

}
    