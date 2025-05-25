package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.umapp.service.sharebook.entity.model.SbBook;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chennaizhun
 * @description: 书籍相关返回信息
 * @created: 2021/11/16 14:08
 */
@Data
@Accessors(chain = true)
public class BookRes extends SbBook {

    private String avatarUrl;

    private Integer gender;

    private String introduction;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("isbn", getIsbn());
        map.put("userName", getUserName());
        map.put("name", getName());
        map.put("author", getAuthor());
        map.put("published", getPublished());
        map.put("designed", getDesigned());
        map.put("pages", getPages());
        map.put("photoUrl", getPhotoUrl());
        map.put("price", getPrice());
        map.put("tags", getTags());
        map.put("tipLocation", getTipLocation());
        map.put("description", getDescription());
        map.put("num", getNum());
        return map;
    }
}
    