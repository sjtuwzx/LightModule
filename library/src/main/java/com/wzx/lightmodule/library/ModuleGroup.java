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
            if (child.shouldShowModule()) {
                return true;
            }
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
    public final void requestRefresh(Object... targets) {
        throw new UnsupportedOperationException("Only support request refresh from leaf module");
    }

    @Override
    public void requestRefreshModules(Object... targets) {
        if (mParent != null) {
            mParent.requestRefreshModules(targets);
        }
    }

    /**
     * 刷新module及子module。若targets为空,则刷新自身及所有后裔节点;targets中元素若为module且后裔节点中包含该module,
     * 刷新该module及包含该module的ModuleGroup;targets中元素若为String,刷新后裔节点中tag为target的module及包含该module的ModuleGroup。
     *
     * @param targets
     */
    @Override
    void refresh(Object... targets) {
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
                    child.start(false);
                    child.resume(false);
                } else if (childView != null) {
                    childView.setVisibility(View.GONE);
                    child.pause(false);
                    child.stop(false);
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
    final void start(boolean force) {
        for (Module child : mChildren) {
            child.start(force);
        }
        super.start(force);
    }

    @Override
    final void resume(boolean force) {
        for (Module child : mChildren) {
            child.resume(force);
        }
        super.resume(force);
    }

    @Override
    final void pause(boolean force) {
        for (Module child : mChildren) {
            child.pause(force);
        }
        super.pause(force);
    }

    @Override
    final void stop(boolean force) {
        for (Module child : mChildren) {
            child.stop(force);
        }
        super.stop(force);
    }

    @Override
    void destroyView() {
        for (Module child : mChildren) {
            child.destroyView();
        }
        super.destroyView();
    }

    @Override
    void destroy() {
        for (Module child : mChildren) {
            child.destroy();
        }
        super.destroy();
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
