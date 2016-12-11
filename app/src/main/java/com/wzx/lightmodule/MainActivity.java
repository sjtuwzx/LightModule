package com.wzx.lightmodule;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.wzx.lightmodule.library.Module;
import com.wzx.lightmodule.library.ModuleManager;
import com.wzx.lightmodule.library.impl.VerticalScrollModuleGroup;
import com.wzx.lightmodule.module.ItemModule;
import com.wzx.lightmodule.module.TopModule;

public class MainActivity extends AppCompatActivity {

    private ModuleManager mModuleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildModuleManager();
    }

    private void buildModuleManager() {
        mModuleManager = new ModuleManager(this, (ViewGroup) findViewById(R.id.activity_main));
        Module topModule = new TopModule(this);
        mModuleManager.addModule(topModule);

        Drawable divider = getResources().getDrawable(R.drawable.gray_horizontal_separator);
        VerticalScrollModuleGroup scrollModuleGroup = new VerticalScrollModuleGroup(this, divider);
        for (int i = 0; i < 20; i++) {
            scrollModuleGroup.addModule(new ItemModule(this, i + 1));
        }
        mModuleManager.addModule(scrollModuleGroup);
        mModuleManager.refresh();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mModuleManager.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mModuleManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mModuleManager.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mModuleManager.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mModuleManager.destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mModuleManager.onActivityResult(requestCode, resultCode, data);
    }
}
