/**************************************************************************
 * PostListAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.post;


import com.jeanlui.demactor.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.harmony.view.HarmonyCursorAdapter;
import com.jeanlui.demactor.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.PosttoGroupContract;
import com.jeanlui.demactor.provider.contract.GroupContract;

/**
 * List adapter for Post entity.
 */
public class PostListAdapter extends HarmonyCursorAdapter<Post> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PostListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PostListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Post cursorToItem(Cursor cursor) {
        return PostContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PostContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Post> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Post> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_post);
        }

        /**
         * Populate row with a {@link Post}.
         *
         * @param model {@link Post} data
         */
        public void populate(final Post model) {
            TextView titleView = (TextView) this.getView().findViewById(
                    R.id.row_post_title);
                    
            TextView contentView = (TextView) this.getView().findViewById(
                    R.id.row_post_content);
                    
            TextView ownerView = (TextView) this.getView().findViewById(
                    R.id.row_post_owner);
                    
            TextView createdAtView = (TextView) this.getView().findViewById(
                    R.id.row_post_createdat);
                    
            TextView updatedAtView = (TextView) this.getView().findViewById(
                    R.id.row_post_updatedat);
                    
            TextView expiresAtView = (TextView) this.getView().findViewById(
                    R.id.row_post_expiresat);
                    

            if (model.getTitle() != null) {
                titleView.setText(model.getTitle());
            }
            if (model.getContent() != null) {
                contentView.setText(model.getContent());
            }
            if (model.getOwner() != null) {
                ownerView.setText(
                        String.valueOf(model.getOwner().getId()));
            }
            if (model.getCreatedAt() != null) {
                createdAtView.setText(DateUtils.formatDateTimeToString(model.getCreatedAt()));
            }
            if (model.getUpdatedAt() != null) {
                updatedAtView.setText(DateUtils.formatDateTimeToString(model.getUpdatedAt()));
            }
            if (model.getExpiresAt() != null) {
                expiresAtView.setText(DateUtils.formatDateTimeToString(model.getExpiresAt()));
            }
        }
    }
}
