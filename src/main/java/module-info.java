module com.webnobis.mediaplanner {

	requires java.base;

	requires org.slf4j;

	requires java.xml.bind;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.swing;

	exports com.webnobis.mediaplanner;

	uses java.awt.Toolkit;
	uses javax.xml.bind.JAXB;

	opens com.webnobis.mediaplanner.element.palette to javafx, com.webnobis.mediaplanner.sheet.util;
	opens com.webnobis.mediaplanner.element;
	opens com.webnobis.mediaplanner.element.util;
	opens com.webnobis.mediaplanner.sheet.util;
	opens com.webnobis.mediaplanner.sheet to javafx;

}