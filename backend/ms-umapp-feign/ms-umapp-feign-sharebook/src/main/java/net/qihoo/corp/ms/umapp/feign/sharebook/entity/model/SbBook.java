package net.qihoo.corp.umapp.service.sharebook.entity.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author
 * @version 1.0
 * @PackageName: net.qihoo.corp.umapp.service.api.entity.resp
 * @ClassName: CCWorkCheckSignResp
 * @Description:
 * @date 2021/9/27 2:18 下午
 */
@Data
@Accessors(chain = true)
public class SbBook {

    /**
     * 书籍id
     */
    private Integer id;

    /**
     * isbn号
     */
    private Long isbn;

    /**
     * 是否在书架上，0-默认否  1-是
     */
//    private Boolean isBookshelf;

    /**
     * 状态：0-闲置中   1-借阅中  2-已废弃
     */
    private Integer state;

    /**
     * 提交人id
     */
    private Long userId;


    /**
     * 上传用户名
     */
    private String userName;

    /**
     * 书名
     */
    private String name;

    /**
     * 副标题
     */
//    private String subtitle;

    /**
     * 作者
     */
    private String author;

    /**
     * 译者
     */
//    private String translator;

    /**
     * 出版社
     */
    private String publishing;

    /**
     * 出版时间
     */
    private String published;

    /**
     * 精装、平装、、
     */
    private String designed;

    /**
     * 豆瓣id
     */
//    private Integer douban;

    /**
     * 豆瓣评分
     */
//    private Integer doubanScore;

    /**
     * 分类
     */
//    private String brand;

    /**
     * 页数
     */
    private Integer pages;

    /**
     * 封面图url
     */
    private String photoUrl;

    /**
     * 定价
     */
    private String price;

    /**
     * 作者简介
     */
//    private String authorIntro;

    /**
     * 简介
     */
    private String description;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 创建时间
     */
    private Date createTime;

    private String tags;
    private String tipLocation="";
    private Integer feedId = 0 ;//书架id，默认为0 就是默认书架
    private int num = 1;//数量

}
