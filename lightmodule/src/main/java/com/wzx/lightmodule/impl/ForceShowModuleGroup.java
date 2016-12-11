package com.wzx.lightmodule.impl;

import android.content.Context;
import android.view.ViewGroup;

import com.wzx.lightmodule.ModuleGroup;

/**
 * Created by wangzhenxing on 16/11/18.
 */

public class ForceShowModuleGroup extends ModuleGroup {

    public ForceShowModuleGroup(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    public ForceShowModuleGroup(Context context, ViewGroup container) {
        super(context, container);
    }

    @Override
    public boolean shouldShowModule() {
        return true;
    }
}
