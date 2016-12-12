package com.wzx.lightmodule.sample.module;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wzx.lightmodule.Module;
import com.wzx.lightmodule.sample.R;

/**
 * Created by wangzhenxing on 16/12/10.
 */

public class ItemModule extends Module {

    private static final String TAG = ItemModule.class.getSimpleName();

    private TextView mTextView;
    private int mIndex;
    private int mRefreshCount = 0;

    public ItemModule(Context context, int index) {
        super(context);
        mIndex = index;
    }

    @Override
    public boolean shouldShowModule() {
        return mIndex % 5 != 0;
    }

    @Override
    protected String getTag() {
        return "Item" + mIndex;
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
        Log.i(TAG, String.format("Item[%d] refresh[%d]", mIndex, mRefreshCount));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, String.format("Item[%d] onStart[%d]", mIndex, mRefreshCount));
        requestRefresh();
        requestRefresh("Item1");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, String.format("Item[%d] onResume[%d]", mIndex, mRefreshCount));
        requestRefresh();
        requestRefresh("Item2");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, String.format("Item[%d] onPause[%d]", mIndex, mRefreshCount));
        requestRefresh();
        requestRefresh("Item3");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, String.format("Item[%d] onStop[%d]", mIndex, mRefreshCount));
        requestRefresh();
        requestRefresh("Item4");
    }
}
