package net.qihoo.corp.ms.umapp.gateway.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.gateway.route
 * @ClassName: MsUmappGatewayDynamicRouteService
 * @Description:
 * @date 2022-10-12 16:34:15
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class MsUmappGatewayDynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;
    @Autowired
    private ApplicationEventPublisher publisher;

    public MsUmappGatewayDynamicRouteService(RouteDefinitionWriter routeDefinitionWriter,RouteDefinitionLocator routeDefinitionLocator) {
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 增加路由
     *
     * @param definition
     * @return
     */
    public String addRoute(RouteDefinition definition) {
        log.info("gateway add route {}", definition);
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    /**
     * 删除路由
     *
     * @param id
     * @return
     */
    public String deleteRoute(String id) {
        try {
            log.info("gateway delete route id {}", id);
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "delete success";
        } catch (Exception e) {
            return "delete fail";
        }
    }

    /**
     * 更新路由
     *
     * @param definitions
     * @return
     */
    public String updateRouteList(List<RouteDefinition> definitions) {
        log.info("gateway update route {}", definitions);
        List<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        if (!CollectionUtils.isEmpty(routeDefinitions)) {
            routeDefinitions.forEach(routeDefinition -> {
                log.info("delete routeDefinition {}", routeDefinition);
                deleteRoute(routeDefinition.getId());
            });
        }
        definitions.forEach(definition -> {
            updateRouteById(definition);
        });
        return "success";
    }

    /**
     * 更新路由
     *
     * @param definition
     * @return
     */
    public String updateRouteById(RouteDefinition definition) {
        try {
            log.info("gateway update route {}", definition);
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail,not find route routeId:" + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route fail";
        }
    }


}
