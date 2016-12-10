package com.wzx.lightmodule.module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wzx.lightmodule.R;
import com.wzx.lightmodule.library.Module;

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
        if (mClickCount % 2 == 0) {
            requestRefresh(true);
        } else {
            requestRefresh(false);
        }
    }
}