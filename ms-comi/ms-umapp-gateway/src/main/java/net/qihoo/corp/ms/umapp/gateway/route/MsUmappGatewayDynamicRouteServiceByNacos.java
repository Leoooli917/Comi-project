package net.qihoo.corp.ms.umapp.gateway.route;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import net.qihoo.corp.ms.umapp.gateway.config.MsUmappGatewayRouteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.gateway.route
 * @ClassName: MsUmappDynamicRouteServiceByNacos
 * @Description:
 * @date 2022-10-12 16:36:25
 */
@Slf4j
@Component
@DependsOn({"msUmappGatewayRouteConfig"})
@SuppressWarnings("all")
public class MsUmappGatewayDynamicRouteServiceByNacos {

    @Autowired
    private MsUmappGatewayDynamicRouteService dynamicRouteService;

    private ConfigService configService;

    @PostConstruct
    public void init() {
        log.info("gateway route init...");
        try {
            configService = initConfigService();
            if (configService == null) {
                log.warn("initConfigService fail");
                return;
            }
            String configInfo = configService.getConfig(
                    MsUmappGatewayRouteConfig.NACOS_ROUTE_DATA_ID,
                    MsUmappGatewayRouteConfig.NACOS_ROUTE_GROUP,
                    MsUmappGatewayRouteConfig.DEFAULT_TIMEOUT
            );
            log.info("获取网关当前配置:\n\r{}", configInfo);
            List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
            if (!CollectionUtils.isEmpty(definitionList)) {
                definitionList.forEach(definition -> {
                    log.info("update route: {}", definition.toString());
                    dynamicRouteService.addRoute(definition);
                });
            }
        } catch (Exception e) {
            log.error("初始化网关路由时发生错误{}", e);
        }
        dynamicRouteByNacosListener(MsUmappGatewayRouteConfig.NACOS_ROUTE_DATA_ID, MsUmappGatewayRouteConfig.NACOS_ROUTE_GROUP);
    }

    /**
     * 初始化网关路由 nacos config
     *
     * @return
     */
    private ConfigService initConfigService() {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", MsUmappGatewayRouteConfig.NACOS_SERVER_ADDR);
            properties.setProperty("namespace", MsUmappGatewayRouteConfig.NACOS_ROUTE_NAMESPACE);
            properties.setProperty("username", MsUmappGatewayRouteConfig.NACOS_ROUTE_USERNAME);
            properties.setProperty("password", MsUmappGatewayRouteConfig.NACOS_ROUTE_PASSWORD);
            properties.setProperty("group", MsUmappGatewayRouteConfig.NACOS_ROUTE_GROUP);
            return configService = NacosFactory.createConfigService(properties);
        } catch (Exception e) {
            log.error("初始化网关路由时发生错误", e);
            return null;
        }
    }

    /**
     * 监听nacos下发的动态路由配置
     *
     * @param dataId
     * @param group
     */
    public void dynamicRouteByNacosListener(String dataId, String group) {
        try {
            configService.addListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    log.info("getExecutor\n\r");
                    return null;
                }
                @Override
                public void receiveConfigInfo(String s) {
                    log.info("进行网关更新:\n\r{}", s);
                    List<RouteDefinition> definitionList = JSON.parseArray(s, RouteDefinition.class);
                    log.info("update route: {}", definitionList.toString());
                    dynamicRouteService.updateRouteList(definitionList);
                }
            });
        } catch (Exception e) {
            log.error("从nacos接收动态路由配置出错{}", e);
        }
    }
}
