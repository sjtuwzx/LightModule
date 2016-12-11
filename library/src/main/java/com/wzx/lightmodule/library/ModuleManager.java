package com.wzx.lightmodule.library;

import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;

/**
 * Created by wangzhenxing on 16/10/10.
 */

public class ModuleManager extends ModuleGroup {

    private Handler mHandler = new Handler();

    public ModuleManager(Context context, ViewGroup container) {
        super(context, container);
    }

    @Override
    public void requestRefreshModules(Object... targets) {
        mHandler.post(new RefreshTask(targets));
    }

    public void refresh() {
        super.refresh();
    }

    private class RefreshTask implements Runnable {

        private Object[] mTargets;

        private RefreshTask(Object... targets) {
            mTargets = targets;
        }

        @Override
        public void run() {
            refresh(mTargets);
        }
    }

    public void start() {
        start(true);
    }

    public void resume() {
        resume(true);
    }

    public void pause() {
        pause(true);
    }

    public void stop() {
        stop(true);
    }

    public void destroy() {
        destroyView();
        super.destroy();
    }
}
