package net.qihoo.corp.ms.umapp.feign.evaluate.entity.feign;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .queryMapEncoder(new CustomerQueryMapEncoder());
    }
}
