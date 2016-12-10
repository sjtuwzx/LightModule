package com.wzx.lightmodule.module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wzx.lightmodule.R;
import com.wzx.lightmodule.library.Module;

/**
 * Created by wangzhenxing on 16/12/10.
 */

public class ItemModule extends Module {

    private TextView mTextView;
    private int mIndex;
    private int mRefreshCount = 0;

    public ItemModule(Context context, int index) {
        super(context);
        mIndex = index;
    }

    @Override
    public boolean shouldShowModule() {
        return mIndex % 3 != 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        mTextView = (TextView) inflater.inflate(R.layout.item_module, parent, false);
        return mTextView;
    }

    @Override
    public void onRefresh() {
        ++mRefreshCount;
        mTextView.setText(String.format("%s[%d]第%d次刷新", getClass().getSimpleName(), mIndex, mRefreshCount));
    }
}
