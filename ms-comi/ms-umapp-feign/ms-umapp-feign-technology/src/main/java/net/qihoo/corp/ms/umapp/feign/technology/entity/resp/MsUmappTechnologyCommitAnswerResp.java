package net.qihoo.corp.ms.umapp.feign.technology.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyCommitAnswerResp {
    Integer id;
    Integer score;
    Long time;
    String msg;
}
