package net.qihoo.corp.umapp.service.sharebook.entity.req;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
/**
 * @description: 上传图书req
 * @created: 2021/11/02 14:19
 */
@Getter
@Setter
@Accessors(chain = true)
public class UploadBookReq {
    /**
     * isbn号
     */
    @NotNull
    private Long isbn;

    /**
     * 状态：0-闲置中   1-借阅中  2-已废弃
     */
//    private Integer state = 0;





    /**
     * 图书分类id
     *
    private Integer typeId;


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
    private String pages;

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
    private String tags;
    private Integer feedId = 0 ;//书架id，默认为0 就是默认书架
    private Integer num = 1;//数量
    private String tipLocation = "";//位置备注

}
