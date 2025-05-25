package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * @description:
 * @created: 2021/11/01 19:01
 */
@Data
@Accessors(chain = true)
public class BookTypeResp {

//    private Integer ptypeId;

    private Integer typeId;

    /**名字*/
    private String name;

    /**描述*/
    private String sub;

    List<BookTypeResp> child;
}
    