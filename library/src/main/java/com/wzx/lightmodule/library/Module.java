package com.wzx.lightmodule.library;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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

    public void refresh(Object... targets) {
        onRefresh();
    }

    /**
     * 请求刷新module树。若targets为空,则刷新整颗module树;targets中元素若为module且module树中包含该module,
     * 刷新该module及其父module链;targets中元素若为String,刷新module树中tag为target的module及其父module链。
     *
     * @param targets
     */
    public void requestRefresh(Object... targets) {
        if (mParent != null) {
            mParent.requestRefresh(targets);
        }
    }

    protected String getTag() {
        return null;
    }

    protected boolean contain(Object... targets) {
        if (targets == null) {
            return false;
        }
        for (Object target : targets) {
            if (target == this) {
                return true;
            } else if (!TextUtils.isEmpty(getTag()) && getTag().equals(target)) {
                return true;
            }
        }
        return false;
    }

    protected void onResume() {

    }

    protected void onPause() {

    }

    protected void onDestroyView() {

    }

    protected void onDestroy() {

    }

    void resume() {
        if (!mIsResumed && getViewIfCreated() != null && shouldShowModule()) {
            onResume();
            mIsResumed = true;
        }
    }

    void pause() {
        if (mIsResumed) {
            onPause();
            mIsResumed = false;
        }
    }

    void destroyView() {
        if (getViewIfCreated() != null) {
            onDestroyView();
        }
    }

    void destroy() {
        onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
