package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author cnn
 */
@Data
@Accessors(chain = true)
public class BookTagsRes {
    private int typeId;
    private String name;
    private int tagCount;
}
