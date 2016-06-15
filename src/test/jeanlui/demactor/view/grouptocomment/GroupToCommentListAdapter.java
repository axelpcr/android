/**************************************************************************
 * GroupToCommentListAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.grouptocomment;


import com.jeanlui.demactor.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeanlui.demactor.entity.GroupToComment;
import com.jeanlui.demactor.harmony.view.HarmonyCursorAdapter;
import com.jeanlui.demactor.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.GroupContract;

/**
 * List adapter for GroupToComment entity.
 */
public class GroupToCommentListAdapter extends HarmonyCursorAdapter<GroupToComment> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public GroupToCommentListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public GroupToCommentListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected GroupToComment cursorToItem(Cursor cursor) {
        return GroupToCommentContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return GroupToCommentContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<GroupToComment> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<GroupToComment> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_grouptocomment);
        }

        /**
         * Populate row with a {@link GroupToComment}.
         *
         * @param model {@link GroupToComment} data
         */
        public void populate(final GroupToComment model) {
            TextView idView = (TextView) this.getView().findViewById(
                    R.id.row_grouptocomment_id);
                    
            TextView displayNameView = (TextView) this.getView().findViewById(
                    R.id.row_grouptocomment_displayname);
                    
            TextView groupView = (TextView) this.getView().findViewById(
                    R.id.row_grouptocomment_group);
                    

            idView.setText(String.valueOf(model.getId()));
            if (model.getDisplayName() != null) {
                displayNameView.setText(model.getDisplayName());
            }
            if (model.getGroup() != null) {
                groupView.setText(
                        String.valueOf(model.getGroup().getId()));
            }
        }
    }
}
