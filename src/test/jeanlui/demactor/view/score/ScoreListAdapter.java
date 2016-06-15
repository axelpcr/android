/**************************************************************************
 * ScoreListAdapter.java, demactor Android
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

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tactfactory.demact.entity.Score;
import com.tactfactory.demact.harmony.view.HarmonyCursorAdapter;
import com.tactfactory.demact.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.ScoreContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.UserContract;

/**
 * List adapter for Score entity.
 */
public class ScoreListAdapter extends HarmonyCursorAdapter<Score> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public ScoreListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public ScoreListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Score cursorToItem(Cursor cursor) {
        return ScoreContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return ScoreContract.COL_IDFD1;
    }
    
    @Override
    protected HarmonyViewHolder<Score> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Score> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_score);
        }

        /**
         * Populate row with a {@link Score}.
         *
         * @param model {@link Score} data
         */
        public void populate(final Score model) {
            TextView moneFGHFGy1View = (TextView) this.getView().findViewById(
                    R.id.row_score_monefghfgy1);
                    

            moneFGHFGy1View.setText(String.valueOf(model.getMoneFGHFGy1()));
        }
    }
}
