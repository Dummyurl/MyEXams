package com.saxxis.myexamspace.helper.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by chaitanyat on 30-03-2018.
 */

public class CustomGridLayoutManager extends GridLayoutManager {
    public CustomGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }
    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
