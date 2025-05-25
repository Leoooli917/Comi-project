package net.qihoo.corp.umapp.service.comi.service.bus;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;


@Data
@Accessors(chain = true)
public class BusBaseItem {
    private Integer type;
    private String message;
//    特殊记录的ID
    private Integer extraId;
    private String groupId;
}
