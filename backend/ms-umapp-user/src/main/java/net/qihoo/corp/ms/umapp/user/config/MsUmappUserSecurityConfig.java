package net.qihoo.corp.ms.umapp.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.user.config
 * @ClassName: MsUmappUserSecurityConfig
 * @Description:
 * @date 2022-10-12 17:55:14
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("all")
public class MsUmappUserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .formLogin()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/*")
                .permitAll();
    }
}
