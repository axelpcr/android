/**************************************************************************
 * UserGroupListAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.usergroup;


import com.jeanlui.demactor.R;
import android.widget.CheckBox;
import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeanlui.demactor.entity.UserGroup;
import com.jeanlui.demactor.harmony.view.HarmonyCursorAdapter;
import com.jeanlui.demactor.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.UserGroupContract;

/**
 * List adapter for UserGroup entity.
 */
public class UserGroupListAdapter extends HarmonyCursorAdapter<UserGroup> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public UserGroupListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public UserGroupListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected UserGroup cursorToItem(Cursor cursor) {
        return UserGroupContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return UserGroupContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<UserGroup> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<UserGroup> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_usergroup);
        }

        /**
         * Populate row with a {@link UserGroup}.
         *
         * @param model {@link UserGroup} data
         */
        public void populate(final UserGroup model) {
            TextView idView = (TextView) this.getView().findViewById(
                    R.id.row_usergroup_id);
                    
            TextView nameView = (TextView) this.getView().findViewById(
                    R.id.row_usergroup_name);
                    
            CheckBox writePermissionView = (CheckBox) this.getView().findViewById(
                    R.id.row_usergroup_writepermission);
            writePermissionView.setEnabled(false);
            
            CheckBox deletePermissionView = (CheckBox) this.getView().findViewById(
                    R.id.row_usergroup_deletepermission);
            deletePermissionView.setEnabled(false);
            

            idView.setText(String.valueOf(model.getId()));
            if (model.getName() != null) {
                nameView.setText(model.getName());
            }
            writePermissionView.setChecked(model.isWritePermission());
            deletePermissionView.setChecked(model.isDeletePermission());
        }
    }
}
