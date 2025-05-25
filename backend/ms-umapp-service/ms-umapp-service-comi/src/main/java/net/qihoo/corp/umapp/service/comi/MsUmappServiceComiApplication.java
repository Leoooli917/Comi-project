package net.qihoo.corp.umapp.service.comi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"net.qihoo.corp.ms.umapp.feign"})
@SpringBootApplication(scanBasePackages = {"net.qihoo.corp.ms.umapp.common.*","net.qihoo.corp.umapp.service.comi"})
@EnableScheduling
public class MsUmappServiceComiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsUmappServiceComiApplication.class, args);
    }
}
