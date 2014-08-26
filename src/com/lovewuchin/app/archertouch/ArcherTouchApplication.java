package com.lovewuchin.app.archertouch;

import android.app.Application;
import android.view.WindowManager;

public class ArcherTouchApplication extends Application{
	
	private static ArcherTouchApplication instance = new ArcherTouchApplication();
	private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

	public WindowManager.LayoutParams getWindowParams() {
		return windowParams;
	}
	
	public static ArcherTouchApplication getInstance() {
		return instance;
	}

}
