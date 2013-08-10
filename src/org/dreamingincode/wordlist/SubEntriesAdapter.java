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

//FIXME: Synchronization may be needed

class SubEntriesAdapter extends BaseExpandableListAdapter {
	
	private class ViewHolder {
		
		public TextView relation;
		
		public TextView word;
		
		public TextView definition;
		
		public ImageView indicator;
		
		
		public ViewHolder(TextView relation, TextView word, TextView definition, ImageView indicator) {
			this.relation = relation;
			this.word = word;
			this.definition = definition;
			this.indicator = indicator;
		}
		
	}
	
	private Context context;
	
	private LayoutInflater layoutInflater;

	private List<Entry.SubEntryInformation> subEntries;
	
	private int indentation;
	
	
	public SubEntriesAdapter(Context context, List<Entry.SubEntryInformation> subEntries, int indentation) {
		super();
		this.context = context;
		layoutInflater = LayoutInflater.from(this.context);
		this.subEntries = subEntries;
		this.indentation = indentation;
	}
	
	
	public void ChangeSubEntries(List<Entry.SubEntryInformation> subEntries) {
		this.subEntries = subEntries;
		notifyDataSetChanged();
	}
	
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return subEntries.get(groupPosition).entry.getSubEntries().get(childPosition);
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			ExpandableListView view = new FullSizeExpandableListView(context);
			view.setId(android.R.id.list);
			view.setDrawSelectorOnTop(false);
			view.setGroupIndicator(null);
			view.setLayoutParams(new ExpandableListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
																	ViewGroup.LayoutParams.WRAP_CONTENT));
			view.setAdapter(new SubEntriesAdapter(context, subEntries.get(groupPosition).entry.getSubEntries(), indentation + 1));
			return view;
		} else {
			((SubEntriesAdapter) ((ExpandableListView) convertView).getExpandableListAdapter()).ChangeSubEntries(subEntries.get(groupPosition).entry.getSubEntries());
			return convertView;
		}
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return subEntries.get(groupPosition).entry.getSubEntries().size() > 0 ? 1 : 0;
	}
	
	@Override
	public Object getGroup(int groupPosition) {
		return subEntries.get(groupPosition);
	}
	
	@Override
	public int getGroupCount() {
		return subEntries.size();
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
			convertView = layoutInflater.inflate(R.layout.fragment_entries_sublistitem, null);
			convertView.setPadding(convertView.getPaddingLeft() + Math.round(indentation * context.getResources().getDimension(R.dimen.fragment_entries_listItem_indentation)),
					convertView.getPaddingTop(), convertView.getPaddingRight(), convertView.getPaddingBottom());
			holder = new ViewHolder((TextView) convertView.findViewById(R.id.fragment_entries_subListItem_relation),
									(TextView) convertView.findViewById(R.id.fragment_entries_subListItem_word),
									(TextView) convertView.findViewById(R.id.fragment_entries_subListItem_definition),
									(ImageView) convertView.findViewById(R.id.fragment_entries_subListItem_indicator));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.relation.setText(subEntries.get(groupPosition).relation.getAnnotation());
		Entry entry = subEntries.get(groupPosition).entry;
		holder.word.setText(entry.getWord());
		holder.definition.setText(entry.definitionsToString());
		boolean isEmpty = subEntries.get(groupPosition).entry.getSubEntries().size() == 0;
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
