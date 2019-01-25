package com.teplot.testapp.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class SendGridView extends GridView {
    public SendGridView(Context context) {
        super(context);
    }

    public SendGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SendGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
