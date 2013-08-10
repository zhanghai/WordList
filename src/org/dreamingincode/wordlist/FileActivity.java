package org.dreamingincode.wordlist;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class FileActivity extends FragmentActivity {
	
	private static final String ERROR = "org.dreamingincode.wordlist.FileActivity.error";
	private CriticalError error = new CriticalError();
	
	private static final String FILES = "org.dreamingincode.wordlist.FileActivity.files"; 
	private List<File> files;
	
	
	private boolean LoadFiles() {
		File directory = new File(Environment.getExternalStorageDirectory().getPath() + "/WordList");
		File[] filesList = directory.listFiles();
		if (filesList == null) {
			error.set(this, "单词表目录 " + directory.getAbsolutePath() + " 为空。");
			return false;
		}
		files = new ArrayList<File>();
		Collections.addAll(files, filesList);
		Collections.sort(files);
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.title_activity_main));
		setContentView(R.layout.activity_file);
		setupActionBar();
		
		if(savedInstanceState != null) {
			error = (CriticalError) savedInstanceState.getSerializable(ERROR);
			if (error.restore(this)) {
				return;
			}
			files = (List<File>) savedInstanceState.getSerializable(FILES);
		} else {
			if (LoadFiles()) {
				FilesFragment fileListFragment = new FilesFragment();
				Bundle arguments = new Bundle();
				arguments.putSerializable(BundleKeys.FILES, (Serializable) files);
				fileListFragment.setArguments(arguments);
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.add(R.id.fragment_files_container, fileListFragment);
				fragmentTransaction.commit();
			} else {
				return;
			}
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putSerializable(ERROR, error);
		outState.putSerializable(FILES, (Serializable) files);
	}
	
}
