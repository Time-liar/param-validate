package date.timeliar.validate.annotation;

import java.lang.annotation.*;

/**
 * @Author: TimeLiar
 * @CreateTime: 2016-12-11 11:03
 * @Description: auth-center
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Rules {
    String id() default "";

    Rule[] value() default {};
}
