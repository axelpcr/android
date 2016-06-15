/**************************************************************************
 * UserGroupCreateActivity.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.usergroup;

import com.jeanlui.demactor.R;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * UserGroup create Activity.
 *
 * This only contains a UserGroupCreateFragment.
 *
 * @see android.app.Activity
 */
public class UserGroupCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_usergroup_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
