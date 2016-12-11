package com.wzx.lightmodule.impl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.wzx.lightmodule.ModuleGroup;
import com.wzx.lightmodule.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangzhenxing on 16/10/31.
 */

public class VerticalScrollModuleGroup extends ModuleGroup implements ViewTreeObserver.OnScrollChangedListener {

    private Drawable mDivider;
    private int mBackgroundColor = Color.TRANSPARENT;

    private ScrollView mScrollView;
    private Set<OnScrollChangedListener> mOnScrollChangedListeners = new HashSet<>();

    public VerticalScrollModuleGroup(Context context) {
        super(context, View.NO_ID);
    }

    public VerticalScrollModuleGroup(Context context, Drawable divider) {
        super(context, View.NO_ID);
        mDivider = divider;
    }

    public void setDivider(Drawable divider) {
        mDivider = divider;
    }

    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        if (mScrollView == null) {
            mScrollView = (ScrollView) inflater.inflate(R.layout.vercial_scroll_module, parent, false);
            mScrollView.setBackgroundColor(mBackgroundColor);
            mContainer = (ViewGroup) mScrollView.findViewById(R.id.content);
            if (mDivider != null) {
                LinearLayout linearLayout = (LinearLayout) mContainer;
                linearLayout.setDividerDrawable(mDivider);
                linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            }

            mScrollView.getViewTreeObserver().addOnScrollChangedListener(this);
        }
        return mScrollView;
    }

    public void addScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListeners.add(listener);
    }

    @Override
    public void onScrollChanged() {
        for (OnScrollChangedListener listener : mOnScrollChangedListeners) {
            listener.onScrollChanged(mScrollView);
        }
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView scrollView);
    }
}
