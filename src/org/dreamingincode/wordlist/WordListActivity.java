package org.dreamingincode.wordlist;

import java.io.File;
import java.io.Serializable;

import org.dreamingincode.wordlist.core.WordList;

import android.os.Bundle;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class WordListActivity extends FragmentActivity {

	private static final String ERROR = "org.dreamingincode.wordlist.WordListActivity.error";
	private CriticalError error = new CriticalError();
	
	private static final String WORDLIST = "org.dreamingincode.wordlist.WordListActivity.wordList"; 
	private WordList wordList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wordlist);
		setupActionBar();
		
		if(savedInstanceState != null) {
			error = (CriticalError) savedInstanceState.getSerializable(ERROR);
			if (error.restore(this)) {
				return;
			}
			wordList = (WordList) savedInstanceState.getSerializable(WORDLIST);
		} else {
			try {
				wordList = WordList.parse((File) getIntent().getSerializableExtra(BundleKeys.WORDLIST_FILE));
			} catch (Exception exception) {
				error.set(this, exception.toString());
				return;
			}
			
			setTitle(wordList.getName());
			
			EntriesFragment entriesFragment = new EntriesFragment();
			Bundle arguments = new Bundle();
			arguments.putSerializable(BundleKeys.ENTRIES, (Serializable) wordList.getEntries());
			entriesFragment.setArguments(arguments);
			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.fragment_entries_container, entriesFragment);
			fragmentTransaction.commit();
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
		outState.putSerializable(WORDLIST, wordList);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
