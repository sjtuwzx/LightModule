package com.wzx.lightmodule.impl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wzx.lightmodule.ModuleGroup;

/**
 * Created by wangzhenxing on 16/11/1.
 */

public class VerticalLinearModuleGroup extends ModuleGroup {

    private int mHeight;
    private Drawable mDivider;

    public VerticalLinearModuleGroup(Context context, int height) {
        this(context, height, null);
    }

    public VerticalLinearModuleGroup(Context context, int height, Drawable divider) {
        super(context, View.NO_ID);
        mHeight = height;
        mDivider = divider;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        if (mContainer == null) {
            mContainer = createContainer();
        }

        return mContainer;
    }

    private ViewGroup createContainer() {
        LinearLayout container = new LinearLayout(mContext);
        container.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
        container.setLayoutParams(layoutParams);
        if (mDivider != null) {
            container.setDividerDrawable(mDivider);
            container.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        }

        return container;
    }
}
