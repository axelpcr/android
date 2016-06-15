/**************************************************************************
 * UserGroupShowFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.usergroup;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeanlui.demactor.R;
import com.jeanlui.demactor.entity.UserGroup;
import com.jeanlui.demactor.harmony.view.DeleteDialog;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.view.MultiLoader;
import com.jeanlui.demactor.harmony.view.MultiLoader.UriLoadedCallback;
import com.jeanlui.demactor.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.jeanlui.demactor.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.jeanlui.demactor.provider.utils.UserGroupProviderUtils;
import com.jeanlui.demactor.provider.UserGroupProviderAdapter;
import com.jeanlui.demactor.provider.contract.UserGroupContract;

/** UserGroup show fragment.
 *
 * This fragment gives you an interface to show a UserGroup.
 * 
 * @see android.app.Fragment
 */
public class UserGroupShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected UserGroup model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** id View. */
    protected TextView idView;
    /** name View. */
    protected TextView nameView;
    /** writePermission View. */
    protected CheckBox writePermissionView;
    /** deletePermission View. */
    protected CheckBox deletePermissionView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no UserGroup. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.idView =
            (TextView) view.findViewById(
                    R.id.usergroup_id);
        this.nameView =
            (TextView) view.findViewById(
                    R.id.usergroup_name);
        this.writePermissionView =
            (CheckBox) view.findViewById(
                    R.id.usergroup_writepermission);
        this.writePermissionView.setEnabled(false);
        this.deletePermissionView =
            (CheckBox) view.findViewById(
                    R.id.usergroup_deletepermission);
        this.deletePermissionView.setEnabled(false);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.usergroup_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.usergroup_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        this.idView.setText(String.valueOf(this.model.getId()));
        if (this.model.getName() != null) {
            this.nameView.setText(this.model.getName());
        }
        this.writePermissionView.setChecked(this.model.isWritePermission());
        this.deletePermissionView.setChecked(this.model.isDeletePermission());
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
                        R.layout.fragment_usergroup_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((UserGroup) intent.getParcelableExtra(UserGroupContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The UserGroup to get the data from.
     */
    public void update(UserGroup item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    UserGroupProviderAdapter.USERGROUP_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    UserGroupShowFragment.this.onUserGroupLoaded(c);
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
    public void onUserGroupLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            UserGroupContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }

    /**
     * Calls the UserGroupEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    UserGroupEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(UserGroupContract.PARCEL, this.model);
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
        private UserGroup item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build UserGroupSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final UserGroup item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new UserGroupProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                UserGroupShowFragment.this.onPostDelete();
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

