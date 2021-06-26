package com.robotemplates.cookbook.fragment;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robotemplates.cookbook.CookbookApplication;
import com.robotemplates.cookbook.CookbookConfig;
import com.robotemplates.cookbook.R;
import com.robotemplates.cookbook.activity.RecipeDetailActivity;
import com.robotemplates.cookbook.adapter.RecipeListAdapter;
import com.robotemplates.cookbook.adapter.SearchSuggestionAdapter;
import com.robotemplates.cookbook.ads.AdMobInterstitialHelper;
import com.robotemplates.cookbook.ads.AdMobUtility;
import com.robotemplates.cookbook.content.RecipeSearchRecentSuggestionsProvider;
import com.robotemplates.cookbook.database.DatabaseCallListener;
import com.robotemplates.cookbook.database.DatabaseCallManager;
import com.robotemplates.cookbook.database.DatabaseCallTask;
import com.robotemplates.cookbook.database.data.Data;
import com.robotemplates.cookbook.database.model.RecipeModel;
import com.robotemplates.cookbook.database.query.Query;
import com.robotemplates.cookbook.database.query.RecipeReadAllQuery;
import com.robotemplates.cookbook.database.query.RecipeReadByCategoryQuery;
import com.robotemplates.cookbook.database.query.RecipeReadFavoritesQuery;
import com.robotemplates.cookbook.database.query.RecipeSearchQuery;
import com.robotemplates.cookbook.dialog.AboutDialogFragment;
import com.robotemplates.cookbook.listener.OnSearchListener;
import com.robotemplates.cookbook.utility.RatingUtility;
import com.robotemplates.cookbook.widget.GridSpacingItemDecoration;

import org.alfonz.utility.Logcat;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import java.util.ArrayList;
import java.util.List;

public class RecipeListFragment extends TaskFragment implements DatabaseCallListener, RecipeListAdapter.RecipeViewHolder.OnItemClickListener {
	public static final long CATEGORY_ID_ALL = -1L;
	public static final long CATEGORY_ID_FAVORITES = -2L;
	public static final long CATEGORY_ID_SEARCH = -3L;

	private static final String ARGUMENT_CATEGORY_ID = "category_id";
	private static final String ARGUMENT_SEARCH_QUERY = "search_query";
	private static final String DIALOG_ABOUT = "about";
	private static final int LAZY_LOADING_TAKE = 128;
	private static final int LAZY_LOADING_OFFSET = 4;

	private boolean mLazyLoading = false;
	private View mRootView;
	private StatefulLayout mStatefulLayout;
	private RecipeListAdapter mAdapter;
	private OnSearchListener mSearchListener;
	private ActionMode mActionMode;
	private DatabaseCallManager mDatabaseCallManager = new DatabaseCallManager();
	private long mCategoryId;
	private String mSearchQuery;
	private List<RecipeModel> mRecipeList = new ArrayList<>();
	private List<Object> mFooterList = new ArrayList<>();
	private AdMobInterstitialHelper mAdMobInterstitialHelper = new AdMobInterstitialHelper();

	public static RecipeListFragment newInstance(long categoryId) {
		RecipeListFragment fragment = new RecipeListFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putLong(ARGUMENT_CATEGORY_ID, categoryId);
		fragment.setArguments(arguments);

		return fragment;
	}

	public static RecipeListFragment newInstance(String searchQuery) {
		RecipeListFragment fragment = new RecipeListFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putLong(ARGUMENT_CATEGORY_ID, CATEGORY_ID_SEARCH);
		arguments.putString(ARGUMENT_SEARCH_QUERY, searchQuery);
		fragment.setArguments(arguments);

		return fragment;
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);

		// set search listener
		try {
			mSearchListener = (OnSearchListener) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().getClass().getName() + " must implement " + OnSearchListener.class.getName());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);

		// handle fragment arguments
		Bundle arguments = getArguments();
		if (arguments != null) {
			handleArguments(arguments);
		}

		// admob
		mAdMobInterstitialHelper.setupAd(getContext());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
		setupRecyclerView();
		return mRootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// setup stateful layout
		setupStatefulLayout(savedInstanceState);

		// load data
		if (mRecipeList == null || mRecipeList.isEmpty()) loadData();

		// lazy loading progress
		if (mLazyLoading) showLazyLoadingProgress(true);

		// show toolbar if hidden
		showToolbar(true);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();

		// stop adapter
		if (mAdapter != null) mAdapter.stop();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mRootView = null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// cancel async tasks
		mDatabaseCallManager.cancelAllTasks();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		// save current instance state
		super.onSaveInstanceState(outState);

		// stateful layout state
		if (mStatefulLayout != null) mStatefulLayout.saveInstanceState(outState);
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		// action bar menu
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_recipe_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// action bar menu behavior
		switch (item.getItemId()) {
			case R.id.menu_rate:
				startWebActivity(getString(R.string.app_store_uri, CookbookApplication.getContext().getPackageName()));
				return true;

			case R.id.menu_about:
				showAboutDialog();
				return true;

			case R.id.menu_privacy_policy:
				startWebActivity(CookbookConfig.PRIVACY_POLICY_URL);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onItemClick(View view, int position, long id, int viewType) {
		// position
		int recipePosition = mAdapter.getRecipePosition(position);

		// start activity
		RecipeModel recipe = mRecipeList.get(recipePosition);
		startRecipeDetailActivity(view, recipe.getId());

		// check in-app review dialog
		boolean showInAppReviewDialog = RatingUtility.checkInAppReviewDialog(getActivity());
		if (!showInAppReviewDialog) {
			// check interstitial ad
			mAdMobInterstitialHelper.checkAd(getActivity());
		}
	}

	@Override
	public void onDatabaseCallRespond(final DatabaseCallTask task, final Data<?> data) {
		runTaskCallback(() -> {
			if (mRootView == null) return; // view was destroyed

			if (task.getQuery().getClass().equals(RecipeReadAllQuery.class)) {
				Logcat.d("RecipeReadAllQuery");

				// get data
				Data<List<RecipeModel>> recipeReadAllData = (Data<List<RecipeModel>>) data;
				List<RecipeModel> recipeList = recipeReadAllData.getDataObject();
				mRecipeList.addAll(recipeList);
			} else if (task.getQuery().getClass().equals(RecipeReadFavoritesQuery.class)) {
				Logcat.d("RecipeReadFavoritesQuery");

				// get data
				Data<List<RecipeModel>> recipeReadFavoritesData = (Data<List<RecipeModel>>) data;
				List<RecipeModel> recipeList = recipeReadFavoritesData.getDataObject();
				mRecipeList.addAll(recipeList);
			} else if (task.getQuery().getClass().equals(RecipeSearchQuery.class)) {
				Logcat.d("RecipeSearchQuery");

				// get data
				Data<List<RecipeModel>> recipeSearchData = (Data<List<RecipeModel>>) data;
				List<RecipeModel> recipeList = recipeSearchData.getDataObject();
				mRecipeList.addAll(recipeList);
			} else if (task.getQuery().getClass().equals(RecipeReadByCategoryQuery.class)) {
				Logcat.d("RecipeReadByCategoryQuery");

				// get data
				Data<List<RecipeModel>> recipeReadByCategoryData = (Data<List<RecipeModel>>) data;
				List<RecipeModel> recipeList = recipeReadByCategoryData.getDataObject();
				mRecipeList.addAll(recipeList);
			}

			// show content
			if (mRecipeList != null && !mRecipeList.isEmpty()) mStatefulLayout.showContent();
			else mStatefulLayout.showEmpty();
			showLazyLoadingProgress(false);

			// finish query
			mDatabaseCallManager.finishTask(task);
		});
	}

	@Override
	public void onDatabaseCallFail(final DatabaseCallTask task, final Exception exception) {
		runTaskCallback(() -> {
			if (mRootView == null) return; // view was destroyed

			if (task.getQuery().getClass().equals(RecipeReadAllQuery.class)) {
				Logcat.d("RecipeReadAllQuery / exception " + exception.getClass().getSimpleName() + " / " + exception.getMessage());
			} else if (task.getQuery().getClass().equals(RecipeReadFavoritesQuery.class)) {
				Logcat.d("RecipeReadFavoritesQuery / exception " + exception.getClass().getSimpleName() + " / " + exception.getMessage());
			} else if (task.getQuery().getClass().equals(RecipeSearchQuery.class)) {
				Logcat.d("RecipeSearchQuery / exception " + exception.getClass().getSimpleName() + " / " + exception.getMessage());
			} else if (task.getQuery().getClass().equals(RecipeReadByCategoryQuery.class)) {
				Logcat.d("RecipeReadByCategoryQuery / exception " + exception.getClass().getSimpleName() + " / " + exception.getMessage());
			}

			// hide progress
			if (mRecipeList != null && !mRecipeList.isEmpty()) mStatefulLayout.showContent();
			else mStatefulLayout.showEmpty();
			showLazyLoadingProgress(false);

			// handle fail
			handleFail();

			// finish query
			mDatabaseCallManager.finishTask(task);
		});
	}

	private void handleFail() {
		Toast.makeText(getActivity(), R.string.global_database_fail_toast, Toast.LENGTH_LONG).show();
	}

	private void handleArguments(Bundle arguments) {
		mCategoryId = arguments.getLong(ARGUMENT_CATEGORY_ID, CATEGORY_ID_ALL);
		mSearchQuery = arguments.getString(ARGUMENT_SEARCH_QUERY, "");
	}

	private void loadData() {
		if (!mDatabaseCallManager.hasRunningTask(RecipeReadAllQuery.class) &&
				!mDatabaseCallManager.hasRunningTask(RecipeReadFavoritesQuery.class) &&
				!mDatabaseCallManager.hasRunningTask(RecipeSearchQuery.class) &&
				!mDatabaseCallManager.hasRunningTask(RecipeReadByCategoryQuery.class)) {
			// show progress
			mStatefulLayout.showProgress();

			// run async task
			Query query;
			if (mCategoryId == CATEGORY_ID_ALL) {
				query = new RecipeReadAllQuery(0, LAZY_LOADING_TAKE);
			} else if (mCategoryId == CATEGORY_ID_FAVORITES) {
				query = new RecipeReadFavoritesQuery(0, LAZY_LOADING_TAKE);
			} else if (mCategoryId == CATEGORY_ID_SEARCH) {
				query = new RecipeSearchQuery(mSearchQuery, 0, LAZY_LOADING_TAKE);
			} else {
				query = new RecipeReadByCategoryQuery(mCategoryId, 0, LAZY_LOADING_TAKE);
			}
			mDatabaseCallManager.executeTask(query, this);
		}
	}

	private void lazyLoadData() {
		// show lazy loading progress
		showLazyLoadingProgress(true);

		// run async task
		Query query;
		if (mCategoryId == CATEGORY_ID_ALL) {
			query = new RecipeReadAllQuery(mRecipeList.size(), LAZY_LOADING_TAKE);
		} else if (mCategoryId == CATEGORY_ID_FAVORITES) {
			query = new RecipeReadFavoritesQuery(mRecipeList.size(), LAZY_LOADING_TAKE);
		} else if (mCategoryId == CATEGORY_ID_SEARCH) {
			query = new RecipeSearchQuery(mSearchQuery, mRecipeList.size(), LAZY_LOADING_TAKE);
		} else {
			query = new RecipeReadByCategoryQuery(mCategoryId, mRecipeList.size(), LAZY_LOADING_TAKE);
		}
		mDatabaseCallManager.executeTask(query, this);
	}

	private void showLazyLoadingProgress(boolean visible) {
		if (visible) {
			mLazyLoading = true;

			// show footer
			if (mFooterList.size() <= 0) {
				mFooterList.add(new Object());
				getRecyclerView().post(() -> mAdapter.notifyItemInserted(mAdapter.getRecyclerPositionByFooter(0)));
			}
		} else {
			// hide footer
			if (mFooterList.size() > 0) {
				mFooterList.remove(0);
				getRecyclerView().post(() -> mAdapter.notifyItemRemoved(mAdapter.getRecyclerPositionByFooter(0)));
			}

			mLazyLoading = false;
		}
	}

	private void showToolbar(boolean visible) {
		final Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
		if (visible) {
			toolbar.animate()
					.translationY(0)
					.setDuration(200)
					.setInterpolator(new AccelerateDecelerateInterpolator())
					.setListener(new Animator.AnimatorListener() {
						@Override
						public void onAnimationStart(Animator animator) {
							toolbar.setVisibility(View.VISIBLE);
							toolbar.setEnabled(false);
						}

						@Override
						public void onAnimationEnd(Animator animator) {
							toolbar.setEnabled(true);
						}

						@Override
						public void onAnimationCancel(Animator animator) {
						}

						@Override
						public void onAnimationRepeat(Animator animator) {
						}
					});
		} else {
			toolbar.animate()
					.translationY(-toolbar.getBottom())
					.setDuration(200)
					.setInterpolator(new AccelerateDecelerateInterpolator())
					.setListener(new Animator.AnimatorListener() {
						@Override
						public void onAnimationStart(Animator animator) {
							toolbar.setEnabled(false);
						}

						@Override
						public void onAnimationEnd(Animator animator) {
							toolbar.setVisibility(View.GONE);
							toolbar.setEnabled(true);
						}

						@Override
						public void onAnimationCancel(Animator animator) {
						}

						@Override
						public void onAnimationRepeat(Animator animator) {
						}
					});
		}
	}

	private void showFloatingActionButton(boolean visible) {
		final FloatingActionButton fab = getActivity().findViewById(R.id.fab);
		if (visible) {
			fab.show();
		} else {
			fab.hide();
		}
	}

	private void setupView() {
		// reference
		final RecyclerView recyclerView = getRecyclerView();
		final FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);

		// content
		if (recyclerView.getAdapter() == null) {
			// create adapter
			mAdapter = new RecipeListAdapter(mRecipeList, mFooterList, this, getGridSpanCount());

			// add decoration
			RecyclerView.ItemDecoration itemDecoration = new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.recipe_list_recycler_item_padding));
			recyclerView.addItemDecoration(itemDecoration);
		} else {
			// refill adapter
			mAdapter.refill(mRecipeList, mFooterList, this, getGridSpanCount());
		}

		// set fixed size
		recyclerView.setHasFixedSize(false);

		// set animator
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		// set adapter
		recyclerView.setAdapter(mAdapter);

		// lazy loading
		recyclerView.clearOnScrollListeners();
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			private static final int THRESHOLD = 100;

			private int mCounter = 0;
			private Toolbar mToolbar = getActivity().findViewById(R.id.toolbar);

			@Override
			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);

				// reset counter
				if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
					mCounter = 0;
				}

				// disable item animation in adapter
				if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
					mAdapter.setAnimationEnabled(false);
				}
			}

			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);

				GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
				int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
				int visibleItemCount = layoutManager.getChildCount();
				int totalItemCount = layoutManager.getItemCount();
				int lastVisibleItem = firstVisibleItem + visibleItemCount;

				// lazy loading
				if (totalItemCount - lastVisibleItem <= LAZY_LOADING_OFFSET && mRecipeList.size() % LAZY_LOADING_TAKE == 0 && !mRecipeList.isEmpty()) {
					if (!mLazyLoading) lazyLoadData();
				}

				// toolbar and FAB animation
				mCounter += dy;
				if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING || recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {
					// scroll down
					if (mCounter > THRESHOLD && firstVisibleItem > 0) {
						// hide toolbar
						if (mToolbar.getVisibility() == View.VISIBLE && mToolbar.isEnabled()) {
							showToolbar(false);
						}

						// hide FAB
						showFloatingActionButton(false);

						mCounter = 0;
					}

					// scroll up
					else if (mCounter < -THRESHOLD || firstVisibleItem == 0) {
						// show toolbar
						if (mToolbar.getVisibility() == View.GONE && mToolbar.isEnabled()) {
							showToolbar(true);
						}

						// show FAB
						showFloatingActionButton(true);

						mCounter = 0;
					}
				}
			}
		});

		// floating action button
		floatingActionButton.setOnClickListener(v -> mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new SearchActionModeCallback()));

		// admob
		setupBannerView();
	}

	private void setupBannerView() {
		String productionUnitId = getString(R.string.admob_unit_id_recipe_list);
		String testUnitId = getString(R.string.admob_test_unit_id_banner);

		if (!productionUnitId.equals("") && NetworkUtility.isOnline(getActivity())) {
			String unitId = CookbookConfig.TEST_ADS ? testUnitId : productionUnitId;
			ViewGroup contentLayout = getActivity().findViewById(R.id.container_content);
			AdMobUtility.createAdView(getActivity(), unitId, AdMobUtility.getAdaptiveBannerAdSize(getActivity()), contentLayout);
		}
	}

	private RecyclerView getRecyclerView() {
		return mRootView != null ? (RecyclerView) mRootView.findViewById(R.id.recipe_list_recycler) : null;
	}

	private void setupStatefulLayout(Bundle savedInstanceState) {
		// reference
		mStatefulLayout = (StatefulLayout) mRootView;

		// state change listener
		mStatefulLayout.setOnStateChangeListener((view, state) -> {
			Logcat.d(String.valueOf(state));

			// bind data
			if (state == StatefulLayout.CONTENT) {
				if (mLazyLoading && mAdapter != null) {
					mAdapter.notifyDataSetChanged();
				} else {
					if (mRecipeList != null && !mRecipeList.isEmpty()) setupView();
				}
			}

			// floating action button
			showFloatingActionButton(state == StatefulLayout.CONTENT);
		});

		// restore state
		mStatefulLayout.restoreInstanceState(savedInstanceState);
	}

	private void setupRecyclerView() {
		GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), getGridSpanCount());
		gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
		RecyclerView recyclerView = getRecyclerView();
		recyclerView.setLayoutManager(gridLayoutManager);
	}

	private int getGridSpanCount() {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);
		float screenWidth = displayMetrics.widthPixels;
		float cellWidth = getResources().getDimension(R.dimen.recipe_list_recycler_item_size);
		return Math.round(screenWidth / cellWidth);
	}

	private void showAboutDialog() {
		// create and show the dialog
		DialogFragment newFragment = AboutDialogFragment.newInstance();
		newFragment.setTargetFragment(this, 0);
		newFragment.show(getFragmentManager(), DIALOG_ABOUT);
	}

	private void startRecipeDetailActivity(View view, long recipeId) {
		Intent intent = RecipeDetailActivity.newIntent(getActivity(), recipeId);
		ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight());
		getActivity().startActivity(intent, options.toBundle());
	}

	private void startWebActivity(String url) {
		try {
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);
		} catch (android.content.ActivityNotFoundException e) {
			// can't start activity
		}
	}

	private class SearchActionModeCallback implements ActionMode.Callback {
		private SearchView mSearchView;
		private SearchSuggestionAdapter mSearchSuggestionAdapter;

		@Override
		public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
			// search view
			mSearchView = new SearchView(((AppCompatActivity) getActivity()).getSupportActionBar().getThemedContext());
			setupSearchView(mSearchView);

			// search menu item
			Drawable drawable = AppCompatResources.getDrawable(getContext(), R.drawable.ic_menu_search);
			MenuItem searchMenuItem = menu.add(Menu.NONE, Menu.NONE, 1, getString(R.string.menu_search));
			searchMenuItem.setIcon(drawable);
			searchMenuItem.setActionView(mSearchView);
			searchMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
			showFloatingActionButton(false);
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode actionMode) {
			showFloatingActionButton(true);
		}

		private void setupSearchView(SearchView searchView) {
			// expand action view
			searchView.setIconifiedByDefault(true);
			searchView.setIconified(false);
			searchView.onActionViewExpanded();

			// search hint
			searchView.setQueryHint(getString(R.string.menu_search_hint));

			// text color
			AutoCompleteTextView searchText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
			searchText.setTextColor(ContextCompat.getColor(getActivity(), R.color.global_text_primary_inverse));
			searchText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.global_text_secondary_inverse));

			// suggestion listeners
			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					// listener
					mSearchListener.onSearch(query);

					// save query for suggestion
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
						SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getActivity(), RecipeSearchRecentSuggestionsProvider.AUTHORITY, RecipeSearchRecentSuggestionsProvider.MODE);
						suggestions.saveRecentQuery(query, null);
					}

					// close action mode
					mActionMode.finish();

					return true;
				}

				@Override
				public boolean onQueryTextChange(String query) {
					if (query.length() > 2) {
						updateSearchSuggestion(query);
					}
					return true;
				}
			});
			searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
				@Override
				public boolean onSuggestionSelect(int position) {
					return false;
				}

				@Override
				public boolean onSuggestionClick(int position) {
					// get query
					Cursor cursor = (Cursor) mSearchSuggestionAdapter.getItem(position);
					String title = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));

					// listener
					mSearchListener.onSearch(title);

					// close action mode
					mActionMode.finish();

					return true;
				}
			});
		}

		private void updateSearchSuggestion(String query) {
			// cursor
			ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();
			String contentUri = "content://" + RecipeSearchRecentSuggestionsProvider.AUTHORITY + '/' + SearchManager.SUGGEST_URI_PATH_QUERY;
			Uri uri = Uri.parse(contentUri);
			Cursor cursor = contentResolver.query(uri, null, null, new String[]{query}, null);

			// searchview content
			if (mSearchSuggestionAdapter == null) {
				// create adapter
				mSearchSuggestionAdapter = new SearchSuggestionAdapter(getActivity(), cursor);

				// set adapter
				mSearchView.setSuggestionsAdapter(mSearchSuggestionAdapter);
			} else {
				// refill adapter
				mSearchSuggestionAdapter.refill(getActivity(), cursor);

				// set adapter
				mSearchView.setSuggestionsAdapter(mSearchSuggestionAdapter);
			}
		}
	}
}
