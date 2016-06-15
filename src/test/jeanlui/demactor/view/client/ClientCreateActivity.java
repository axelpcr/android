/**************************************************************************
 * ClientCreateActivity.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.client;

import com.jeanlui.demactor.R;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * Client create Activity.
 *
 * This only contains a ClientCreateFragment.
 *
 * @see android.app.Activity
 */
public class ClientCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_client_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
