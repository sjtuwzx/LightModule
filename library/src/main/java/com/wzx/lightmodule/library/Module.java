package com.wzx.lightmodule.library;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wang_zx on 2016/3/29.
 */
public abstract class Module {

    protected Context mContext;
    private LayoutInflater mInflater;

    protected ModuleParent mParent;

    private View mView;

    private boolean mIsResumed = false;

    public abstract boolean shouldShowModule();

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup parent);

    public abstract void onRefresh();

    public Module(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    void setParent(ModuleParent parent) {
        mParent = parent;
    }

    protected final View getView(ViewGroup parent) {
        if (mView == null) {
            mView = onCreateView(mInflater, parent);
        }

        return mView;
    }

    public final View getViewIfCreated() {
        return mView;
    }

    public void refresh() {
        onRefresh();
    }

    public final void requestRefresh(boolean forceSelf) {
        if (mParent != null) {
            mParent.requestRefresh(forceSelf ? this : null);
        }
    }

    protected boolean contain(Module module) {
        return module == this;
    }

    protected void onResume() {

    }

    protected void onPause() {

    }

    protected void onDestroyView() {

    }

    protected void onDestroy() {

    }

    protected boolean isResumed() {
        return mIsResumed;
    }

    void resume() {
        onResume();
        mIsResumed = true;
    }

    void pause() {
        onPause();
        mIsResumed = false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
