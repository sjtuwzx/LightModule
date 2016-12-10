package com.wzx.lightmodule.library;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by wangzhenxing on 16/10/10.
 */

public class ModuleManager extends ModuleGroup {

    public ModuleManager(Context context, ViewGroup container) {
        super(context, container);
    }

    @Override
    public void requestRefresh(Module trigger) {
        if (trigger == null) {
            refresh();
        } else {
            refresh(trigger);
        }
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void pause() {
        super.pause();
    }

    public void destroy() {
        onDestroyView();
        onDestroy();
    }
}
