package cn.zmn.materialbutton;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.nineoldandroids.animation.ObjectAnimator;

public class FloatingActionButton extends Button {

	private final int NORMAL_STATUS = 0;
	private final int DISABLE_STATUS = 1;
	private final int FOCUS_STATUS = 2;
	private final int PRESSED_STATUS = 3;
	private int status = NORMAL_STATUS;
	
	private Paint defaultPaint;
	private Paint focusPaint;
	
	private int width;
	private int height;
	
	private Bitmap icon;
	private int iconWidth;
	private int iconHeight;
	private int defaultBgColor = 0xff33B5E5;
	private int focusBgcolor = 0xff0099CC;
	
	private int degree;
	private int rotateDegree = 0;
	private static int NORMAL = 0;
	private static int REVERSE = 1;
	private int rotateMode = NORMAL;
	private int rotateTime = 1000;
    private PaintFlagsDrawFilter mDrawFilter;
	private ObjectAnimator objectAnimator;
	
	private OnTwoWayListener listener;

	public FloatingActionButton(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
        init();
	}

	public FloatingActionButton(Context context, AttributeSet attrs) {
		super(context, attrs);
        init();
	}

	public FloatingActionButton(Context context) {
		super(context);
		init();
	}

	private void init() {
		defaultPaint = new Paint();
		defaultPaint.setAntiAlias(true);
		defaultPaint.setColor(defaultBgColor);

		focusPaint = new Paint();
		focusPaint.setAntiAlias(true);
		focusPaint.setColor(focusBgcolor);

		icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_new);
		getIconParams();

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        this.setBackgroundResource(0);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		if (status == DISABLE_STATUS) {
			return true;
		}
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			status = FOCUS_STATUS;

			break;

		case MotionEvent.ACTION_MOVE:
			float x = event.getX();
			float y = event.getY();
			if (x < 0 || x > width || y > height || y < 0) {
				status = NORMAL_STATUS;
			}
			break;

        case MotionEvent.ACTION_CANCEL:
            status = NORMAL_STATUS;
            break;

		case MotionEvent.ACTION_UP:
			if (status != FOCUS_STATUS) {
				return true;
			}
			status = PRESSED_STATUS;

			objectAnimator = ObjectAnimator.ofFloat(this, "rotation", rotateDegree);
			objectAnimator.setDuration(rotateTime);
			objectAnimator.start();

			if (rotateMode == REVERSE && rotateDegree != 0) {
				rotateDegree -= degree;
				if (listener != null) {
					listener.onForward();
				}
			} else {
				rotateDegree += degree;
				if (listener != null) {
					listener.onBack();
				}
			}

			break;
		}
		invalidate();
		return super.dispatchTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
        this.setBackgroundResource(0);
        canvas.setDrawFilter(mDrawFilter);
		switch (status) {
		case FOCUS_STATUS:
			canvas.drawCircle(width / 2, height / 2, height / 2, focusPaint);
			break;

		default:
			canvas.drawCircle(width / 2, height / 2, height / 2, defaultPaint);
			break;
		}

		canvas.drawBitmap(icon, width / 2 - iconWidth / 2, height / 2
				- iconHeight / 2, defaultPaint);
		super.onDraw(canvas);
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
		getIconParams();
	}

	public void setIcon(int resourceID) {
		this.icon = BitmapFactory.decodeResource(getResources(), resourceID);
		getIconParams();
	}

	private void getIconParams() {
		iconWidth = icon.getWidth();
		iconHeight = icon.getHeight();
	}

	@Override
	public void setBackgroundColor(int color) {
		this.defaultBgColor = color;
		defaultPaint.setColor(defaultBgColor);
	}

	public void setFocusBgcolor(int focusBgcolor) {
		this.focusBgcolor = focusBgcolor;
		focusPaint.setColor(focusBgcolor);
	}

	public void setRotate(int rotateDegree) {
		this.rotateDegree = rotateDegree;
		this.degree = rotateDegree;
		this.rotateMode = NORMAL;
	}

	public void setRotateRepeatReverse(int rotateDegree) {
		this.rotateDegree = rotateDegree;
		this.degree = rotateDegree;
		this.rotateMode = REVERSE;
	}

	public void setRotateDuration(int t) {
		this.rotateTime = t;
	}

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        width = this.getWidth();
        height = this.getHeight();
    }

	public void setOnTwoWayListener(OnTwoWayListener listener) {
		this.listener = listener;
	}

	public interface OnTwoWayListener {
		public void onForward();
		public void onBack();
	}

}
