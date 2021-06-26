package com.robotemplates.cookbook.utility;

import android.app.Activity;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.robotemplates.cookbook.CookbookConfig;

import org.alfonz.utility.Logcat;

public final class RatingUtility {
	private RatingUtility() {}

	public static boolean checkInAppReviewDialog(Activity activity) {
		// check if in-app review dialog is disabled
		if (CookbookConfig.INAPP_REVIEW_DIALOG_FREQUENCY == 0) return false;

		// get counter
		final Preferences preferences = new Preferences();
		final int counter = preferences.getInAppReviewDialogCounter();
		Logcat.d("" + counter);

		// check counter
		boolean showDialog = false;
		if (counter != -1) {
			if (counter >= CookbookConfig.INAPP_REVIEW_DIALOG_FREQUENCY && counter % CookbookConfig.INAPP_REVIEW_DIALOG_FREQUENCY == 0) {
				showDialog = true;
			}
		} else {
			return false;
		}

		// show in-app review dialog
		if (showDialog) {
			showInAppReviewDialog(activity);
		}

		// increment counter
		preferences.setInAppReviewDialogCounter(counter + 1);
		return showDialog;
	}

	private static void showInAppReviewDialog(Activity activity) {
		ReviewManager reviewManager = ReviewManagerFactory.create(activity);
		//ReviewManager reviewManager = new FakeReviewManager(activity);
		Task<ReviewInfo> request = reviewManager.requestReviewFlow();
		request.addOnCompleteListener(requestTask -> {
			if (requestTask.isSuccessful()) {
				Logcat.d("success");
				ReviewInfo reviewInfo = requestTask.getResult();
				Task<Void> launch = reviewManager.launchReviewFlow(activity, reviewInfo);
				launch.addOnCompleteListener(launchTask -> {
					Logcat.d("launch");
				});
			} else {
				Logcat.d("error " + requestTask.getException().getMessage());
			}
		});
	}
}
