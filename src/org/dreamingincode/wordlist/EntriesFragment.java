package org.dreamingincode.wordlist;

import java.util.List;

import org.dreamingincode.wordlist.core.Entry;

import android.os.Bundle;
import android.support.v4.app.ExpandableListFragment;

public class EntriesFragment extends ExpandableListFragment {
	
	public void notifyEntriesChanged() {
		((EntriesAdapter) getExpandableListAdapter()).notifyDataSetChanged();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setGroupIndicator(null);
		// Initialization before onAttach() will fail because getActivity() returns null.
		setExpandableListAdapter(new EntriesAdapter(getActivity(), (List<Entry>) getArguments().getSerializable(BundleKeys.ENTRIES)));
	}
	
}
