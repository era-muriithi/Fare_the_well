package com.blackgoose.fare_the_well;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ExpandedGridviewAdapter extends GridView {

    public ExpandedGridviewAdapter(Context context) {
        super(context);
    }

    public ExpandedGridviewAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandedGridviewAdapter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST
        );
        super.onMeasure(widthMeasureSpec, expandSpec);
        getLayoutParams().height = getMeasuredHeight();
    }
}

