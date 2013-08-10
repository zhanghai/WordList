package org.dreamingincode.wordlist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class WrapContentExpandableListView extends ExpandableListView {

	public WrapContentExpandableListView(Context context) {
		super(context);
	}

	public WrapContentExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WrapContentExpandableListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
			// MeasureSpec.UNSPECIFIED (0x0) is not handled correctly by
			// ListView.measureHeightOfChildren()
			// Workaround makeMeasureSpec() overflow in early versions
			// See source code of MeasureSpec for 0x3FFFFFFF
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(0x3FFFFFFF,
					MeasureSpec.AT_MOST);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
