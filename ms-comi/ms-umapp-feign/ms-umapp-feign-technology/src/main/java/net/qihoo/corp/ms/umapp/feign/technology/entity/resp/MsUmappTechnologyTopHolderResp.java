package net.qihoo.corp.ms.umapp.feign.technology.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyTopHolderResp {
    private  String date ;
    private Integer topPosition;
    private MsUmappTechnologyUserWithOutTokenResp currentUser;
    List<TopUser> users;
//    当前排行
    @Data
    @Accessors(chain = true)
    public static class  TopUser extends MsUmappTechnologyUserWithOutTokenResp{
        private Long realTime;
        private Integer score;
        private Date createdTime;
    }

}
