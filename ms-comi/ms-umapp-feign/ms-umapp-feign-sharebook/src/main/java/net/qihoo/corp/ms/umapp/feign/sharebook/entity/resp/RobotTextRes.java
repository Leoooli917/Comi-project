package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.umapp.service.sharebook.entity.model.RobotText;

import java.util.Date;

/**
 * @author cnn
 */
@Data
@Accessors(chain = true)
public class RobotTextRes  {
    private int type;
    private String title;
    private String content;
}
    