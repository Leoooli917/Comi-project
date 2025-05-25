package net.qihoo.corp.umapp.service.sharebook.entity.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.umapp.service.sharebook.entity.model.SbUser;

import java.util.Date;

/**
 * <p>
 * @author cnn
 */
@Data
@Accessors(chain = true)
public class SbUserRes extends SbUser {

    /**
     * 书的数量
     */
    private Integer bookNum =100;

    private Integer feedNum = 100;

    private Integer userRanking = 0;//

}
