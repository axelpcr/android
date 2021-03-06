/**************************************************************************
 * CommentShowFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.comment;


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
import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.entity.GroupToComment;
import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.harmony.view.DeleteDialog;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.view.MultiLoader;
import com.jeanlui.demactor.harmony.view.MultiLoader.UriLoadedCallback;
import com.jeanlui.demactor.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.jeanlui.demactor.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.jeanlui.demactor.provider.utils.CommentProviderUtils;
import com.jeanlui.demactor.provider.CommentProviderAdapter;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;

/** Comment show fragment.
 *
 * This fragment gives you an interface to show a Comment.
 * 
 * @see android.app.Fragment
 */
public class CommentShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Comment model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** id View. */
    protected TextView idView;
    /** content View. */
    protected TextView contentView;
    /** owner View. */
    protected TextView ownerView;
    /** post View. */
    protected TextView postView;
    /** createdAt View. */
    protected TextView createdAtView;
    /** validate View. */
    protected CheckBox validateView;
    /** groups View. */
    protected TextView groupsView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Comment. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.idView =
            (TextView) view.findViewById(
                    R.id.comment_id);
        this.contentView =
            (TextView) view.findViewById(
                    R.id.comment_content);
        this.ownerView =
            (TextView) view.findViewById(
                    R.id.comment_owner);
        this.postView =
            (TextView) view.findViewById(
                    R.id.comment_post);
        this.createdAtView =
            (TextView) view.findViewById(
                    R.id.comment_createdat);
        this.validateView =
            (CheckBox) view.findViewById(
                    R.id.comment_validate);
        this.validateView.setEnabled(false);
        this.groupsView =
            (TextView) view.findViewById(
                    R.id.comment_groups);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.comment_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.comment_empty);
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
        if (this.model.getOwner() != null) {
            this.ownerView.setText(
                    String.valueOf(this.model.getOwner().getId()));
        }
        if (this.model.getPost() != null) {
            this.postView.setText(
                    String.valueOf(this.model.getPost().getId()));
        }
        if (this.model.getCreatedAt() != null) {
            this.createdAtView.setText(
                    DateUtils.formatDateTimeToString(
                            this.model.getCreatedAt()));
        }
        this.validateView.setChecked(this.model.isValidate());
        if (this.model.getGroups() != null) {
            String groupsValue = "";
            for (GroupToComment item : this.model.getGroups()) {
                groupsValue += item.getId() + ",";
            }
            this.groupsView.setText(groupsValue);
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
                        R.layout.fragment_comment_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Comment) intent.getParcelableExtra(CommentContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Comment to get the data from.
     */
    public void update(Comment item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    CommentProviderAdapter.COMMENT_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CommentShowFragment.this.onCommentLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/owner"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CommentShowFragment.this.onOwnerLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/post"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CommentShowFragment.this.onPostLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/groups"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    CommentShowFragment.this.onGroupsLoaded(c);
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
    public void onCommentLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            CommentContract.cursorToItem(
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
    public void onOwnerLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setOwner(UserContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setOwner(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onPostLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setPost(PostContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setPost(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onGroupsLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setGroups(GroupToCommentContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setGroups(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the CommentEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    CommentEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(CommentContract.PARCEL, this.model);
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
        private Comment item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build CommentSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Comment item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new CommentProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                CommentShowFragment.this.onPostDelete();
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

