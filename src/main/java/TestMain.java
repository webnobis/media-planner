import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXB;

import com.webnobis.mediaplanner.sheet.util.SheetElements;
import com.webnobis.mediaplanner.sheet.util.XmlSheet;

public class TestMain {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Path p = Paths.get("/home/steffen/Dokumente/medien/schaltplaene/strom/ev_og/np_strom_og1v6.xml");
		System.out.println(Files.exists(p));
		XmlSheet xmlSheet = JAXB.unmarshal(p.toFile(), XmlSheet.class);
		System.out.println(xmlSheet);
		SheetElements e = new SheetElements(xmlSheet.getElements(), xmlSheet.getWidth(), xmlSheet.getHeight());
		System.out.println(e);
	}

}
