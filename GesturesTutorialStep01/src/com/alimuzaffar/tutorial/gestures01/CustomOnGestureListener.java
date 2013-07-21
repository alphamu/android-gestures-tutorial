package com.alimuzaffar.tutorial.gestures01;

import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

public class CustomOnGestureListener implements OnGestureListener,
		OnDoubleTapListener {

	public final String TAG = CustomOnGestureListener.class.getSimpleName();
	
	EditText mGestureName;
	
	public CustomOnGestureListener(TextView gestureName) {
		mGestureName = (EditText) gestureName;
	}
	
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		prepend("onDoubleTap() ptrs:" + e.getPointerCount());

		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		prepend("onDoubleTapEvent() ptrs:" + e.getPointerCount());

		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		prepend("onSingleTapConfirmed() ptrs:" + e.getPointerCount());

		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		prepend("onDown() ptrs:" + e.getPointerCount());
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		prepend("onFling() ptrs:e1:" + e1.getPointerCount() + " e2:" + e2.getPointerCount());
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		prepend("onLongPress() ptrs:" + e.getPointerCount());

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		//e1 The first down motion event that started the scrolling.
		//e2 The move motion event that triggered the current onScroll.
		prepend("onScroll() ptrs:e1:" + e1.getPointerCount() + " e2:" + e2.getPointerCount());
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		prepend("onShowPress() ptrs:" + e.getPointerCount());

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		prepend("onSingleTapUp() ptrs:" + e.getPointerCount());
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
