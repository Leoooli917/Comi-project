package net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangchuanhai
 * @version 1.0
 * @PackageName: net.qihoo.corp.ms.umapp.feign.anniversary.entity.resp
 * @ClassName: MsUmappAnnBaseResp
 * @Description:
 * @date 2021/11/23 5:28 下午
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class MsUmappAnnBaseResp<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int rcode;
    private String rmsg;
    private T data;
    private int total;
}
