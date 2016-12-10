package com.wzx.lightmodule.library;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang_zx on 2016/3/29.
 */
public class ModuleGroup extends Module implements ModuleParent {

    private List<Module> mChildren = new ArrayList<Module>();
    private int mLayoutResource;
    protected ViewGroup mContainer;

    public ModuleGroup(Context context, int layoutResource) {
        super(context);
        mLayoutResource = layoutResource;
    }

    public ModuleGroup(Context context, ViewGroup container) {
        super(context);
        mContainer = container;
    }

    public void addModule(Module module) {
        module.setParent(this);
        mChildren.add(module);
    }

    public List<Module> getLeafModules() {
        List<Module> leafModules = new ArrayList<Module>();
        for (Module child : mChildren) {
            if (child instanceof ModuleGroup) {
                ModuleGroup moduleGroup = (ModuleGroup)child;
                leafModules.addAll(moduleGroup.getLeafModules());
            } else {
                leafModules.add(child);
            }
        }
        return leafModules;
    }

    @Override
    public boolean shouldShowModule() {
        for (Module child : mChildren) {
            return child.shouldShowModule();
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        if (mContainer == null) {
            mContainer = (ViewGroup) inflater.inflate(mLayoutResource, parent, false);
        }
        return mContainer;
    }

    @Override
    public void refresh(Object... targets) {
        boolean isTargetsEmpty = targets == null || targets.length == 0;
        int index = 0;
        for (Module child : mChildren) {
            View childView = child.getViewIfCreated();

            if (isTargetsEmpty || child.contain(targets)) {
                if (child.shouldShowModule()) {
                    if (childView == null) {
                        childView = child.getView(mContainer);
                        if (childView.getParent() == null) {
                            mContainer.addView(childView, index);
                        }
                    }
                    childView.setVisibility(View.VISIBLE);

                    if (child instanceof ModuleGroup) {
                        ModuleGroup childGroup = (ModuleGroup) child;
                        childGroup.refresh(targets);
                    } else {
                        child.refresh();
                    }
                    child.resume();
                } else if (childView != null) {
                    childView.setVisibility(View.GONE);
                    child.pause();
                }
            }
            if (childView != null && mContainer.indexOfChild(childView) >= 0) {
                ++index;
            }
        }

        onRefresh();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    protected boolean contain(Object... targets) {
        for (Module child : mChildren) {
            if (child.contain(targets)) {
                return true;
            }
        }
        return false;
    }

    @Override
    void resume() {
        for (Module child : mChildren) {
            child.resume();
        }
        super.resume();
    }

    @Override
    void pause() {
        for (Module child : mChildren) {
            child.pause();
        }
        super.pause();
    }

    @Override
    protected void onDestroyView() {
        for (Module child : mChildren) {
            if (child.getViewIfCreated() != null) {
                child.onDestroyView();
            }
        }
    }

    @Override
    protected void onDestroy() {
        for (Module child : mChildren) {
            child.onDestroy();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Module child : mChildren) {
            if (child.shouldShowModule()) {
                child.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
