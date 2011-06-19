package android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.text.BoringLayout;
import android.util.AttributeSet;
import android.widget.TextView;

public class GradientTextView extends TextView {
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
        int[] ids = new int[attrs.getAttributeCount()];
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            ids[i] = attrs.getAttributeNameResource(i);
        }
        
        TypedArray a = context.obtainStyledAttributes(attrs, ids, defStyle, 0);

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            if (attrName == null)
                continue;
            
            if (attrName.equals("startColor")) {
                mStartColor = a.getColor(i, -1);
            }
            else if (attrName.equals("endColor")) {
                mEndColor = a.getColor(i, -1);
            }
            else if (attrName.equals("angle")) {
                mAngle = a.getFloat(i, 0);
            }
        }
    }
    
    public static void setGradient(TextView tv, float angle, int startColor, int endColor) {
        tv.measure(tv.getLayoutParams().width, tv.getLayoutParams().height);
        LinearGradient gradient = getGradient(tv.getMeasuredWidth(), tv.getMeasuredHeight(), angle, startColor, endColor);
        tv.getPaint().setShader(gradient);
    }
    
    static LinearGradient getGradient(int measuredWidth, int measuredHeight, float angle, int startColor, int endColor) {
        // calculate a vector for this angle
        double rad = Math.toRadians(angle);
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
        int mx = measuredWidth;
        int my = measuredHeight;
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
        
        return new LinearGradient(sx, sy, ex, ey, startColor, endColor, TileMode.CLAMP);
    }
    
    protected void onDraw(android.graphics.Canvas canvas) {
        if (mGradient == null) {
            mGradient = getGradient(getMeasuredWidth(), getMeasuredHeight(), mAngle, mStartColor, mEndColor);
            getPaint().setShader(mGradient);
        }
        super.onDraw(canvas);
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


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mGradient = null;
    }
    LinearGradient mGradient;
}
