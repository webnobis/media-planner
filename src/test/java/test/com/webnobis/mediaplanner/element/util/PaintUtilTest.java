package test.com.webnobis.mediaplanner.element.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.awt.Graphics2D;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.element.ElementException;
import com.webnobis.mediaplanner.element.Line;
import com.webnobis.mediaplanner.element.XY;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Constants;

/**
 * @author steffen
 * @version 1.00
 */
@RunWith(Theories.class)
public class PaintUtilTest {

	private static final XY FIRST = new XY(200, 120);

	private static final XY SECOND = new XY(500, 60);

	private static final XY NEXT = new XY(-23, -985); // should be ignored

	private static final int WIDTH = SECOND.getX() - FIRST.getX();

	private static final int HEIGHT = FIRST.getY() - SECOND.getY(); // because from bottom to top

	private static final int CENTER_X = FIRST.getX() + (WIDTH / 2);

	private static final int CENTER_Y = FIRST.getY() - (HEIGHT / 2); // because from bottom to top

	private static final int X = 690;

	private static final int Y = 1020;

	@DataPoints
	public static final Element[] sElements = { null, new TestLine(FIRST, SECOND, NEXT), new TestLine(FIRST), new TestElement(FIRST, SECOND), new TestElement(FIRST), new TestElement() };

	@Theory
	public void testIsPaintable(Element pElement) {
		// only 1, 2 and 3 are correct filled
		assertEquals(PaintUtil.isPaintable(pElement), sElements[1] == pElement || sElements[2] == pElement || sElements[3] == pElement);
	}

	@Theory
	public void testGetX(Element pElement) {
		if (sElements[1] == pElement || sElements[2] == pElement || sElements[3] == pElement) {
			assertEquals(FIRST.getX(), PaintUtil.getX(pElement));
		} else {
			assertEquals(0, PaintUtil.getX(pElement));
		}
	}

	@Theory
	public void testGetY(Element pElement) {
		if (sElements[1] == pElement || sElements[2] == pElement || sElements[3] == pElement) {
			assertEquals(FIRST.getY(), PaintUtil.getY(pElement));
		} else {
			assertEquals(0, PaintUtil.getY(pElement));
		}
	}

	@Theory
	public void testGetWidth(Element pElement) {
		if (sElements[1] == pElement || sElements[3] == pElement) {
			assertEquals(WIDTH, PaintUtil.getWidth(pElement));
		} else {
			assertEquals(0, PaintUtil.getWidth(pElement));
		}
	}

	@Theory
	public void testGetHeight(Element pElement) {
		if (sElements[1] == pElement || sElements[3] == pElement) {
			assertEquals(HEIGHT, PaintUtil.getHeight(pElement));
		} else {
			assertEquals(0, PaintUtil.getHeight(pElement));
		}
	}

	@Theory
	public void testGetCenterX(Element pElement) {
		if (sElements[1] == pElement || sElements[3] == pElement) {
			assertEquals(CENTER_X, PaintUtil.getCenterX(pElement));
		} else {
			assertEquals(0, PaintUtil.getCenterX(pElement));
		}
	}

	@Theory
	public void testGetCenterY(Element pElement) {
		if (sElements[1] == pElement || sElements[3] == pElement) {
			assertEquals(CENTER_Y, PaintUtil.getCenterY(pElement));
		} else {
			assertEquals(0, PaintUtil.getCenterY(pElement));
		}
	}

	@Theory
	public void testCreateFrom(Element pElement) throws ElementException {
		Element e = PaintUtil.createFrom(pElement, X, Y, false);
		if (sElements[1] == pElement || sElements[2] == pElement || sElements[3] == pElement) {
			assertNotNull(e);
			assertFalse(e.getPositions().isEmpty());
			if (e.isLine()) {
				assertEquals(1, e.getPositions().size());
				assertEquals(X, e.getPositions().get(0).getX());
				assertEquals(Y, e.getPositions().get(0).getY());
			} else {
				assertEquals(2, e.getPositions().size());
				assertEquals(WIDTH, Math.abs(e.getPositions().get(0).getX() - e.getPositions().get(1).getX()));
				assertEquals(HEIGHT, Math.abs(e.getPositions().get(0).getY() - e.getPositions().get(1).getY()));
			}
		} else {
			assertNull(e);
		}
	}

	@Test
	public void testCreateFromRastered() throws ElementException {
		Element e = PaintUtil.createFrom(new TestElement(FIRST, SECOND), X, Y, true);
		assertNotNull(e);
		assertFalse(e.getPositions().isEmpty());
		assertEquals(2, e.getPositions().size());
		int x = Math.max(0, (X / Constants.RASTER) * Constants.RASTER - (WIDTH / 2));
		int y = Math.max(0, (Y / Constants.RASTER) * Constants.RASTER - (HEIGHT / 2));
		assertEquals(x, e.getPositions().get(0).getX());
		assertEquals(y, e.getPositions().get(0).getY());
		assertEquals(x + WIDTH, e.getPositions().get(1).getX());
		assertEquals(y + HEIGHT, e.getPositions().get(1).getY());
	}

	@Line
	public static class TestLine extends TestElement {

		public TestLine() {
			super();
		}

		public TestLine(XY... pXYs) {
			super(pXYs);
		}

	}

	public static class TestElement extends AbstractElement {

		public TestElement() {
			super();
		}

		public TestElement(XY... pXYs) {
			super();
			if (pXYs != null) {
				for (XY xy : pXYs) {
					super.getPositions().add(xy);
				}
			}
		}

		@Override
		protected void paintElement(Graphics2D pGraphics) {
			// does nothing
		}

	}

}
