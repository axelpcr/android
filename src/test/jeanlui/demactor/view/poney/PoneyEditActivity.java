/**************************************************************************
 * PoneyEditActivity.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.view.poney;

import com.tactfactory.demact.R;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Poney edit Activity.
 *
 * This only contains a PoneyEditFragment.
 *
 * @see android.app.Activity
 */
public class PoneyEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_poney_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
