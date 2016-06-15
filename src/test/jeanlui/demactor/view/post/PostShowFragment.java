/**************************************************************************
 * PostShowFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.post;


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
import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.entity.Group;
import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.harmony.view.DeleteDialog;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.view.MultiLoader;
import com.jeanlui.demactor.harmony.view.MultiLoader.UriLoadedCallback;
import com.jeanlui.demactor.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.jeanlui.demactor.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.jeanlui.demactor.provider.utils.PostProviderUtils;
import com.jeanlui.demactor.provider.PostProviderAdapter;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.GroupContract;

/** Post show fragment.
 *
 * This fragment gives you an interface to show a Post.
 * 
 * @see android.app.Fragment
 */
public class PostShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Post model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** title View. */
    protected TextView titleView;
    /** content View. */
    protected TextView contentView;
    /** owner View. */
    protected TextView ownerView;
    /** comments View. */
    protected TextView commentsView;
    /** groups View. */
    protected TextView groupsView;
    /** createdAt View. */
    protected TextView createdAtView;
    /** updatedAt View. */
    protected TextView updatedAtView;
    /** expiresAt View. */
    protected TextView expiresAtView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Post. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.titleView =
            (TextView) view.findViewById(
                    R.id.post_title);
        this.contentView =
            (TextView) view.findViewById(
                    R.id.post_content);
        this.ownerView =
            (TextView) view.findViewById(
                    R.id.post_owner);
        this.commentsView =
            (TextView) view.findViewById(
                    R.id.post_comments);
        this.groupsView =
            (TextView) view.findViewById(
                    R.id.post_groups);
        this.createdAtView =
            (TextView) view.findViewById(
                    R.id.post_createdat);
        this.updatedAtView =
            (TextView) view.findViewById(
                    R.id.post_updatedat);
        this.expiresAtView =
            (TextView) view.findViewById(
                    R.id.post_expiresat);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.post_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.post_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getTitle() != null) {
            this.titleView.setText(this.model.getTitle());
        }
        if (this.model.getContent() != null) {
            this.contentView.setText(this.model.getContent());
        }
        if (this.model.getOwner() != null) {
            this.ownerView.setText(
                    String.valueOf(this.model.getOwner().getId()));
        }
        if (this.model.getComments() != null) {
            String commentsValue = "";
            for (Comment item : this.model.getComments()) {
                commentsValue += item.getId() + ",";
            }
            this.commentsView.setText(commentsValue);
        }
        if (this.model.getGroups() != null) {
            String groupsValue = "";
            for (Group item : this.model.getGroups()) {
                groupsValue += item.getId() + ",";
            }
            this.groupsView.setText(groupsValue);
        }
        if (this.model.getCreatedAt() != null) {
            this.createdAtView.setText(
                    DateUtils.formatDateTimeToString(
                            this.model.getCreatedAt()));
        }
        if (this.model.getUpdatedAt() != null) {
            this.updatedAtView.setText(
                    DateUtils.formatDateTimeToString(
                            this.model.getUpdatedAt()));
        }
        if (this.model.getExpiresAt() != null) {
            this.expiresAtView.setText(
                    DateUtils.formatDateTimeToString(
                            this.model.getExpiresAt()));
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
                        R.layout.fragment_post_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Post) intent.getParcelableExtra(PostContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Post to get the data from.
     */
    public void update(Post item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PostProviderAdapter.POST_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PostShowFragment.this.onPostLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/owner"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PostShowFragment.this.onOwnerLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/comments"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PostShowFragment.this.onCommentsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/groups"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PostShowFragment.this.onGroupsLoaded(c);
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
    public void onPostLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PostContract.cursorToItem(
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
    public void onCommentsLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setComments(CommentContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setComments(null);
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
            this.model.setGroups(GroupContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setGroups(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PostEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PostEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PostContract.PARCEL, this.model);
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
        private Post item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PostSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Post item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PostProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PostShowFragment.this.onPostDelete();
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

