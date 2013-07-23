package com.alimuzaffar.tutorial.gestures03;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.FloatMath;
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

	public static final long DOUBLE_TAP_TIMEOUT = ViewConfiguration
			.getDoubleTapTimeout();
	public static float mViewScaledTouchSlop;
	EditText mGestureName;

	private boolean mStillDown;

	private MotionEvent mCurrentDownEvent;
	private MotionEvent mPreviousUpEvent;

	private int mPtrCount = 0;

	private float mPrimStartTouchEventX = 0;
	private float mPrimStartTouchEventY = 0;
	private float mSecStartTouchEventX = 0;
	private float mSecStartTouchEventY = 0;
	private float mPrimSecStartTouchDistance = 0;

	long downTimestamp = System.currentTimeMillis();

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
				// prepend("Double Tap "+mCurrentDownEvent.getPointerCount());
				onDoubleTapConfirmed(mCurrentDownEvent);
				break;
			default:
				throw new RuntimeException("Unknown message " + msg); // never
			}
		}
	}

	GestureHandler mHandler;

	public CustomOnGestureListener(Context context, TextView gestureName) {
		mGestureName = (EditText) gestureName;
		mHandler = new GestureHandler();

		final ViewConfiguration viewConfig = ViewConfiguration.get(context);
		mViewScaledTouchSlop = viewConfig.getScaledTouchSlop();
	}

	public boolean onTouchEvent(MotionEvent ev) {
		int action = (ev.getAction() & MotionEvent.ACTION_MASK);

		// prepend("onTouchEvent() ptrs:" + ev.getPointerCount() + " "
		// + actionToString(action));
		switch (action) {
		case MotionEvent.ACTION_POINTER_DOWN:
			mPtrCount++;
			if (ev.getPointerCount() > 1) {
				mSecStartTouchEventX = ev.getX(1);
				mSecStartTouchEventY = ev.getY(1);
				mPrimSecStartTouchDistance = distance(ev, 0, 1);

				if (mCurrentDownEvent != null)
					mCurrentDownEvent.recycle();
				mCurrentDownEvent = MotionEvent.obtain(ev);

				if (System.currentTimeMillis() - downTimestamp > 50) {

					if (!mHandler.hasMessages(TAP)) {
						mHandler.sendEmptyMessageDelayed(TAP,
								DOUBLE_TAP_TIMEOUT);
					} else {
						mHandler.removeMessages(TAP);
						mHandler.sendEmptyMessageDelayed(DOUBLE_TAP,
								DOUBLE_TAP_TIMEOUT);
					}

				}

				downTimestamp = System.currentTimeMillis();

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
		// prepend("onDoubleTap() ptrs:" + e.getPointerCount());
		if (mCurrentDownEvent.getPointerCount() == 1) {
			prepend("onDoubleTap() ptrs:" + mCurrentDownEvent.getPointerCount());
			mHandler.sendEmptyMessageDelayed(DOUBLE_TAP, DOUBLE_TAP_TIMEOUT);
		}
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// prepend("onDoubleTapEvent() ptrs:" + e.getPointerCount());

		return true;
	}

	/**
	 * We don't want to ignore the old double tap method. Or in onDoubleTap
	 * ignore MotionEvent and use our mCurrentDownEvent and don't call double
	 * tap in the handler
	 * 
	 * @param e
	 * @return
	 */
	public boolean onDoubleTapConfirmed(MotionEvent e) {
		prepend("onDoubleTapConfirmed() ptrs:" + e.getPointerCount());

		if (mPtrCount == 1) {
			prepend("onDoubleTapConfirmed(): tap and a half");
		}
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		if (!mHandler.hasMessages(TAP)
				&& mCurrentDownEvent.getPointerCount() == e.getPointerCount())
			prepend("onSingleTapConfirmed() ptrs:" + e.getPointerCount());

		if (mPtrCount == 1 && mCurrentDownEvent.getPointerCount() == 2) {
			// one finger is still down and a single tap occured
			prepend("onSingleTapConfirmed(): One finger down, one finger tap");
		}
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// prepend("onDown() ptrs:" + e.getPointerCount());
		if (mCurrentDownEvent != null)
			mCurrentDownEvent.recycle();
		mCurrentDownEvent = MotionEvent.obtain(e);

		mPrimStartTouchEventX = e.getX();
		mPrimStartTouchEventY = e.getY();

		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// prepend("onFling() ptrs:e1:" + e1.getPointerCount() + " e2:"
		// + e2.getPointerCount() +
		// " last: "+mCurrentDownEvent.getPointerCount());
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// prepend("onLongPress() ptrs:" + e.getPointerCount());

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// e1 The first down motion event that started the scrolling.
		// e2 The move motion event that triggered the current onScroll.
		prepend("onScroll() ptrs:e1:" + e1.getPointerCount() + " e2:"
				+ e2.getPointerCount() + " ptrCnt: " + mPtrCount);

		boolean scroll = isScrollGesture(e2);
		boolean pinch = isPinchGesture(e2);

		prepend("onScroll() scroll = " + scroll + " pinch = " + pinch);

		if (scroll || pinch)
			cancelAll();

		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// prepend("onShowPress() ptrs:" + e.getPointerCount());

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// prepend("onSingleTapUp() ptrs:" + e.getPointerCount());
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

	private boolean isPinchGesture(MotionEvent event) {
		if (event.getPointerCount() == 2) {
			final float distanceCurrent = distance(event, 0, 1);
			final float diffPrimX = mPrimStartTouchEventX - event.getX(0);
			final float diffPrimY = mPrimStartTouchEventY - event.getY(0);
			final float diffSecX = mSecStartTouchEventX - event.getX(1);
			final float diffSecY = mSecStartTouchEventY - event.getY(1);

			if (// if the distance between the two fingers has increased past
				// our threshold
			Math.abs(distanceCurrent - mPrimSecStartTouchDistance) > mViewScaledTouchSlop
					// and the fingers are moving in opposing directions
					&& (diffPrimY * diffSecY) <= 0
					&& (diffPrimX * diffSecX) <= 0) {
				// mPinchClamp = false; // don't clamp initially
				return true;
			}
		}

		return false;
	}

	public float distance(MotionEvent event, int first, int second) {
		if (event.getPointerCount() >= 2) {
			final float x = event.getX(first) - event.getX(second);
			final float y = event.getY(first) - event.getY(second);

			return (float) Math.sqrt(x * x + y * y);
		} else {
			return 0;
		}
	}

	private boolean isScrollGesture(MotionEvent event) {
		if (event.getPointerCount() == 2) {
			final float diffPrim = mPrimStartTouchEventY - event.getY(0);
			final float diffSec = mSecStartTouchEventY - event.getY(1);

			if (// make sure both fingers are moving in the same direction
			diffPrim * diffSec > 0
					// make sure both fingers have moved past the scrolling
					// threshold
					&& Math.abs(diffPrim) > mViewScaledTouchSlop
					&& Math.abs(diffSec) > mViewScaledTouchSlop) {
				return true;
			}
		} else if (event.getPointerCount() == 1) {
			final float diffPrim = mPrimStartTouchEventY - event.getY(0);
			if (// make sure finger has moved past the scrolling threshold
			Math.abs(diffPrim) > mViewScaledTouchSlop) {
				return true;
			}
		}

		return false;
	}

}
