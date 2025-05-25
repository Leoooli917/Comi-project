package net.qihoo.corp.umapp.service.sharebook.entity.req;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cnn
 */
@Data
@Accessors(chain = true)
public class ScanIsbnReq {
    private String isbn;

    @Override
    public String toString() {
        return "ScanIsbnReq{" +
                "isbn='" + isbn + '\'' +
                '}';
    }
}
