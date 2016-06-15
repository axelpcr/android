/**************************************************************************
 * ClientShowFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.client;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeanlui.demactor.R;
import com.jeanlui.demactor.entity.Client;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.harmony.view.DeleteDialog;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.view.MultiLoader;
import com.jeanlui.demactor.harmony.view.MultiLoader.UriLoadedCallback;
import com.jeanlui.demactor.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.jeanlui.demactor.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.jeanlui.demactor.provider.utils.ClientProviderUtils;
import com.jeanlui.demactor.provider.ClientProviderAdapter;
import com.jeanlui.demactor.provider.contract.ClientContract;

/** Client show fragment.
 *
 * This fragment gives you an interface to show a Client.
 * 
 * @see android.app.Fragment
 */
public class ClientShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Client model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** adress View. */
    protected TextView adressView;
    /** login View. */
    protected TextView loginView;
    /** password View. */
    protected TextView passwordView;
    /** firstname View. */
    protected TextView firstnameView;
    /** lastname View. */
    protected TextView lastnameView;
    /** createdAt View. */
    protected TextView createdAtView;
    /** birthdate View. */
    protected TextView birthdateView;
    /** userGroup View. */
    protected TextView userGroupView;
    /** title View. */
    protected TextView titleView;
    /** fullName View. */
    protected TextView fullNameView;
    /** friends View. */
    protected TextView friendsView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Client. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.adressView =
            (TextView) view.findViewById(
                    R.id.client_adress);
        this.loginView =
            (TextView) view.findViewById(
                    R.id.client_login);
        this.passwordView =
            (TextView) view.findViewById(
                    R.id.client_password);
        this.firstnameView =
            (TextView) view.findViewById(
                    R.id.client_firstname);
        this.lastnameView =
            (TextView) view.findViewById(
                    R.id.client_lastname);
        this.createdAtView =
            (TextView) view.findViewById(
                    R.id.client_createdat);
        this.birthdateView =
            (TextView) view.findViewById(
                    R.id.client_birthdate);
        this.userGroupView =
            (TextView) view.findViewById(
                    R.id.client_usergroup);
        this.titleView =
            (TextView) view.findViewById(
                    R.id.client_title);
        this.fullNameView =
            (TextView) view.findViewById(
                    R.id.client_fullname);
        this.friendsView =
            (TextView) view.findViewById(
                    R.id.client_friends);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.client_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.client_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getAdress() != null) {
            this.adressView.setText(this.model.getAdress());
        }
        if (this.model.getLogin() != null) {
            this.loginView.setText(this.model.getLogin());
        }
        if (this.model.getPassword() != null) {
            this.passwordView.setText(this.model.getPassword());
        }
        if (this.model.getFirstname() != null) {
            this.firstnameView.setText(this.model.getFirstname());
        }
        if (this.model.getLastname() != null) {
            this.lastnameView.setText(this.model.getLastname());
        }
        if (this.model.getCreatedAt() != null) {
            this.createdAtView.setText(
                    DateUtils.formatDateTimeToString(
                            this.model.getCreatedAt()));
        }
        if (this.model.getBirthdate() != null) {
            this.birthdateView.setText(
                    DateUtils.formatDateToString(
                            this.model.getBirthdate()));
        }
        if (this.model.getUserGroup() != null) {
            this.userGroupView.setText(
                    String.valueOf(this.model.getUserGroup().getId()));
        }
        if (this.model.getTitle() != null) {
            this.titleView.setText(this.model.getTitle().toString());
        }
        if (this.model.getFullName() != null) {
            this.fullNameView.setText(this.model.getFullName());
        }
        if (this.model.getFriends() != null) {
            String friendsValue = "";
            for (User item : this.model.getFriends()) {
                friendsValue += item.getId() + ",";
            }
            this.friendsView.setText(friendsValue);
        }
        } else {
            this.dataLayout.setVisibility(View.GONE);
            this.emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(
                        R.layout.fragment_client_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Client) intent.getParcelableExtra(ClientContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Client to get the data from.
     */
    public void update(Client item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    ClientProviderAdapter.CLIENT_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ClientShowFragment.this.onClientLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.init();
        }
    }

    /**
     * Called when the entity has been loaded.
     * 
     * @param c The cursor of this entity
     */
    public void onClientLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            ClientContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }

    /**
     * Calls the ClientEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    ClientEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(ClientContract.PARCEL, this.model);
        intent.putExtras(extras);

        this.getActivity().startActivity(intent);
    }
    /**
     * Shows a confirmation dialog.
     */
    @Override
    public void onClickDelete() {
        new DeleteDialog(this.getActivity(), this).show();
    }

    @Override
    public void onDeleteDialogClose(boolean ok) {
        if (ok) {
            new DeleteTask(this.getActivity(), this.model).execute();
        }
    }
    
    /** 
     * Called when delete task is done.
     */    
    public void onPostDelete() {
        if (this.deleteCallback != null) {
            this.deleteCallback.onItemDeleted();
        }
    }

    /**
     * This class will remove the entity into the DB.
     * It runs asynchronously.
     */
    private class DeleteTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private android.content.Context ctx;
        /** Entity to delete. */
        private Client item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build ClientSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Client item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new ClientProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                ClientShowFragment.this.onPostDelete();
            }
            super.onPostExecute(result);
        }
        
        

    }

    /**
     * Callback for item deletion.
     */ 
    public interface DeleteCallback {
        /** Called when current item has been deleted. */
        void onItemDeleted();
    }
}

