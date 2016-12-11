package com.wzx.lightmodule.sample.module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wzx.lightmodule.Module;
import com.wzx.lightmodule.sample.R;

/**
 * Created by wangzhenxing on 16/12/10.
 */

public class TopModule extends Module implements View.OnClickListener {

    private Button mButton;
    private int mClickCount = 0;

    public TopModule(Context context) {
        super(context);
    }

    @Override
    public boolean shouldShowModule() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        mButton = (Button) inflater.inflate(R.layout.top_module, parent, false);
        mButton.setOnClickListener(this);
        return mButton;
    }

    @Override
    public void onRefresh() {
        mButton.setText(String.format("第%d次点击", mClickCount));
    }

    @Override
    public void onClick(View v) {
        ++mClickCount;
        if (mClickCount % 3 == 0) {
            requestRefresh(this, "Item1", "Item2", "Item5", "Item6");
        } else if (mClickCount % 3 == 1){
            requestRefresh();
        } else if (mClickCount % 3 == 2) {
            requestRefreshSelf();
        }
    }
}
