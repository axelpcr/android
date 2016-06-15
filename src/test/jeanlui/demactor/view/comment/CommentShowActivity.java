/**************************************************************************
 * CommentShowActivity.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.comment;

import com.jeanlui.demactor.R;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.view.comment.CommentShowFragment.DeleteCallback;
import android.os.Bundle;

/** Comment show Activity.
 *
 * This only contains a CommentShowFragment.
 *
 * @see android.app.Activity
 */
public class CommentShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_comment_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
