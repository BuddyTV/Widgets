package com.koushikdutta.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.PivotLayout;

public class PivotActivity extends Activity {
	private static final int PIVOT_ANIMATION_DURATION = 1000;
	
	PivotLayout mPivot1;
	PivotLayout mPivot2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pivot);
		
		mPivot1 = (PivotLayout)findViewById(R.id.pivot1);
		mPivot1.setAnimationDuration(PIVOT_ANIMATION_DURATION);
		
		mPivot2 = (PivotLayout)findViewById(R.id.pivot2);
		mPivot2.setAnimationDuration(PIVOT_ANIMATION_DURATION);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem previous = menu.add("Previous");
		
		previous.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				mPivot1.movePrevious();
				mPivot2.movePrevious();
				return true;
			}
		});
		
		MenuItem next = menu.add("Next");
		next.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				mPivot1.moveNext();
				mPivot2.moveNext();
				return true;
			}
		});
		
		return super.onCreateOptionsMenu(menu);
	}
}
