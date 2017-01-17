package com.wzx.lightmodule;

import android.support.v4.app.Fragment;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangzhenxing on 17/1/17.
 */

public class LifecycleFragment extends Fragment {

    private Set<ModuleManager> mModuleManagerSet = new HashSet<>();

    void addModuleManager(ModuleManager manager) {
        mModuleManagerSet.add(manager);
    }

    @Override
    public void onStart() {
        super.onStart();
        for (ModuleManager manager : mModuleManagerSet) {
            manager.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (ModuleManager manager : mModuleManagerSet) {
            manager.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (ModuleManager manager : mModuleManagerSet) {
            manager.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (ModuleManager manager : mModuleManagerSet) {
            manager.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (ModuleManager manager : mModuleManagerSet) {
            manager.destroyView();
            manager.destroy();
        }
    }
}
