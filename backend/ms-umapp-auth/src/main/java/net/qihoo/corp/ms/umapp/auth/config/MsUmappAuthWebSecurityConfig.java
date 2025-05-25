package net.qihoo.corp.ms.umapp.auth.config;

import net.qihoo.corp.ms.umapp.auth.service.MsUmappUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.auth.config
 * @ClassName: MsUmappAuthWebSecurityConfig
 * @Description:
 * @date 2022-10-12 14:19:51
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("all")
public class MsUmappAuthWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MsUmappUserDetailService msUmappUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(msUmappUserDetailService).passwordEncoder(passwordEncoder());
    }
}
