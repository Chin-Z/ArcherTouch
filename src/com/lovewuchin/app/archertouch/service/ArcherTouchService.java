package com.lovewuchin.app.archertouch.service;

import com.lovewuchin.app.archertouch.ArcherTouchApplication;
import com.lovewuchin.app.archertouch.R;
import com.lovewuchin.app.archertouch.utils.LockScreen;
import com.lovewuchin.app.archertouch.utils.LockScreen.Controller;
import com.lovewuchin.app.archertouch.view.FloatingHaloView;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class ArcherTouchService extends Service implements OnClickListener{

    private WindowManager.LayoutParams mParams;  
    private WindowManager mWindowManager; 
    private FloatingHaloView mFloatingHaloView;
    
    @Override
    public void onCreate() {
    	// TODO Auto-generated method stub
    	super.onCreate();
    	initView();
    	createView();
    }
    
	private void createView() {
		// TODO Auto-generated method stub
        mWindowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        mParams = ((ArcherTouchApplication) getBaseContext().getApplicationContext()).getWindowParams();
        mParams.type = LayoutParams.TYPE_PHONE;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.flags = LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mParams.gravity = Gravity.LEFT | Gravity.TOP; 
        mParams.x = 0;
        mParams.y = 0;
		mParams.width = LayoutParams.WRAP_CONTENT;
		mParams.height = LayoutParams.WRAP_CONTENT;
		
		mWindowManager.addView(mFloatingHaloView, mParams);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mFloatingHaloView = new FloatingHaloView(getApplicationContext());
		mFloatingHaloView.setOnClickListener(this);
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		removeView();
	}

	private void removeView() {
		// TODO Auto-generated method stub
		mWindowManager.removeView(mFloatingHaloView);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, Controller.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
}
