package com.webnobis.mediaplanner.element.palette;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.webnobis.mediaplanner.sheet.util.Palette;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Group {
	
	Palette[] palette();

}
