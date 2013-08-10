package org.dreamingincode.wordlist;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FilesAdapter extends BaseAdapter {
	
	private Context context;
	
	private LayoutInflater layoutInflater;
	
	private List<File> files;
	
	
	FilesAdapter(Context context, List<File> files) {
		super();
		this.context = context;
		layoutInflater = LayoutInflater.from(this.context);
		this.files = files;
	}
	
	
	@Override
	public int getCount() {
		return files.size();
	}
	
	@Override
	public Object getItem(int position) {
		return files.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.fragment_files_listitem, null);
		}
		((TextView) convertView).setText(files.get(position).getName());
		return convertView;
	}

}
