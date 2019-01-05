module com.webnobis.mediaplanner {
	
	requires java.base;
	requires java.desktop;
	requires java.xml.bind;
	
	requires org.slf4j;

	exports com.webnobis.mediaplanner;
	
	uses java.awt.Toolkit;
	uses javax.xml.bind.JAXB;
	
}