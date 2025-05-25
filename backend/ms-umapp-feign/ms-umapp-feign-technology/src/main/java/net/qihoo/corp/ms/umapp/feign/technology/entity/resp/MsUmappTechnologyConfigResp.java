package net.qihoo.corp.ms.umapp.feign.technology.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * add cnn
 */
@Data
@Accessors(chain = true)
public class MsUmappTechnologyConfigResp {
    List<Config> configs;
    @Data
    @Accessors(chain = true)
    public static class Config {
        private Integer    id;
        private Integer type;//配置类型 1简介 2.活动规则 3.奖品设置
        private String content;//文案内容
    }
}
