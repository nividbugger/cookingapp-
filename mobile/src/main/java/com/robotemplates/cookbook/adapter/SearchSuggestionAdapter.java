package com.robotemplates.cookbook.adapter;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.robotemplates.cookbook.R;

public class SearchSuggestionAdapter extends CursorAdapter {
	public SearchSuggestionAdapter(Context context, Cursor cursor) {
		super(context, cursor, 0);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		return inflater.inflate(R.layout.search_suggestion_item, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// reference
		LinearLayout root = (LinearLayout) view;
		TextView titleTextView = root.findViewById(R.id.search_suggestion_item_title);

		// content
		final int index = cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1);
		titleTextView.setText(cursor.getString(index));
	}

	public void refill(Context context, Cursor cursor) {
		changeCursor(cursor);
	}
}
