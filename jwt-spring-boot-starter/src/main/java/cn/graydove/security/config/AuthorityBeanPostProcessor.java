package cn.graydove.security.config;

import cn.graydove.security.annotation.Authorization;
import cn.graydove.security.token.authority.AuthorizeRequestBuilder;
import cn.graydove.security.token.manager.AuthorityAdapter;
import cn.graydove.security.utils.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;

@Slf4j
@Configuration
public class AuthorityBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private AuthorityAdapter authorityAdapter;


    private boolean isController(Object bean) {
        return AnnotatedElementUtils.isAnnotated(bean.getClass(), Controller.class);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isController(bean)) {
            Class<?> clazz = bean.getClass();
            RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(clazz, RequestMapping.class);
            Authorization authorization = AnnotationUtils.findAnnotation(clazz, Authorization.class);
            String basePath = "";
            if (requestMapping != null) {
                //若controller上有注解
                String[] path = requestMapping.path();
                if (path.length > 1) {
                    log.warn("多个路径只匹配第一个");
                } else if (path.length == 1) {
                    basePath = path[0];
                }
            }
            Method[] methods = clazz.getMethods();
            for (Method method: methods) {
                Authorization authorizationMethod = AnnotationUtils.findAnnotation(method, Authorization.class);
                if (authorizationMethod == null) {
                    authorizationMethod = authorization;
                }
                if (authorizationMethod != null) {
                    requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
                    resolveRequestMapping(authorizationMethod, requestMapping, basePath);
                }
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private void resolveRequestMapping(Authorization authorization, RequestMapping requestMapping, String basePath) {
        if (requestMapping == null) {
            return;
        }
        String[] path = requestMapping.path();
        for (int i = 0; i < path.length; ++i) {
            path[i] = PathUtils.join(basePath, path[i]);
        }
        RequestMethod[] methods = requestMapping.method();
        if (methods.length == 0) {
            resolveAuthorization(authorization, path, null);
        } else {
            for (RequestMethod requestMethod: methods) {
                resolveAuthorization(authorization, path, requestMethod);
            }
        }
    }

    private void resolveAuthorization(Authorization authorization, String[] path, RequestMethod requestMethod) {
        AuthorizeRequestBuilder authorizeRequestBuilder = authorityAdapter.antMatchers(requestMethod, path);
        if (authorization.permitAll()) {
            authorizeRequestBuilder.permitAll();
        } else {
            String[] authorities = authorization.authorities();
            if (authorities.length == 0) {
                authorizeRequestBuilder.authenticated();
            } else if (authorization.anyAuthority()) {
                authorizeRequestBuilder.hasAnyAuthority(authorities);
            } else {
                authorizeRequestBuilder.hasAuthority(authorities);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        authorityAdapter = applicationContext.getBean(AuthorityAdapter.class);
    }
}
