package net.qihoo.corp.umapp.service.sharebook.entity.req;

import lombok.Data;
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
public class SbCommentReq {
    private Integer type;//type 0 是 普通评论 1 是读后感
    private Long bookId;
    private String content;
}
