package org.dreamingincode.wordlist;

import java.io.Serializable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

class CriticalError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private boolean shown;
	
	private CharSequence message;
	
	
	public boolean restore(Activity activity) {
		if (shown) {
			show(activity);
		}
		return shown;
	}
	
	public void set(Activity activity, CharSequence message) {
		shown = true;
		this.message = message; 
		
		show(activity);
	}
	
	public void reset() {
		shown = false;
		message = null;
	}
	
	private void show(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(android.R.string.dialog_alert_title);
		builder.setMessage(message);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.finish();
			}
		});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				activity.finish();
			}
		});
		builder.create().show();
	}
	
}

class CriticalErrorState {
	
}
