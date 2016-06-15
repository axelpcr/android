/**************************************************************************
 * ScoreShowActivity.java, demactor Android
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
import com.jeanlui.demactor.view.score.ScoreShowFragment.DeleteCallback;
import android.os.Bundle;

/** Score show Activity.
 *
 * This only contains a ScoreShowFragment.
 *
 * @see android.app.Activity
 */
public class ScoreShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_score_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
