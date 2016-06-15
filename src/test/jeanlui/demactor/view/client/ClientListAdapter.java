/**************************************************************************
 * ClientListAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.client;


import com.jeanlui.demactor.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.entity.Client;
import com.jeanlui.demactor.harmony.view.HarmonyCursorAdapter;
import com.jeanlui.demactor.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.ClientContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.UserGroupContract;
import com.jeanlui.demactor.provider.contract.UsertoUserContract;

/**
 * List adapter for Client entity.
 */
public class ClientListAdapter extends HarmonyCursorAdapter<Client> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public ClientListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public ClientListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Client cursorToItem(Cursor cursor) {
        return ClientContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return UserContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Client> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Client> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_client);
        }

        /**
         * Populate row with a {@link Client}.
         *
         * @param model {@link Client} data
         */
        public void populate(final Client model) {
            TextView adressView = (TextView) this.getView().findViewById(
                    R.id.row_client_adress);
                    
            TextView loginView = (TextView) this.getView().findViewById(
                    R.id.row_client_login);
                    
            TextView passwordView = (TextView) this.getView().findViewById(
                    R.id.row_client_password);
                    
            TextView firstnameView = (TextView) this.getView().findViewById(
                    R.id.row_client_firstname);
                    
            TextView lastnameView = (TextView) this.getView().findViewById(
                    R.id.row_client_lastname);
                    
            TextView createdAtView = (TextView) this.getView().findViewById(
                    R.id.row_client_createdat);
                    
            TextView birthdateView = (TextView) this.getView().findViewById(
                    R.id.row_client_birthdate);
                    
            TextView userGroupView = (TextView) this.getView().findViewById(
                    R.id.row_client_usergroup);
                    
            TextView titleView = (TextView) this.getView().findViewById(
                    R.id.row_client_title);
                    
            TextView fullNameView = (TextView) this.getView().findViewById(
                    R.id.row_client_fullname);
                    

            if (model.getAdress() != null) {
                adressView.setText(model.getAdress());
            }
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
