package net.qihoo.corp.ms.umapp.gateway.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.gateway.entity
 * @ClassName: MsUmappGatewayTokenInfo
 * @Description:
 * @date 2022-10-12 16:04:18
 */
@Data
public class MsUmappGatewayTokenInfo {
    /**
     * token是否可用
     */
    private boolean active;
    /**
     * 哪个客户端申请的token
     */
    private String client_id;
    /**
     * 认证成功后返回的权限
     */
    private String[] scope;
    /**
     * 用户名
     */
    private String user_name;
    /**
     * 向哪些资源服务器访问
     */
    private String[] aud;
    /**
     * token的过期时间
     */
    private Date exp;
    /**
     * 该用户的权限
     */
    private String[] authorities;
}
