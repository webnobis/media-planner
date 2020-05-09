package com.webnobis.mediaplanner.element.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Graphics2D;

import org.junit.jupiter.api.Test;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.element.ElementException;
import com.webnobis.mediaplanner.element.Line;
import com.webnobis.mediaplanner.element.XY;
import com.webnobis.mediaplanner.sheet.util.Constants;

/**
 * @author steffen
 * @version 1.00
 */
class PaintUtilTest {

	private static final XY FIRST = new XY(200, 120);

	private static final XY SECOND = new XY(500, 60);

	private static final XY NEXT = new XY(-23, -985); // should be ignored

	private static final int WIDTH = SECOND.getX() - FIRST.getX();

	private static final int HEIGHT = FIRST.getY() - SECOND.getY(); // because from bottom to top

	private static final int CENTER_X = FIRST.getX() + (WIDTH / 2);

	private static final int CENTER_Y = FIRST.getY() - (HEIGHT / 2); // because from bottom to top

	private static final int X = 690;

	private static final int Y = 1020;

	private static final Element[] sElements = { null, new TestLine(FIRST, SECOND, NEXT), new TestLine(FIRST),
			new TestElement(FIRST, SECOND), new TestElement(FIRST), new TestElement() };

	@Test
	void testIsPaintable() {
		assertTrue(PaintUtil.isPaintable(sElements[1]));
		assertFalse(PaintUtil.isPaintable(sElements[0]));
	}

	@Test
	void testGetX() {
		assertEquals(FIRST.getX(), PaintUtil.getX(sElements[1]));
		assertEquals(0, PaintUtil.getX(sElements[5]));
	}

	@Test
	public void testGetY() {
		assertEquals(FIRST.getY(), PaintUtil.getY(sElements[1]));
		assertEquals(0, PaintUtil.getY(sElements[0]));
	}

	@Test
	public void testGetWidth() {
		assertEquals(WIDTH, PaintUtil.getWidth(sElements[3]));
		assertEquals(0, PaintUtil.getWidth(sElements[5]));
	}

	@Test
	public void testGetHeight() {
		assertEquals(HEIGHT, PaintUtil.getHeight(sElements[3]));
		assertEquals(0, PaintUtil.getHeight(sElements[5]));
	}

	@Test
	public void testGetCenterX() {
		assertEquals(CENTER_X, PaintUtil.getCenterX(sElements[1]));
		assertEquals(0, PaintUtil.getCenterX(sElements[0]));
	}

	@Test
	public void testGetCenterY() {
		assertEquals(CENTER_Y, PaintUtil.getCenterY(sElements[1]));
		assertEquals(0, PaintUtil.getCenterY(sElements[0]));
	}

	@Test
	public void testCreateFrom() throws ElementException {
		Element e = PaintUtil.createFrom(sElements[2], X, Y, false);
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
		assertNull(PaintUtil.createFrom(sElements[5], X, Y, false));
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
