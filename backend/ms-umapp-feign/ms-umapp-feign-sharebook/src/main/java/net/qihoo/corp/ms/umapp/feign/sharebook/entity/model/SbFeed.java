package net.qihoo.corp.umapp.service.sharebook.entity.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author cnz
 * @since 2021-11-02
 */
@Data
public class SbFeed {
    /**
     * 动态id
     */
    private Integer id;

    private Integer status;//删除 -1
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 图片url数组，以空格为分隔符
     */
    private String images;

    /**
     * 正文
     */
    private String content;
    private String contentDes;
    private int isDefault;//是否为默认书架
    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 创建时间
     */
    private Date createTime;


}
