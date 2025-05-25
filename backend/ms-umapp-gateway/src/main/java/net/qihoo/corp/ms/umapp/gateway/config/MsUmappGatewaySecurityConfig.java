package net.qihoo.corp.ms.umapp.gateway.config;

import net.qihoo.corp.ms.umapp.gateway.business.MsUmappGatewayWhiteListBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.gateway.config
 * @ClassName: MsUmappGatewaySecurityConfig
 * @Description:
 * @date 2022-10-12 16:20:05
 */
@Configuration
@EnableWebFluxSecurity
public class MsUmappGatewaySecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity security) throws Exception {
        security.csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers("/**")
                .permitAll()
                .anyExchange()
                .authenticated();
        return security.build();
    }
}
