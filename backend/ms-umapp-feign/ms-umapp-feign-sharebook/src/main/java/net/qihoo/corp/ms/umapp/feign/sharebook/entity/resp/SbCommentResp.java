package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.umapp.service.sharebook.entity.model.SbComment;

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
public class SbCommentResp extends SbComment {

    private String account;
    private String avatarUrl;
    private String name;
}
