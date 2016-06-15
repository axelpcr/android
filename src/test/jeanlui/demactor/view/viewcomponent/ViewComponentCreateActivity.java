/**************************************************************************
 * ViewComponentCreateActivity.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.viewcomponent;

import com.jeanlui.demactor.R;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * ViewComponent create Activity.
 *
 * This only contains a ViewComponentCreateFragment.
 *
 * @see android.app.Activity
 */
public class ViewComponentCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_viewcomponent_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
