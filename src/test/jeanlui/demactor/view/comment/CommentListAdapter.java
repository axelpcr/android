/**************************************************************************
 * CommentListAdapter.java, demactor Android
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
import android.widget.CheckBox;
import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.harmony.view.HarmonyCursorAdapter;
import com.jeanlui.demactor.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;

/**
 * List adapter for Comment entity.
 */
public class CommentListAdapter extends HarmonyCursorAdapter<Comment> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public CommentListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public CommentListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Comment cursorToItem(Cursor cursor) {
        return CommentContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return CommentContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Comment> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Comment> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_comment);
        }

        /**
         * Populate row with a {@link Comment}.
         *
         * @param model {@link Comment} data
         */
        public void populate(final Comment model) {
            TextView idView = (TextView) this.getView().findViewById(
                    R.id.row_comment_id);
                    
            TextView contentView = (TextView) this.getView().findViewById(
                    R.id.row_comment_content);
                    
            TextView ownerView = (TextView) this.getView().findViewById(
                    R.id.row_comment_owner);
                    
            TextView postView = (TextView) this.getView().findViewById(
                    R.id.row_comment_post);
                    
            TextView createdAtView = (TextView) this.getView().findViewById(
                    R.id.row_comment_createdat);
                    
            CheckBox validateView = (CheckBox) this.getView().findViewById(
                    R.id.row_comment_validate);
            validateView.setEnabled(false);
            

            idView.setText(String.valueOf(model.getId()));
            if (model.getContent() != null) {
                contentView.setText(model.getContent());
            }
            if (model.getOwner() != null) {
                ownerView.setText(
                        String.valueOf(model.getOwner().getId()));
            }
            if (model.getPost() != null) {
                postView.setText(
                        String.valueOf(model.getPost().getId()));
            }
            if (model.getCreatedAt() != null) {
                createdAtView.setText(DateUtils.formatDateTimeToString(model.getCreatedAt()));
            }
            validateView.setChecked(model.isValidate());
        }
    }
}
