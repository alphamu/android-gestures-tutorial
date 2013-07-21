package com.alimuzaffar.tutorial.gestures01;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private GestureDetector mGestureDetector;
	private View.OnTouchListener mViewTouchListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		View gestureView = findViewById(R.id.gesture_view);
		EditText gestureName = (EditText) findViewById(R.id.gesture_name);

		//NOTE: If you pass an activity to the gesture detector.
		//and you support orientation changes. You need to 
		//destroy the gesture detector and recreate it.
		CustomOnGestureListener gestureListener = new CustomOnGestureListener(gestureName);
		mGestureDetector = new GestureDetector(this, gestureListener);
		mGestureDetector.setOnDoubleTapListener(gestureListener);
		
		mViewTouchListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (mGestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		};
		
		
		gestureView.setOnTouchListener(mViewTouchListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
