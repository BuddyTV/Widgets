package com.koushikdutta.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.text.BoringLayout;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class GradientTextView extends View {
    public GradientTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    int mStartColor = 0;
    int mEndColor = 0;
    float mAngle;
    String mText;
    BoringLayout mLayout;

    public GradientTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        
        int[] ids = new int[attrs.getAttributeCount()];
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            ids[i] = attrs.getAttributeNameResource(i);
        }
        
        TypedArray a = context.obtainStyledAttributes(attrs, ids, defStyle, 0);

        
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);

            /*
            try {
                int resId = mRAttr.getField(attrName).getInt(null);
                System.out.println(attrName);
                System.out.println(resId);
                System.out.println(attrs.getAttributeNameResource(i));
                System.out.println(attrs.getAttributeIntValue(i, -10000));
                System.out.println(attrs.getAttributeResourceValue(i, -10000));
                System.out.println(attrs.getAttributeValue(i));
                System.out.println("Color: " + a.getColor(i, 0));
                System.out.println("Color: " + a.getColor(resId, 0));
            }
            catch (Exception ex) {
            }
            */
            
            
            if (attrName.equals("textSize")) {
                mPaint.setTextSize(a.getDimension(i, -1));
            }
            else if (attrName.equals("text")) {
                mText = a.getText(i).toString();
            }
            else if (attrName.equals("startColor")) {
                mStartColor = a.getColor(i, -1);
            }
            else if (attrName.equals("endColor")) {
                mEndColor = a.getColor(i, -1);
            }
            else if (attrName.equals("angle")) {
                mAngle = a.getFloat(i, 0);
            }

            /*
            switch (resId) {
            case R.styleable.GradientTextView_gradientColorBottomRight:
                TypedValue a;
                TypedArray f;
                mBR = a.getColor(resId, 0);
                break;
            case R.styleable.GradientTextView_gradientColorTopLeft:
                mTL = a.getColor(resId, 0);
                break;
            case R.styleable.GradientTextView_textSize:
                mPaint.setTextSize(a.getDimension(i, 0));
                break;
            case R.styleable.GradientTextView_text:
                mText = a.getString(resId);
            }
            */
        }
    }
    
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        if (mLayout == null)
            return;
        if (mGradient == null) {
            // calculate a vector for this angle
            double rad = Math.toRadians(mAngle);
            double oa = Math.tan(rad);
            double x;
            double y;
            if (oa == Double.POSITIVE_INFINITY) {
                y = 1;
                x = 0;
            }
            else if (oa == Double.NEGATIVE_INFINITY) {
                y = -1;
                x = 0;
            }
            else {
                y = oa;
                if (rad > Math.PI)
                    x = -1;
                else
                    x = 1;
            }
            
            // using the vector, calculate the start and end points from the center of the box
            int mx = getMeasuredWidth();
            int my = getMeasuredHeight();
            int cx = mx / 2;
            int cy = my / 2;
            
            double n;
            if (x == 0) {
                n = (double)cy / y;  
            }
            else if (y == 0) {
                n = (double)cx / x;
            }
            else {
                n = (double)cy / y;
                double n2 = (double)cx / x;
                if (Math.abs(n2) < Math.abs(n))
                    n = n2;
            }
            
            int sx = (int)(cx - n * x);
            int sy = (int)(cy - n * y);
            int ex = (int)(cx + n * x);
            int ey = (int)(cy + n * y);
            
            mGradient = new LinearGradient(sx, sy, ex, ey, mStartColor, mEndColor, TileMode.CLAMP);
            mPaint.setShader(mGradient);
        }
        mLayout.draw(canvas);
    }
    
    public int getStartColor() {
        return mStartColor;
    }
    
    public void setStartColor(int startColor) {
        mStartColor = startColor;
        invalidate();
    }
    
    public int getEndColor() {
        return mEndColor;
    }
    
    public void setEndColor(int endColor) {
        mEndColor = endColor;
        invalidate();
    }
    
    public void setText(String text) {
        mText = text;
    }
    
    public String getText() {
        return mText;
    }
    
    public float getTextSize() {
        return mPaint.getTextSize();
    }
    
    public void setTextSize(float textSize) {
        mPaint.setTextSize(textSize);
    }
    
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mText == null) {
            setMeasuredDimension(0, 0);
            return;
        }
        float w = mPaint.measureText(mText);
        float h = mPaint.getFontSpacing();
        setMeasuredDimension((int)w, (int)h);
        mLayout = new BoringLayout(mText, mPaint, 0, Alignment.ALIGN_NORMAL, 0, 0, BoringLayout.isBoring(mText, mPaint), false);
        mGradient = null;
    }

    TextPaint mPaint;
    LinearGradient mGradient;
}
