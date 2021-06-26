package com.robotemplates.cookbook;

public class CookbookConfig {
	// Envato purchase code
	public static final String PURCHASE_CODE = "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX";

	// frequency of showing in-app review dialog,
	// review dialog will be shown after each x openings of recipe detail,
	// set 0 if you do not want to show in-app review dialog
	public static final int INAPP_REVIEW_DIALOG_FREQUENCY = 30;

	// frequency of showing AdMob interstitial ad,
	// ad will be shown after each x openings of recipe detail
	public static final int ADMOB_INTERSTITIAL_FREQUENCY = 5;

	// true for showing GDPR consent form
	public static final boolean GDPR = true;

	// URL link to your privacy policy,
	// it is also required for GDPR consent form
	public static final String PRIVACY_POLICY_URL = "https://link.to/privacy-policy.html";

	// file name of the SQLite database, this file should be placed in assets folder
	public static final String DATABASE_NAME = "cookbook.db";

	// database version, should be incremented if database has been changed
	public static final int DATABASE_VERSION = 1;

	// debug logs, value is set via build config in build.gradle
	public static final boolean LOGS = BuildConfig.LOGS;

	// development environment, value is set via build config in build.gradle
	public static final boolean DEV_ENVIRONMENT = BuildConfig.DEV_ENVIRONMENT;

	// AdMob test ads, value is set via build config in build.gradle
	public static final boolean TEST_ADS = BuildConfig.TEST_ADS;
}
