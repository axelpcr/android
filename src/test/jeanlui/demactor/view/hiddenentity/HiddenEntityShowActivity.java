/**************************************************************************
 * HiddenEntityShowActivity.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.hiddenentity;

import com.jeanlui.demactor.R;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.view.hiddenentity.HiddenEntityShowFragment.DeleteCallback;
import android.os.Bundle;

/** HiddenEntity show Activity.
 *
 * This only contains a HiddenEntityShowFragment.
 *
 * @see android.app.Activity
 */
public class HiddenEntityShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_hiddenentity_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
