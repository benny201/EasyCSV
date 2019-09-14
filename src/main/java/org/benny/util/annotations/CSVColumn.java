package org.benny.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVColumn {
    /**
     * column name.
     * no default value
     */
    String name();

    /**
     * column index
     * if no specified index or negative index, this field will be put in the last position
     */
    int index() default Integer.MIN_VALUE;

    /**
     * field need to export
     * default true
     */
    boolean needExport() default true;
}
