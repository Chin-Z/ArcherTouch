package com.lovewuchin.app.archertouch.settings;

import com.lovewuchin.app.archertouch.ArcherTouchApplication;
import com.lovewuchin.app.archertouch.Constants;
import com.lovewuchin.app.archertouch.R;
import com.lovewuchin.app.archertouch.service.ArcherTouchService;
import com.lovewuchin.app.archertouch.view.FloatingHaloView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;

public class MainSettings extends PreferenceActivity implements OnPreferenceChangeListener{

	private WindowManager mWindowManager = null;
	private WindowManager.LayoutParams mParams = null;
	
	private CheckBoxPreference mOpenPreference;
	private SharedPreferences mPrefs;
	private Intent service;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
		//initialization
		mWindowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		mParams = ((ArcherTouchApplication) getApplication()).getWindowParams();
		service = new Intent(this, ArcherTouchService.class);
		
		mPrefs = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_WORLD_READABLE);
		mOpenPreference = (CheckBoxPreference) findPreference("key_open_halo");
		mOpenPreference.setDefaultValue(mPrefs.getBoolean("key_open_halo", false));
		mOpenPreference.setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		String key = preference.getKey();
		if(key.equals("key_open_halo")) {
			mPrefs.edit().putBoolean("key_open_halo", (Boolean) newValue).commit();
			if((Boolean) newValue) {
				startService(service);
			}else if(service != null){	
				stopService(service);
			}
			return true;
		}
		return false;
	}
}
