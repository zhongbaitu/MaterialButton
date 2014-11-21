package cn.zmn.materialbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class FlatButton extends Button {

	private final int NORMAL_STATUS = 0;
	private final int DISABLE_STATUS = 1;
	private final int FOCUS_STATUS = 2;
	private final int PRESSED_STATUS = 3;
	protected int status = NORMAL_STATUS;
	
	protected final int NORMAL_TEXTCOLOR = 0xff000000;
	protected final int DISABLE_TEXTCOLOR = 0xffCCCCCC;
	protected int textColor = NORMAL_TEXTCOLOR;
	protected int backgroundColor = 0xff999999;
	protected int focusColor = 0xff777777;
	
	protected Paint bgPaint;
	protected Paint focusPaint;
	
	protected int width, height;
	
	protected float radius = 10;
	protected float minRadius;
	protected float maxRadius;
	
	protected ObjectAnimator objectAnimator;
	protected AnimatorSet animatorSet;
	
	protected float bgAlpha = 255;
	
	protected RectF bgRectF;
	
	protected final int ROUND_CORNET_RADIUS = 4;

    private PaintFlagsDrawFilter mDrawFilter;

    public FlatButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        init();
	}

	public FlatButton(Context context, AttributeSet attrs) {
		super(context, attrs);
        init();
	}

	public FlatButton(Context context) {
		super(context);
		init();
	}

	protected void init() {
		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
		bgPaint.setColor(backgroundColor);

		focusPaint = new Paint();
		focusPaint.setAntiAlias(true);
		focusPaint.setColor(focusColor);

		animatorSet = new AnimatorSet();

		bgRectF = new RectF();

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
			objectAnimator = ObjectAnimator.ofFloat(this, "radius", minRadius, maxRadius).setDuration(1000);
			objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
			objectAnimator.setRepeatCount(Integer.MAX_VALUE);
			objectAnimator.start();
			break;

		case MotionEvent.ACTION_MOVE:
			float x = event.getX();
			float y = event.getY();
			if (x < 0 || x > width || y > height || y < 0) {
				status = NORMAL_STATUS;
				objectAnimator.cancel();
				setRadius(0);
			}
			break;

        case MotionEvent.ACTION_CANCEL:
            status = NORMAL_STATUS;
            objectAnimator.cancel();
            setRadius(0);
            break;

		case MotionEvent.ACTION_UP:
			if (status != FOCUS_STATUS) {
				return true;
			}
			status = PRESSED_STATUS;
			objectAnimator.cancel();
			animatorSet.playTogether(
					ObjectAnimator.ofFloat(this, "radius", radius, radius * 2),
					ObjectAnimator.ofFloat(this, "bgAlpha", 0));
			animatorSet.setDuration(500);

			animatorSet.addListener(new Animator.AnimatorListener() {

				@Override
				public void onAnimationStart(Animator arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animator arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animator arg0) {
					status = NORMAL_STATUS;
					bgAlpha = 255;
					setRadius(0);
				}

				@Override
				public void onAnimationCancel(Animator arg0) {
					// TODO Auto-generated method stub

				}
			});
			animatorSet.start();
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
        this.setBackgroundResource(0);
        canvas.setDrawFilter(mDrawFilter);
		switch (status) {
		case NORMAL_STATUS:
			break;
		case DISABLE_STATUS:
			break;

		default:
			bgPaint.setAlpha((int) (bgAlpha));
			canvas.drawRoundRect(bgRectF, ROUND_CORNET_RADIUS, ROUND_CORNET_RADIUS, bgPaint);
			focusPaint.setAlpha((int) (bgAlpha));
			canvas.drawCircle(width / 2, height / 2, radius, focusPaint);
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

	@Override
	public void setClickable(boolean clickable) {
		if (clickable) {
			this.textColor = NORMAL_TEXTCOLOR;
		} else {
			status = DISABLE_STATUS;
			this.textColor = DISABLE_TEXTCOLOR;
		}
		this.setTextColor(textColor);
		invalidate();
	}

	@Override
	public void setBackgroundColor(int color) {
        this.backgroundColor = color;
        bgPaint.setColor(backgroundColor);
	}

	public void setFocusColor(int focusColor) {
        this.focusColor = focusColor;
		focusPaint.setColor(focusColor);
	}

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        width = this.getWidth();
        height = this.getHeight();
        if (this.getWidth() > this.getHeight()) {
            minRadius = width * 3 / 8;
        } else {
            minRadius = height * 3 / 8;
        }
        maxRadius = minRadius + 10;

        bgRectF.left = 0;
        bgRectF.top = 0;
        bgRectF.right = width;
        bgRectF.bottom = height;
    }
}
