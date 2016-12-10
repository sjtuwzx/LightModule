package com.wzx.lightmodule.module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzx.lightmodule.R;
import com.wzx.lightmodule.library.Module;

/**
 * Created by wangzhenxing on 16/12/10.
 */

public class TopModule extends Module implements View.OnClickListener {

    public TopModule(Context context) {
        super(context);
    }

    @Override
    public boolean shouldShowModule() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.top_module, parent, false);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        requestRefresh(false);
    }
}
