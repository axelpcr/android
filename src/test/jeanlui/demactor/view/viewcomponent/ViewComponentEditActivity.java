/**************************************************************************
 * ViewComponentEditActivity.java, demactor Android
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

/** ViewComponent edit Activity.
 *
 * This only contains a ViewComponentEditFragment.
 *
 * @see android.app.Activity
 */
public class ViewComponentEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_viewcomponent_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
