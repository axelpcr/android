/**************************************************************************
 * HomeActivity.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.view.jockey.JockeyListActivity;
import com.jeanlui.demactor.view.poney.PoneyListActivity;
import com.jeanlui.demactor.view.score.ScoreListActivity;
import com.jeanlui.demactor.view.user.UserListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * BEWARE : This class is regenerated with orm:generate:crud. Don't modify it.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity 
        implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.initButtons();
    }
    
    @Override
    protected int getContentView() {
        return R.layout.main;
    }

    /**
     * Initialize the buttons click listeners.
     */
    private void initButtons() {
        this.findViewById(R.id.jockey_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.poney_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.score_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.user_list_button)
                        .setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.jockey_list_button:
                intent = new Intent(this,
                        JockeyListActivity.class);
                break;

            case R.id.poney_list_button:
                intent = new Intent(this,
                        PoneyListActivity.class);
                break;

            case R.id.score_list_button:
                intent = new Intent(this,
                        ScoreListActivity.class);
                break;

            case R.id.user_list_button:
                intent = new Intent(this,
                        UserListActivity.class);
                break;

            default:
                intent = null;
                break;
        }

        if (intent != null) {
            this.startActivity(intent);
        }
    }
}
