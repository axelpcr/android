/**************************************************************************
 * SimpleEntityShowFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.simpleentity;


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
import com.jeanlui.demactor.entity.SimpleEntity;
import com.jeanlui.demactor.harmony.view.DeleteDialog;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.view.MultiLoader;
import com.jeanlui.demactor.harmony.view.MultiLoader.UriLoadedCallback;
import com.jeanlui.demactor.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.jeanlui.demactor.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.jeanlui.demactor.provider.utils.SimpleEntityProviderUtils;
import com.jeanlui.demactor.provider.SimpleEntityProviderAdapter;
import com.jeanlui.demactor.provider.contract.SimpleEntityContract;

/** SimpleEntity show fragment.
 *
 * This fragment gives you an interface to show a SimpleEntity.
 * 
 * @see android.app.Fragment
 */
public class SimpleEntityShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected SimpleEntity model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no SimpleEntity. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.simpleentity_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.simpleentity_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


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
                        R.layout.fragment_simpleentity_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((SimpleEntity) intent.getParcelableExtra(SimpleEntityContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The SimpleEntity to get the data from.
     */
    public void update(SimpleEntity item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    SimpleEntityProviderAdapter.SIMPLEENTITY_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    SimpleEntityShowFragment.this.onSimpleEntityLoaded(c);
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
    public void onSimpleEntityLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            SimpleEntityContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }

    /**
     * Calls the SimpleEntityEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    SimpleEntityEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(SimpleEntityContract.PARCEL, this.model);
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
        private SimpleEntity item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build SimpleEntitySQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final SimpleEntity item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new SimpleEntityProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                SimpleEntityShowFragment.this.onPostDelete();
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

