/**************************************************************************
 * HiddenEntityShowFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.hiddenentity;


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
import com.jeanlui.demactor.entity.HiddenEntity;
import com.jeanlui.demactor.harmony.view.DeleteDialog;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.view.MultiLoader;
import com.jeanlui.demactor.harmony.view.MultiLoader.UriLoadedCallback;
import com.jeanlui.demactor.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.jeanlui.demactor.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.jeanlui.demactor.provider.utils.HiddenEntityProviderUtils;
import com.jeanlui.demactor.provider.HiddenEntityProviderAdapter;
import com.jeanlui.demactor.provider.contract.HiddenEntityContract;

/** HiddenEntity show fragment.
 *
 * This fragment gives you an interface to show a HiddenEntity.
 * 
 * @see android.app.Fragment
 */
public class HiddenEntityShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected HiddenEntity model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** id View. */
    protected TextView idView;
    /** content View. */
    protected TextView contentView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no HiddenEntity. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.idView =
            (TextView) view.findViewById(
                    R.id.hiddenentity_id);
        this.contentView =
            (TextView) view.findViewById(
                    R.id.hiddenentity_content);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.hiddenentity_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.hiddenentity_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        this.idView.setText(String.valueOf(this.model.getId()));
        if (this.model.getContent() != null) {
            this.contentView.setText(this.model.getContent());
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
                        R.layout.fragment_hiddenentity_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((HiddenEntity) intent.getParcelableExtra(HiddenEntityContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The HiddenEntity to get the data from.
     */
    public void update(HiddenEntity item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    HiddenEntityProviderAdapter.HIDDENENTITY_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    HiddenEntityShowFragment.this.onHiddenEntityLoaded(c);
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
    public void onHiddenEntityLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            HiddenEntityContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }

    /**
     * Calls the HiddenEntityEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    HiddenEntityEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(HiddenEntityContract.PARCEL, this.model);
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
        private HiddenEntity item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build HiddenEntitySQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final HiddenEntity item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new HiddenEntityProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                HiddenEntityShowFragment.this.onPostDelete();
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

