/**************************************************************************
 * ScoreCreateActivity.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.view.score;

import com.tactfactory.demact.R;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * Score create Activity.
 *
 * This only contains a ScoreCreateFragment.
 *
 * @see android.app.Activity
 */
public class ScoreCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_score_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
