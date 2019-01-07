package com.it.ymk.bubble.component.log.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解 拦截controller
 *
 * @author yanmingkun
 * @date 2018-12-04 13:54
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopLog {
    String description() default "";
}
