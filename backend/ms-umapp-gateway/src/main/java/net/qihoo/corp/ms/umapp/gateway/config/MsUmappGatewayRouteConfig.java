package net.qihoo.corp.ms.umapp.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.gateway.config
 * @ClassName: MsUmappGatewayRouteConfig
 * @Description:
 * @date 2022-10-12 16:15:34
 */
@Configuration
public class MsUmappGatewayRouteConfig {

    public static final long DEFAULT_TIMEOUT = 30000;
    public static String NACOS_SERVER_ADDR;
    public static String NACOS_ROUTE_DATA_ID;
    public static String NACOS_ROUTE_GROUP;
    public static String NACOS_ROUTE_USERNAME;
    public static String NACOS_ROUTE_PASSWORD;
    public static String NACOS_ROUTE_NAMESPACE;

    @Value("${spring.profiles.nacos.domain}")
    public void setNacosServerAddr(String nacosServerAddr){
        NACOS_SERVER_ADDR = nacosServerAddr;
    }

    @Value("${spring.profiles.nacos.data-id}")
    public void setNacosRouteDataId(String nacosRouteDataId){
        NACOS_ROUTE_DATA_ID = nacosRouteDataId;
    }

    @Value("${spring.profiles.nacos.group}")
    public void setNacosRouteGroup(String nacosRouteGroup){
        NACOS_ROUTE_GROUP = nacosRouteGroup;
    }

    @Value("${spring.profiles.nacos.username}")
    public void setNacosRouteUsername(String nacosRouteUsername) {
        NACOS_ROUTE_USERNAME = nacosRouteUsername;
    }

    @Value("${spring.profiles.nacos.password}")
    public void setNacosRoutePassword(String nacosRoutePassword) {
        NACOS_ROUTE_PASSWORD = nacosRoutePassword;
    }

    @Value("${spring.profiles.nacos.namespace}")
    public void setNacosRouteNamespace(String nacosRouteNamespace) {
        NACOS_ROUTE_NAMESPACE = nacosRouteNamespace;
    }
}
