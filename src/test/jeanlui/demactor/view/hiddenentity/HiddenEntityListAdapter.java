/**************************************************************************
 * HiddenEntityListAdapter.java, demactor Android
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

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeanlui.demactor.entity.HiddenEntity;
import com.jeanlui.demactor.harmony.view.HarmonyCursorAdapter;
import com.jeanlui.demactor.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.HiddenEntityContract;

/**
 * List adapter for HiddenEntity entity.
 */
public class HiddenEntityListAdapter extends HarmonyCursorAdapter<HiddenEntity> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public HiddenEntityListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public HiddenEntityListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected HiddenEntity cursorToItem(Cursor cursor) {
        return HiddenEntityContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return HiddenEntityContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<HiddenEntity> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<HiddenEntity> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_hiddenentity);
        }

        /**
         * Populate row with a {@link HiddenEntity}.
         *
         * @param model {@link HiddenEntity} data
         */
        public void populate(final HiddenEntity model) {
            TextView idView = (TextView) this.getView().findViewById(
                    R.id.row_hiddenentity_id);
                    
            TextView contentView = (TextView) this.getView().findViewById(
                    R.id.row_hiddenentity_content);
                    

            idView.setText(String.valueOf(model.getId()));
            if (model.getContent() != null) {
                contentView.setText(model.getContent());
            }
        }
    }
}
