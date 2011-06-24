package android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

public class PivotLayout extends LinearLayout {
	public PivotLayout(Context context) {
		this(context, null);
	}

	public PivotLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	// TODO: move the duration into an XML configurable value
	// See the GradientTextView to see how to do that using android:duration.
	int mAnimationDuration;
	public void setAnimationDuration(int duration) {
		mAnimationDuration = duration;
	}
	
	public int getDuration() {
		return mAnimationDuration;
	}
	
	PivotListener mListener;
	public PivotListener getPivotListener() {
		return mListener;
	}
	
	public void setPivotListener(PivotListener listener) {
		mListener = listener;
	}
	
	public void move(View v1, View v2, View fade, float fromAlpha, float toAlpha) {
		clearAnimation();
		AnimationSet anim = new AnimationSet(false);
		anim.setFillBefore(true);
		anim.setFillAfter(true);
		anim.setFillEnabled(true);

		// TODO: Use something nonlinear for the translate? I believe animations support different interpolators.
		TranslateAnimation trans = new TranslateAnimation(-v1.getLeft(), -v2.getLeft(), -v1.getTop(), -v2.getTop());
		trans.setDuration(mAnimationDuration);
		trans.setFillBefore(true);
		trans.setFillAfter(true);
		trans.setFillEnabled(true);
		anim.addAnimation(trans);

		AlphaAnimation alpha = new AlphaAnimation(fromAlpha, toAlpha);
		alpha.setDuration(mAnimationDuration);
		alpha.setFillBefore(true);
		alpha.setFillAfter(true);
		alpha.setFillEnabled(true);
		fade.startAnimation(alpha);
		
		startAnimation(anim);
	}
	
	public void movePrevious() {
		if (mCurrent <= 0)
			return;
		
		View v1 = getChildAt(mCurrent);
		View v2 = getChildAt(mCurrent - 1);
		
		move(v1, v2, v2, 0, 1);
		if (mListener != null)
			mListener.onViewEnter(v2);
		
		mCurrent--;
	}

	int mCurrent = 0;
	public void moveNext() {
		if (mCurrent + 1 >= getChildCount())
			return;
		
		View v1 = getChildAt(mCurrent);
		View v2 = getChildAt(mCurrent + 1);
		
		move(v1, v2, v1, 1, 0);
		if (mListener != null)
			mListener.onViewExit(v1);
		
		mCurrent++;
	}
	
	public int getCurrentPivot() {
		return mCurrent;
	}
	
	public int getCurrentPivotId() {
		return getChildAt(mCurrent).getId();
	}
	
	public View getCurrentPivotView() {
		return getChildAt(mCurrent);
	}
}
