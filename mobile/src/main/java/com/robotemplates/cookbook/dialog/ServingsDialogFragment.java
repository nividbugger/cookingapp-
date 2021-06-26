package com.robotemplates.cookbook.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.robotemplates.cookbook.R;

public class ServingsDialogFragment extends DialogFragment {
	private static final String ARGUMENT_SERVINGS = "servings";

	private View mRootView;
	private int mServings;
	private ServingsDialogListener mListener;

	public interface ServingsDialogListener {
		void onServingsDialogPositiveClick(DialogFragment dialog, int servings);
	}

	public static ServingsDialogFragment newInstance(int servings) {
		ServingsDialogFragment fragment = new ServingsDialogFragment();

		// arguments
		Bundle arguments = new Bundle();
		arguments.putInt(ARGUMENT_SERVINGS, servings);
		fragment.setArguments(arguments);

		return fragment;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCancelable(true);
		setRetainInstance(true);

		// handle fragment arguments
		Bundle arguments = getArguments();
		if (arguments != null) {
			handleArguments(arguments);
		}

		// set callback listener
		try {
			mListener = (ServingsDialogListener) getTargetFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException(getTargetFragment().toString() + " must implement " + ServingsDialogListener.class.getName());
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// cancelable on touch outside
		if (getDialog() != null) getDialog().setCanceledOnTouchOutside(true);

		// restore saved state
		handleSavedInstanceState();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDestroyView() {
		// http://code.google.com/p/android/issues/detail?id=17423
		if (getDialog() != null && getRetainInstance()) getDialog().setDismissMessage(null);
		super.onDestroyView();
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		// save current instance state
		super.onSaveInstanceState(outState);

		EditText countEditText = mRootView.findViewById(R.id.dialog_servings_count);

		mServings = Integer.parseInt(countEditText.getText().toString());
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mRootView = inflater.inflate(R.layout.dialog_servings, null);

		builder
				.setTitle(R.string.dialog_servings_title)
				.setView(mRootView)
				.setPositiveButton(android.R.string.ok, (dialog, id) -> {
					EditText countEditText = mRootView.findViewById(R.id.dialog_servings_count);
					String number = countEditText.getText().toString();

					if (!number.isEmpty()) {
						int servings = Integer.parseInt(number);
						if (servings > 0) {
							mListener.onServingsDialogPositiveClick(ServingsDialogFragment.this, servings);
							dialog.dismiss();
						}
					}
				})
				.setNegativeButton(android.R.string.cancel, (dialog, id) -> {
					// do nothing
				});

		// create dialog from builder
		final Dialog dialog = builder.create();

		// show soft keyboard
		dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		return dialog;
	}

	private void handleArguments(Bundle arguments) {
		if (arguments.containsKey(ARGUMENT_SERVINGS)) {
			mServings = (int) arguments.get(ARGUMENT_SERVINGS);
		}
	}

	private void handleSavedInstanceState() {
		EditText countEditText = mRootView.findViewById(R.id.dialog_servings_count);
		countEditText.setText(String.valueOf(mServings));
		countEditText.setSelection(countEditText.getText().length());
	}
}
