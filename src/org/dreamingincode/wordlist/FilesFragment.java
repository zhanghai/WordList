package org.dreamingincode.wordlist;

import java.io.File;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class FilesFragment extends ListFragment {
	
	public void notifyWordListsChanged() {
		((FilesAdapter) getListAdapter()).notifyDataSetChanged();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Initialization before onAttach() will fail because getActivity() returns null.
		setListAdapter(new FilesAdapter(getActivity(), (List<File>) getArguments().getSerializable(BundleKeys.FILES)));
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent(getActivity(), WordListActivity.class);
		intent.putExtra(BundleKeys.WORDLIST_FILE, (File) ((FilesAdapter) l.getAdapter()).getItem(position));
		startActivity(intent);
	}
	
}
