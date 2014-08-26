package com.lovewuchin.app.archertouch.utils;

import com.lovewuchin.app.archertouch.R;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class LockScreen extends DeviceAdminReceiver{
	
	public static class Controller extends Activity{
		
		private DevicePolicyManager mDevicePolicyManager;
		private ComponentName mDeviceAdminName;
		private boolean active;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
			mDeviceAdminName = new ComponentName(this, LockScreen.class);
			
			active = mDevicePolicyManager.isAdminActive(mDeviceAdminName);
			
			if(!active) {
				getAdmin();
			}else {
				mDevicePolicyManager.lockNow();
			}
			android.os.Process.killProcess(android.os.Process.myPid());
		}

		private void getAdmin() {
			// TODO Auto-generated method stub
			Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminName);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.text_onekey_lock));
			startActivity(intent);
		}
	}
}
