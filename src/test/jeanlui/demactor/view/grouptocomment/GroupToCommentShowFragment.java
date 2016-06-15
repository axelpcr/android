/**************************************************************************
 * GroupToCommentShowFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.grouptocomment;


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
import com.jeanlui.demactor.entity.GroupToComment;
import com.jeanlui.demactor.harmony.view.DeleteDialog;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.view.MultiLoader;
import com.jeanlui.demactor.harmony.view.MultiLoader.UriLoadedCallback;
import com.jeanlui.demactor.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.jeanlui.demactor.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.jeanlui.demactor.provider.utils.GroupToCommentProviderUtils;
import com.jeanlui.demactor.provider.GroupToCommentProviderAdapter;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.GroupContract;

/** GroupToComment show fragment.
 *
 * This fragment gives you an interface to show a GroupToComment.
 * 
 * @see android.app.Fragment
 */
public class GroupToCommentShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected GroupToComment model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** id View. */
    protected TextView idView;
    /** displayName View. */
    protected TextView displayNameView;
    /** group View. */
    protected TextView groupView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no GroupToComment. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.idView =
            (TextView) view.findViewById(
                    R.id.grouptocomment_id);
        this.displayNameView =
            (TextView) view.findViewById(
                    R.id.grouptocomment_displayname);
        this.groupView =
            (TextView) view.findViewById(
                    R.id.grouptocomment_group);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.grouptocomment_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.grouptocomment_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        this.idView.setText(String.valueOf(this.model.getId()));
        if (this.model.getDisplayName() != null) {
            this.displayNameView.setText(this.model.getDisplayName());
        }
        if (this.model.getGroup() != null) {
            this.groupView.setText(
                    String.valueOf(this.model.getGroup().getId()));
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
                        R.layout.fragment_grouptocomment_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((GroupToComment) intent.getParcelableExtra(GroupToCommentContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The GroupToComment to get the data from.
     */
    public void update(GroupToComment item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    GroupToCommentShowFragment.this.onGroupToCommentLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/group"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    GroupToCommentShowFragment.this.onGroupLoaded(c);
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
    public void onGroupToCommentLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            GroupToCommentContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onGroupLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setGroup(GroupContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setGroup(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the GroupToCommentEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    GroupToCommentEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(GroupToCommentContract.PARCEL, this.model);
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
        private GroupToComment item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build GroupToCommentSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final GroupToComment item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new GroupToCommentProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                GroupToCommentShowFragment.this.onPostDelete();
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

