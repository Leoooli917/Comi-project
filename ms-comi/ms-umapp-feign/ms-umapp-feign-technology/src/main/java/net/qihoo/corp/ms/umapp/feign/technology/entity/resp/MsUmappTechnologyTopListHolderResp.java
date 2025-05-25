package net.qihoo.corp.ms.umapp.feign.technology.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;
import net.qihoo.corp.ms.umapp.feign.technology.entity.model.MsUmappTechnologyUser;

import java.util.List;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyTopListHolderResp {
    private Integer id;//榜单批次id
    private Integer type;//1 日榜  type2 幸运奖
    private List<MsUmappTechnologyUser> listUser;
}
