package com.wzx.lightmodule;

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

    private boolean mIsStarted = false;
    private boolean mIsResumed = false;

    private boolean mIsForcePause = false;
    private boolean mIsForceStop = false;

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

    void refresh(Object... targets) {
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
            mParent.requestRefreshModules(targets);
        }
    }

    /**
     * 请求刷新自身
     */
    public final void requestRefreshSelf() {
        requestRefresh(this);
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

    protected void onStart() {

    }

    protected void onResume() {

    }

    protected void onPause() {

    }

    protected void onStop() {

    }

    protected void onDestroyView() {

    }

    protected void onDestroy() {

    }

    void start(boolean force) {
        mIsForceStop &= !force;
        if (!mIsForceStop && !mIsStarted && getViewIfCreated() != null && shouldShowModule()) {
            onStart();
            mIsStarted = true;
        }
    }

    void resume(boolean force) {
        mIsForcePause &= !force;
        if (!mIsForcePause && !mIsResumed && getViewIfCreated() != null && shouldShowModule()) {
            onResume();
            mIsResumed = true;
        }
    }

    void pause(boolean force) {
        mIsForcePause |= force;
        if (mIsResumed) {
            onPause();
            mIsResumed = false;
        }
    }

    void stop(boolean force) {
        mIsForceStop |= force;
        if (mIsStarted) {
            onStop();
            mIsStarted = false;
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
