package cn.graydove.security.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {

}
