package net.qihoo.corp.umapp.service.sharebook.entity.req;

import lombok.Data;

/**
 * @description: 动态增加req
 * @created: 2021/11/02 17:21
 */
@Data
public class FeedAddReq {

    private String images;
    private String content;
    private String contentDes;
    private int isDefault;//是否为默认书架

}
    