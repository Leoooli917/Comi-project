package net.qihoo.corp.umapp.service.sharebook.entity.req;

import lombok.Data;


@Data
public class ChangeBookInfoReq{
    private Integer bookId;
    private Long isbn;
    private String name;
    private String author;
    private String publishing;
    private String published;
    private String designed;
    private Integer pages;
    private String photoUrl;
    private String price;
    private String description;
    private String tags;
    private String tipLocation="";
    private Integer feedId = 0 ;//书架id，默认为0 就是默认书架
}
