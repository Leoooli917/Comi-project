package net.qihoo.corp.ms.umapp.gateway.hook;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.gateway.route.CachingRouteLocator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.gateway.hook
 * @ClassName: MsUmappGatewayCachingRouteLocatorHook
 * @Description:
 * @date 2022-10-12 16:27:48
 */
@Component
public class MsUmappGatewayCachingRouteLocatorHook implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
        if (targetClass.getName().equals(CachingRouteLocator.class.getName())) {
            ProxyFactory factory = new ProxyFactory();
            factory.addInterface(Ordered.class);
            factory.setTarget(bean);
            factory.setProxyTargetClass(true);
            factory.addAdvice((MethodInterceptor) methodInvocation -> {
                if ("getOrder".equals(methodInvocation.getMethod().getName())) {
                    //这里就简单返回1吧，只要比WeightCalculator的order小就行了.
                    return 1;
                } else {
                    return methodInvocation.proceed();
                }
            });
            return factory.getProxy();
        }
        return bean;
    }
}
