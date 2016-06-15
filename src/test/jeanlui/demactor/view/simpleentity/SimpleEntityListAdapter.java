/**************************************************************************
 * SimpleEntityListAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.simpleentity;


import com.jeanlui.demactor.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeanlui.demactor.entity.SimpleEntity;
import com.jeanlui.demactor.harmony.view.HarmonyCursorAdapter;
import com.jeanlui.demactor.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.SimpleEntityContract;

/**
 * List adapter for SimpleEntity entity.
 */
public class SimpleEntityListAdapter extends HarmonyCursorAdapter<SimpleEntity> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public SimpleEntityListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public SimpleEntityListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected SimpleEntity cursorToItem(Cursor cursor) {
        return SimpleEntityContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return SimpleEntityContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<SimpleEntity> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<SimpleEntity> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_simpleentity);
        }

        /**
         * Populate row with a {@link SimpleEntity}.
         *
         * @param model {@link SimpleEntity} data
         */
        public void populate(final SimpleEntity model) {

        }
    }
}
