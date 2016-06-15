/**************************************************************************
 * UserListAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.user;


import com.jeanlui.demactor.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.harmony.view.HarmonyCursorAdapter;
import com.jeanlui.demactor.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.ClientContract;
import com.jeanlui.demactor.provider.contract.UserGroupContract;
import com.jeanlui.demactor.provider.contract.UsertoUserContract;

/**
 * List adapter for User entity.
 */
public class UserListAdapter extends HarmonyCursorAdapter<User> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public UserListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public UserListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected User cursorToItem(Cursor cursor) {
        return UserContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return UserContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<User> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<User> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_user);
        }

        /**
         * Populate row with a {@link User}.
         *
         * @param model {@link User} data
         */
        public void populate(final User model) {
            TextView loginView = (TextView) this.getView().findViewById(
                    R.id.row_user_login);
                    
            TextView passwordView = (TextView) this.getView().findViewById(
                    R.id.row_user_password);
                    
            TextView firstnameView = (TextView) this.getView().findViewById(
                    R.id.row_user_firstname);
                    
            TextView lastnameView = (TextView) this.getView().findViewById(
                    R.id.row_user_lastname);
                    
            TextView createdAtView = (TextView) this.getView().findViewById(
                    R.id.row_user_createdat);
                    
            TextView birthdateView = (TextView) this.getView().findViewById(
                    R.id.row_user_birthdate);
                    
            TextView userGroupView = (TextView) this.getView().findViewById(
                    R.id.row_user_usergroup);
                    
            TextView titleView = (TextView) this.getView().findViewById(
                    R.id.row_user_title);
                    
            TextView fullNameView = (TextView) this.getView().findViewById(
                    R.id.row_user_fullname);
                    

            if (model.getLogin() != null) {
                loginView.setText(model.getLogin());
            }
            if (model.getPassword() != null) {
                passwordView.setText(model.getPassword());
            }
            if (model.getFirstname() != null) {
                firstnameView.setText(model.getFirstname());
            }
            if (model.getLastname() != null) {
                lastnameView.setText(model.getLastname());
            }
            if (model.getCreatedAt() != null) {
                createdAtView.setText(DateUtils.formatDateTimeToString(model.getCreatedAt()));
            }
            if (model.getBirthdate() != null) {
                birthdateView.setText(DateUtils.formatDateToString(model.getBirthdate(), true));
            }
            if (model.getUserGroup() != null) {
                userGroupView.setText(
                        String.valueOf(model.getUserGroup().getId()));
            }
            if (model.getTitle() != null) {
                titleView.setText(model.getTitle().name());
            }
            if (model.getFullName() != null) {
                fullNameView.setText(model.getFullName());
            }
        }
    }
}
