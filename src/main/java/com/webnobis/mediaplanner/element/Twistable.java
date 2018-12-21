package com.webnobis.mediaplanner.element;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Twistable {

	Direction defaultDirection();

	Direction[] allowedDirections() default { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };

}
