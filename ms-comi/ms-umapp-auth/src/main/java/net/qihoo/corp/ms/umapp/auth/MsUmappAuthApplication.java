package net.qihoo.corp.ms.umapp.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"net.qihoo.corp.ms.umapp.*"})
@SpringBootApplication(scanBasePackages = {"net.qihoo.corp.ms.umapp.*", "net.qihoo.corp.ms.umapp.auth"})
public class MsUmappAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUmappAuthApplication.class, args);
    }

}
