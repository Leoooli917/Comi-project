package net.qihoo.corp.umapp.service.comi.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @quthor cnn
 * @date 2024/3/20
 */
@Configuration
@EnableWebSecurity
public class UmappServiceComiSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .formLogin().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/*").permitAll();
    }
}
