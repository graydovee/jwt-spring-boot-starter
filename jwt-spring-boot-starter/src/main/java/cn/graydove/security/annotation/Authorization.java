package cn.graydove.security.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 权限注解，可放在controller类上或方法上
 * 若方法上无{@Code @Authorization}注解，则使用类上的{@Code @Authorization}注解
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {

    /**
     *
     * 所需权限，若为空，则只需要登录权限
     * @return 权限
     */
    @AliasFor("authorities")
    String[] value() default {};

    @AliasFor("value")
    String[] authorities() default {};

    /**
     *
     * @return 若为false，则需满足所有权限，否则只需要一个权限
     */
    boolean anyAuthority() default true;

    /**
     *
     * @return true则为
     */
    boolean permitAll() default false;
}
