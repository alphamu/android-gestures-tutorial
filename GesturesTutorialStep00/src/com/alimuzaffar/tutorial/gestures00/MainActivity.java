package com.alimuzaffar.tutorial.gestures00;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.EditText;

public class MainActivity extends Activity implements OnGestureListener,
		OnDoubleTapListener {

	private GestureDetector mGestureDetector;
	private EditText mGestureName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mGestureName = (EditText) findViewById(R.id.gesture_name);

		mGestureDetector = new GestureDetector(this, this);
		mGestureDetector.setOnDoubleTapListener(this);

	}

	//need to call our gesture detector
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.mGestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		prepend("onDoubleTap()");

		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		prepend("onDoubleTapEvent()");

		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		prepend("onSingleTapConfirmed()");

		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		prepend("onDown()");
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		prepend("onFling()");
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		prepend("onLongPress()");

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		prepend("onScroll()");
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		prepend("onShowPress()");

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		prepend("onSingleTapUp()");
		return true;
	}

	private void prepend(String method) {
		StringBuilder s = new StringBuilder(mGestureName.getText());

		s.insert(0, '\n');
		s.insert(0, method);
		// s.insert(0, '.');
		// s.insert(0, TAG);
		mGestureName.setText(s);
	}

}
