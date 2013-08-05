package com.gaobo.e5community.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.gaobo.e5community.R;

public class ExitDialog {
	private Context context;

	public ExitDialog(Context context) {
		this.context = context;
	}

	public Dialog showDialog() {
		Dialog dialog = new AlertDialog.Builder(context)
				.setIcon(R.drawable.ic_launcher)
				.setTitle(context.getString(R.string.alteout))
				.setNegativeButton(context.getString(R.string.cancel),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						})
				.setPositiveButton(context.getString(R.string.out),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								((Activity) context).finish();
							}
						}).create();
		dialog.show();
		return dialog;
	}
}
