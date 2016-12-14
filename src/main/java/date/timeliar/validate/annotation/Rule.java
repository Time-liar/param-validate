package date.timeliar.validate.annotation;

import date.timeliar.validate.enums.ParamPosition;
import date.timeliar.validate.enums.ParamType;

/**
 * @Author: TimeLiar
 * @CreateTime: 2016-12-11 11:04
 * @Description: auth-center
 */
public @interface Rule {
    String name() default "";

    String description() default "";

    String correctPattern() default ".*";

    String wrongPattern() default "^.";

    ParamPosition position() default ParamPosition.QUERY;

    ParamType type() default ParamType.STRING;

    Class<?> objectType() default Object.class;

    String[] limit() default {};

    boolean necessary() default true;

    int max() default 0;

    int min() default 0;

    String defaultValue() default "";
}
