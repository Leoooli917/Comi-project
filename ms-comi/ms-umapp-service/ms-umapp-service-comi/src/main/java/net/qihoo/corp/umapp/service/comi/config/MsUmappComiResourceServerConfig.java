package net.qihoo.corp.umapp.service.comi.config;

import net.qihoo.corp.ms.umapp.common.feign.config.IMsUmappServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * @quthor cnn
 * @date 2024/3/25
 */
@Configuration
@EnableResourceServer
@SuppressWarnings("all")
public class MsUmappComiResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    MsUmappComiWhiteList whiteList;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] allIgnoredPath = whiteList.getAllIgnoredPath();
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(allIgnoredPath).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(IMsUmappServiceConstants.MS_UMAPP_AUTH).stateless(false);
    }
}
