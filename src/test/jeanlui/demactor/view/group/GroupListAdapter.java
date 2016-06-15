/**************************************************************************
 * GroupListAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.group;


import com.jeanlui.demactor.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeanlui.demactor.entity.Group;
import com.jeanlui.demactor.harmony.view.HarmonyCursorAdapter;
import com.jeanlui.demactor.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.GroupContract;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;

/**
 * List adapter for Group entity.
 */
public class GroupListAdapter extends HarmonyCursorAdapter<Group> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public GroupListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public GroupListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Group cursorToItem(Cursor cursor) {
        return GroupContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return GroupContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Group> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Group> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_group);
        }

        /**
         * Populate row with a {@link Group}.
         *
         * @param model {@link Group} data
         */
        public void populate(final Group model) {
            TextView idView = (TextView) this.getView().findViewById(
                    R.id.row_group_id);
                    
            TextView nameView = (TextView) this.getView().findViewById(
                    R.id.row_group_name);
                    

            idView.setText(String.valueOf(model.getId()));
            if (model.getName() != null) {
                nameView.setText(model.getName());
            }
        }
    }
}
