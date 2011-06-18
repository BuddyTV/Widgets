package com.koushikdutta.test;

import com.koushikdutta.widgets.GradientTextView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WidgetsSampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView secondText = (TextView)findViewById(R.id.secondtext);
        GradientTextView.setGradient(secondText, 0, 0xFF000000, 0xFFFFFFFF);
    }
}