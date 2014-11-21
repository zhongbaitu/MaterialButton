package cn.zmn.materialbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class RaisedButton extends FlatButton {

	private final int NORMAL_STATUS = 0;
	private final int DISABLE_STATUS = 1;
	private final int FOCUS_STATUS = 2;
	private final int PRESSED_STATUS = 3;

	public RaisedButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public RaisedButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RaisedButton(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		bgPaint.setColor(backgroundColor);
		switch (status) {
		case FOCUS_STATUS:
			bgPaint.setAlpha((int) (bgAlpha));
			canvas.drawRoundRect(bgRectF, ROUND_CORNET_RADIUS, ROUND_CORNET_RADIUS, bgPaint);
			focusPaint.setAlpha((int) (bgAlpha));
			canvas.drawCircle(width / 2, height / 2, radius, focusPaint);
			break;

		case PRESSED_STATUS:
			bgPaint.setAlpha((int) (255));
			canvas.drawRoundRect(bgRectF, ROUND_CORNET_RADIUS, ROUND_CORNET_RADIUS, bgPaint);
			focusPaint.setAlpha((int) (bgAlpha));
			canvas.drawCircle(width / 2, height / 2, radius, focusPaint);
			break;

		default:
			bgPaint.setAlpha((int) (bgAlpha));
			canvas.drawRoundRect(bgRectF, ROUND_CORNET_RADIUS, ROUND_CORNET_RADIUS, bgPaint);
			break;
		}

		super.onDraw(canvas);
	}

	private void setRadius(float radius) {
		this.radius = radius;
		invalidate();
	}

	private float getBgAlpha() {
		return bgAlpha;
	}

	private void setBgAlpha(float bgAlpha) {
		this.bgAlpha = bgAlpha;
		invalidate();
	}

}
