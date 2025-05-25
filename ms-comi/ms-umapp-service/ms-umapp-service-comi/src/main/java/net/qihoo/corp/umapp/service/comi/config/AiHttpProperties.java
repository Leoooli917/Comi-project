package net.qihoo.corp.umapp.service.comi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gpt")
@Data
public class AiHttpProperties {
    private String bingo_access_key;
    private String bingo_secret_key;
    private String secret_gpt;
//    private static final String ACCESS_KEY = "MDRhYzVlNGUtNTEz"; // Replace with actual access key
//    private static final String SECRET_KEY = "ZDQ0YWMyYWUtZGRiYS00YzgwLTliM2ItZDkyYzcxNDk2M2Nh"; // Replace with actual secret key
//    public static final String Secret_gpt = "fk3101625831.mvQEkPYJ90JWVWN9nMwfy1j6gWMMLj0Vad3a694b";

}