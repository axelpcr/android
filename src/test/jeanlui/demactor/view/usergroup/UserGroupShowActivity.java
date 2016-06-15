/**************************************************************************
 * UserGroupShowActivity.java, demactor Android
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
import com.jeanlui.demactor.view.usergroup.UserGroupShowFragment.DeleteCallback;
import android.os.Bundle;

/** UserGroup show Activity.
 *
 * This only contains a UserGroupShowFragment.
 *
 * @see android.app.Activity
 */
public class UserGroupShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_usergroup_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
