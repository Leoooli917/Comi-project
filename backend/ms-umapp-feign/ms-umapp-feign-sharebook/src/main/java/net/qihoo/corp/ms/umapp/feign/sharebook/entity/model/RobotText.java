package net.qihoo.corp.umapp.service.sharebook.entity.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author cnn
 */
@Data
@Accessors(chain = true)
public class RobotText {
    private long id;
    private long uid;
    private int type;
    private String title;
    private String content;
    private Date modifyTime;
    private Date createTime;
}
    