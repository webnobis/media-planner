package test.com.webnobis.mediaplanner.element.util;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics2D;

import org.junit.Test;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.element.Line;
import com.webnobis.mediaplanner.element.XY;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Constants;

/**
 * @author steffen
 * @version 1.00
 */
public class PaintUtilFoundTest {

	private static final int MAX_X_OR_Y = 50;

	private static final Element MODULE = new TestElement(15, 35, 30, 40);

	private static final Element LINE_FOUND = new TestLine(0, MAX_X_OR_Y, MAX_X_OR_Y, 0);

	private static final Element LINE_NOT_FOUND = new TestLine(-1 - Constants.LINE_FOUND_SIZE, -1 - Constants.LINE_FOUND_SIZE, -1 - Constants.LINE_FOUND_SIZE, MAX_X_OR_Y + Constants.LINE_FOUND_SIZE
			+ 1, MAX_X_OR_Y + Constants.LINE_FOUND_SIZE + 1, MAX_X_OR_Y + Constants.LINE_FOUND_SIZE + 1);

	@Test
	public void testFoundModule() {
		int foundCount = 0;
		for (int x = 0; x <= MAX_X_OR_Y; x++) {
			for (int y = 0; y <= MAX_X_OR_Y; y++) {
				if (PaintUtil.found(MODULE, x, y)) {
					foundCount++;
				}
			}
		}
		int width = PaintUtil.getWidth(MODULE) + 1;
		int height = PaintUtil.getHeight(MODULE) + 1;
		assertEquals(width * height, foundCount);
	}

	@Test
	public void testFoundLine() {
		int foundCount1 = 0;
		int foundCount2 = 0;
		for (int x = 0; x <= MAX_X_OR_Y; x++) {
			for (int y = 0; y <= MAX_X_OR_Y; y++) {
				if (PaintUtil.found(LINE_FOUND, x, y)) {
					foundCount1++;
				}
				if (PaintUtil.found(LINE_NOT_FOUND, x, y)) {
					foundCount2++;
				}
			}
		}
		int width = PaintUtil.getWidth(LINE_FOUND) + 1;
		int height = PaintUtil.getHeight(LINE_FOUND) + 1;
		assertEquals(width * height, foundCount1);
		assertEquals(0, foundCount2);
	}

	@Line
	private static class TestLine extends TestElement {

		public TestLine(int... pXYs) {
			super(pXYs);
		}

	}

	private static class TestElement extends AbstractElement {

		public TestElement(int... pXYs) {
			super();
			if (pXYs != null) {
				for (int x = 0, y = 1; y < pXYs.length; x += 2, y += 2) {
					super.getPositions().add(new XY(pXYs[x], pXYs[y]));
				}
			}
		}

		@Override
		protected void paintElement(Graphics2D pGraphics) {
			// does nothing
		}

	}

}
