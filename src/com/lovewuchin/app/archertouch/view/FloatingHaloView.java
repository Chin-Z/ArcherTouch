package com.lovewuchin.app.archertouch.view;

import com.lovewuchin.app.archertouch.ArcherTouchApplication;
import com.lovewuchin.app.archertouch.Constants;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

public class FloatingHaloView extends ImageView{

	private float mTouchX, mTouchY;
	private float X, Y;
	private boolean isMove, isRight;
	private WindowManager mWindowManager = null;
	private LayoutParams mParams = null;
	private OnClickListener mClickListener = null;
	private OnLongClickListener mLongClickListener = null;
	
	public FloatingHaloView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		isMove = false;
		isRight = false;
		mWindowManager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		mParams = ((ArcherTouchApplication) getContext().getApplicationContext()).getWindowParams();
		setImageResource(Constants.defaultResource);
		//window layoutparams inits
		mParams.type = LayoutParams.TYPE_PHONE; 
		mParams.format = PixelFormat.RGBA_8888;
		mParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
		mParams.gravity = Gravity.LEFT | Gravity.TOP;
		mParams.x = 0;
		mParams.y = 0;
		mParams.width = LayoutParams.WRAP_CONTENT;
		mParams.height = LayoutParams.WRAP_CONTENT;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Rect frame = new Rect();
		getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		X = event.getRawX();
		Y = event.getRawY() - statusBarHeight;
		int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
		
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mTouchX = event.getX();
			mTouchY = event.getY();
			isMove = false;
			if(isRight) {
				setImageResource(Constants.focusRightResource);
			}else {
				setImageResource(Constants.focusLeftResource);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(X > 60 && (screenWidth - X) > 60) {
				isMove = true;
				setImageResource(Constants.defaultResource);
				updateViewPosition();
			}
			break;
		case MotionEvent.ACTION_UP:
			if(isMove) {
				isMove = false;
				float halfScreen = screenWidth/2;
				if(X <= halfScreen) {
					setImageResource(Constants.leftResource);
					X = 0 ;
					isRight = false;
				} else {
					setImageResource(Constants.rightResource);
					X = screenWidth;
					isRight = true;
				}
				updateViewPosition();
			} else {
				setImageResource(Constants.leftResource);
				if(mClickListener!=null) {
					mClickListener.onClick(this);
				}else if(mLongClickListener!=null) {
					mLongClickListener.onLongClick(this);
				}
			}
			mTouchX = mTouchY = 0;
			break;
		}
		return true;
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		// TODO Auto-generated method stub
		this.mClickListener = l;
	}
	
	@Override
	public void setOnLongClickListener(OnLongClickListener l) {
		// TODO Auto-generated method stub
		this.mLongClickListener = l;
	}
	private void updateViewPosition() {
		// TODO Auto-generated method stub
		mParams.x = (int) (X - mTouchX);
		mParams.y = (int) (Y - mTouchY);
		mWindowManager.updateViewLayout(this, mParams);
	}

}
