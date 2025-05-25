package net.qihoo.corp.ms.umapp.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.user
 * @ClassName: MsUmappUserApplication
 * @Description:
 * @date 2022-10-11 19:12:13
 */
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"net.qihoo.corp.ms.umapp.feign.*"})
@SpringBootApplication(scanBasePackages = {"net.qihoo.corp.ms.umapp.*", "net.qihoo.corp.ms.umapp.user"})
public class MsUmappUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUmappUserApplication.class, args);
    }
}
