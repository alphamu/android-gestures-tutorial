package com.alimuzaffar.tutorial.gestures03;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.TextView;

public class CustomOnGestureListener implements OnGestureListener,
		OnDoubleTapListener {

	public final String TAG = CustomOnGestureListener.class.getSimpleName();

	public static final int TAP = 0;
	public static final int DOUBLE_TAP = 1;

	public static final long DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();

	EditText mGestureName;

	private boolean mStillDown;

	private MotionEvent mCurrentDownEvent;
	private MotionEvent mPreviousUpEvent;

	private int mCurrentTapType;
	
	private int mPtrCount = 0;

	private class GestureHandler extends Handler {
		GestureHandler() {
			super();
		}

		GestureHandler(Handler handler) {
			super(handler.getLooper());
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TAP:
				// prepend("TAP Finger Count "+mCurrentDownEvent.getPointerCount());
				onSingleTapConfirmed(mCurrentDownEvent);
				break;
			case DOUBLE_TAP:
				//prepend("Double Tap "+mCurrentDownEvent.getPointerCount());
				onDoubleTap(mCurrentDownEvent);
				break;
			default:
				throw new RuntimeException("Unknown message " + msg); // never
			}
		}
	}

	GestureHandler mHandler;

	public CustomOnGestureListener(TextView gestureName) {
		mGestureName = (EditText) gestureName;
		mHandler = new GestureHandler();
	}

	public boolean onTouchEvent(MotionEvent ev) {
		int action = (ev.getAction() & MotionEvent.ACTION_MASK);

//		prepend("onTouchEvent() ptrs:" + ev.getPointerCount() + " "
//				+ actionToString(action));
		switch (action) {
		case MotionEvent.ACTION_POINTER_DOWN:
			mPtrCount++;
			if (ev.getPointerCount() > 1) {
				if(!mHandler.hasMessages(TAP))
					mHandler.sendEmptyMessageDelayed(TAP, DOUBLE_TAP_TIMEOUT);
				else {
					mHandler.removeMessages(TAP);
					mHandler.sendEmptyMessageDelayed(DOUBLE_TAP, DOUBLE_TAP_TIMEOUT);
				}
				if (mCurrentDownEvent != null)
					mCurrentDownEvent.recycle();
				mCurrentDownEvent = MotionEvent.obtain(ev);
				// return true to prevent other actions.
				return true;
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mPtrCount--;
			break;
		case MotionEvent.ACTION_DOWN:
			mPtrCount++;
			break;
		case MotionEvent.ACTION_UP:
			mPtrCount--;
			break;
			
		}

		return false;
	}

	// Given an action int, returns a string description
	public static String actionToString(int action) {
		switch (action) {

		case MotionEvent.ACTION_DOWN:
			return "*Down";
		case MotionEvent.ACTION_MOVE:
			return "*Move";
		case MotionEvent.ACTION_POINTER_DOWN:
			return "*Pointer Down";
		case MotionEvent.ACTION_UP:
			return "*Up";
		case MotionEvent.ACTION_POINTER_UP:
			return "*Pointer Up";
		case MotionEvent.ACTION_OUTSIDE:
			return "*Outside";
		case MotionEvent.ACTION_CANCEL:
			return "*Cancel";
		}
		return "";
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		prepend("onDoubleTap() ptrs:" + e.getPointerCount());

		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		//prepend("onDoubleTapEvent() ptrs:" + e.getPointerCount());

		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		if(!mHandler.hasMessages(TAP))
			prepend("onSingleTapConfirmed() ptrs:" + e.getPointerCount());
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		//prepend("onDown() ptrs:" + e.getPointerCount());
		if (mCurrentDownEvent != null)
			mCurrentDownEvent.recycle();
		mCurrentDownEvent = MotionEvent.obtain(e);
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
//		prepend("onFling() ptrs:e1:" + e1.getPointerCount() + " e2:"
//				+ e2.getPointerCount() + " last: "+mCurrentDownEvent.getPointerCount());
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
//		prepend("onLongPress() ptrs:" + e.getPointerCount());

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// e1 The first down motion event that started the scrolling.
		// e2 The move motion event that triggered the current onScroll.
		prepend("onScroll() ptrs:e1:" + e1.getPointerCount() + " e2:"
				+ e2.getPointerCount() + " ptrCnt: "+mPtrCount);
		
		cancelAll();
		
		
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
//		prepend("onShowPress() ptrs:" + e.getPointerCount());

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
//		prepend("onSingleTapUp() ptrs:" + e.getPointerCount());
		return true;
	}

	private void prepend(String method) {
		StringBuilder s = new StringBuilder(mGestureName.getText());

		s.insert(0, '\n');
		s.insert(0, method);
		mGestureName.setText(s);
	}

	private void cancelAll() {
		if (mHandler.hasMessages(TAP))
			mHandler.removeMessages(TAP);
	}
}
