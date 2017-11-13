package com.cbr.bankseekswing.annotation;

import com.cbr.bankseekswing.pojo.ReferenceItem;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by e.vassaev on 11/13/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EntityEnum {

    Class<? extends ReferenceItem> value();
}
