package net.qihoo.corp.ms.umapp.feign.evaluate.entity.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 项目请求类
 *
 * @author LBin
 * @date 2024-01-17 16:18:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectReq extends SignModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目名称
     */
    private String query = "";

    /**
     * 用户域账号
     */
    private String user_name = "";
}
