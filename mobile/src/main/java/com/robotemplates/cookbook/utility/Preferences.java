package com.robotemplates.cookbook.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.robotemplates.cookbook.CookbookApplication;
import com.robotemplates.cookbook.R;

public class Preferences {
	private Context mContext;
	private SharedPreferences mSharedPreferences;

	public Preferences() {
		mContext = CookbookApplication.getContext();
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
	}

	public void clearPreferences() {
		mSharedPreferences.edit().clear().apply();
	}

	public int getInAppReviewDialogCounter() {
		String key = mContext.getString(R.string.prefs_key_inapp_review_dialog_counter);
		return mSharedPreferences.getInt(key, 0);
	}

	public void setInAppReviewDialogCounter(int inAppReviewDialogCounter) {
		String key = mContext.getString(R.string.prefs_key_inapp_review_dialog_counter);
		mSharedPreferences.edit().putInt(key, inAppReviewDialogCounter).apply();
	}
}
