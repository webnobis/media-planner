package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Direction;
import com.webnobis.mediaplanner.element.Twistable;
import com.webnobis.mediaplanner.element.util.DescriptionPainter;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.element.util.RectanglePainter;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Describable(allowedKeys = { "position", "phase", "characteristic", "value" }, maxCount = 4)
@Twistable(defaultDirection = Direction.NORTH)
@Group(palette = { Palette.POWER })
public class Fuse extends AbstractElement {

	public static final String POSITION_KEY = "position";

	public static final String PHASE_KEY = "phase";

	public static final String CHARACTERISTIC_KEY = "characteristic";

	public static final String VALUE_KEY = "value";

	public static final float FUSE_HEIGHT_FACTOR = 0.7f;

	public static final float FUSE_WIDTH_FACTOR = FUSE_HEIGHT_FACTOR / 3;

	/**
	 * @see com.webnobis.mediaplanner.element.AbstractElement#clearPaintElement(java.awt.Graphics2D)
	 */
	@Override
	protected void clearPaintElement(Graphics2D pGraphics) {
		RectanglePainter.paint(this, pGraphics, PaintUtil.getWidth(this), PaintUtil.getHeight(this), true);
	}

	@Override
	protected void paintElement(Graphics2D pGraphics) {
		int width = PaintUtil.getWidth(this);
		int height = PaintUtil.getHeight(this);
		RectanglePainter.paint(this, pGraphics, width, height, false);

		int x = PaintUtil.getCenterX(this);
		int y1 = PaintUtil.getY(this);
		int y2 = y1 + height;
		pGraphics.drawLine(x, y1, x, y2);

		width *= FUSE_WIDTH_FACTOR;
		height *= FUSE_HEIGHT_FACTOR;
		RectanglePainter.paint(this, pGraphics, width, height, false);

		if (!super.getDescriptions().isEmpty()) {
			String position = null;
			String phase = null;
			String characteristic = null;
			String value = null;
			for (Description description : super.getDescriptions()) {
				if (POSITION_KEY.equals(description.getKey())) {
					position = description.getValue();
				}
				if (PHASE_KEY.equals(description.getKey())) {
					phase = description.getValue();
				}
				if (CHARACTERISTIC_KEY.equals(description.getKey())) {
					characteristic = description.getValue();
				}
				if (VALUE_KEY.equals(description.getKey())) {
					value = description.getValue();
				}
			}
			StringBuilder sb = new StringBuilder();
			if (position != null) {
				sb.append(position).append(' ');
			}
			if (phase != null) {
				sb.append(phase).append('-');
			}
			if (characteristic != null) {
				sb.append(characteristic);
			}
			if (value != null) {
				sb.append(value);
			}
			if (sb.length() > 0) {
				DescriptionPainter.paint(this, pGraphics, sb.toString(), super.getDirection());
			}
		}
	}

}
