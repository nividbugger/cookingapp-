package com.robotemplates.cookbook.content;

import android.content.SearchRecentSuggestionsProvider;

import com.robotemplates.cookbook.CookbookApplication;

public class RecipeSearchRecentSuggestionsProvider extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = CookbookApplication.getContext().getPackageName() + ".content.RecipeSearchRecentSuggestionsProvider";
	public final static int MODE = DATABASE_MODE_QUERIES;

	public RecipeSearchRecentSuggestionsProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}
}
