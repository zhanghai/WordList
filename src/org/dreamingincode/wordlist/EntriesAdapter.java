package org.dreamingincode.wordlist;

import java.util.List;

import org.dreamingincode.wordlist.core.Entry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

// FIXME: Synchronization may be needed

class EntriesAdapter extends BaseExpandableListAdapter {
	
	private class ViewHolder {
		
		public TextView word;
		
		public TextView definition;
		
		public ImageView indicator;
		
		
		public ViewHolder(TextView word, TextView definition, ImageView indicator) {
			this.word = word;
			this.definition = definition;
			this.indicator = indicator;
		}
		
	}
	
	private Context context;
	
	private LayoutInflater layoutInflater;

	private List<Entry> entries;
	
	
	EntriesAdapter(Context context, List<Entry> entries) {
		super();
		this.context = context;
		layoutInflater = LayoutInflater.from(this.context);
		this.entries = entries;
	}
	
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return entries.get(groupPosition).getSubEntries().get(childPosition);
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			ExpandableListView view = new WrapContentExpandableListView(context);
			view.setId(android.R.id.list);
			view.setDrawSelectorOnTop(false);
			view.setGroupIndicator(null);
			view.setLayoutParams(new ExpandableListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
																	ViewGroup.LayoutParams.WRAP_CONTENT));
			view.setAdapter(new SubEntriesAdapter(context, entries.get(groupPosition).getSubEntries(), 0));
			return view;
		} else {
			((SubEntriesAdapter) ((ExpandableListView) convertView).getExpandableListAdapter()).ChangeSubEntries(entries.get(groupPosition).getSubEntries());
			return convertView;
		}
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return entries.get(groupPosition).getSubEntries().size() > 0 ? 1 : 0;
	}
	
	@Override
	public Object getGroup(int groupPosition) {
		return entries.get(groupPosition);
	}
	
	@Override
	public int getGroupCount() {
		return entries.size();
	}
	
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.fragment_entries_listitem, null);
			holder = new ViewHolder((TextView) convertView.findViewById(R.id.fragment_entries_listItem_word),
									(TextView) convertView.findViewById(R.id.fragment_entries_listItem_definition),
									(ImageView) convertView.findViewById(R.id.fragment_entries_listItem_indicator));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Entry entry = entries.get(groupPosition);
		holder.word.setText(entry.getWord());
		holder.definition.setText(entry.definitionsToString());
		boolean isEmpty = entries.get(groupPosition).getSubEntries().size() == 0;
		if (isEmpty) {
			holder.indicator.setVisibility(View.INVISIBLE);
		} else {
			holder.indicator.setVisibility(View.VISIBLE);
			if (isExpanded) {
				holder.indicator.setImageResource(R.drawable.expander_ic_maximized);
			} else {
				holder.indicator.setImageResource(R.drawable.expander_ic_minimized);
			}
		}
		return convertView;
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}