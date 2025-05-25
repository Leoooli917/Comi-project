package net.qihoo.corp.ms.umapp.user.config;

import net.qihoo.corp.ms.umapp.common.feign.config.IMsUmappServiceConstants;
import net.qihoo.corp.ms.umapp.user.whitelist.MsUmappUserWhiteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.user.config
 * @ClassName: MsUmappUserResourceServerConfig
 * @Description:
 * @date 2022-10-12 17:48:33
 */
@Configuration
@EnableResourceServer
@SuppressWarnings("all")
public class MsUmappUserResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    MsUmappUserWhiteList whiteList;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] ignorePath = whiteList.getAllIgnoredPath();
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(ignorePath).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(IMsUmappServiceConstants.MS_UMAPP_USER).stateless(false);
    }
}
