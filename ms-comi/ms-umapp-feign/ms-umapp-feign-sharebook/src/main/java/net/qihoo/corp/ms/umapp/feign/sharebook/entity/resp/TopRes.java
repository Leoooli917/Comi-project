package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.umapp.service.sharebook.entity.model.SbBook;

import java.util.Date;

/**
 * @author cnn
 */
@Data
@Accessors(chain = true)
public class TopRes {
    private long id;
    private String name;
    private String url;
    private String count;
    private String sub="";
    private Date modifyTime;
    private Date createTime;
}
    