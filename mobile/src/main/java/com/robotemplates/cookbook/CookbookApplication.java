package com.robotemplates.cookbook;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.robotemplates.cookbook.ads.AdMobUtility;
import com.robotemplates.kozuza.BaseApplication;
import com.robotemplates.kozuza.Kozuza;

import org.alfonz.utility.Logcat;

public class CookbookApplication extends BaseApplication {
	@Override
	public void onCreate() {
		super.onCreate();

		// init logcat
		Logcat.init(CookbookConfig.LOGS, "COOKBOOK");

		// init analytics
		FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(!CookbookConfig.DEV_ENVIRONMENT);

		// init AdMob
		MobileAds.initialize(this);
		MobileAds.setRequestConfiguration(AdMobUtility.createRequestConfiguration());
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	@Override
	public String getPurchaseCode() {
		return CookbookConfig.PURCHASE_CODE;
	}

	@Override
	public String getProduct() {
		return Kozuza.PRODUCT_COOKBOOK;
	}
}
