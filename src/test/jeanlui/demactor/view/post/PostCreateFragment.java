/**************************************************************************
 * PostCreateFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.post;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.jeanlui.demactor.R;
import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.entity.Group;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.widget.DateTimeWidget;
import com.jeanlui.demactor.harmony.widget.MultiEntityWidget;
import com.jeanlui.demactor.harmony.widget.SingleEntityWidget;
import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;
import com.jeanlui.demactor.provider.utils.PostProviderUtils;
import com.jeanlui.demactor.provider.utils.UserProviderUtils;
import com.jeanlui.demactor.provider.utils.CommentProviderUtils;
import com.jeanlui.demactor.provider.utils.GroupProviderUtils;

/**
 * Post create fragment.
 *
 * This fragment gives you an interface to create a Post.
 */
public class PostCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Post model = new Post();

    /** Fields View. */
    /** title View. */
    protected EditText titleView;
    /** content View. */
    protected EditText contentView;
    /** The owner chooser component. */
    protected SingleEntityWidget ownerWidget;
    /** The owner Adapter. */
    protected SingleEntityWidget.EntityAdapter<User> 
                ownerAdapter;
    /** The comments chooser component. */
    protected MultiEntityWidget commentsWidget;
    /** The comments Adapter. */
    protected MultiEntityWidget.EntityAdapter<Comment> 
                commentsAdapter;
    /** The groups chooser component. */
    protected MultiEntityWidget groupsWidget;
    /** The groups Adapter. */
    protected MultiEntityWidget.EntityAdapter<Group> 
                groupsAdapter;
    /** createdAt DateTime View. */
    protected DateTimeWidget createdAtView;
    /** updatedAt DateTime View. */
    protected DateTimeWidget updatedAtView;
    /** expiresAt DateTime View. */
    protected DateTimeWidget expiresAtView;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.titleView =
            (EditText) view.findViewById(R.id.post_title);
        this.contentView =
            (EditText) view.findViewById(R.id.post_content);
        this.ownerAdapter = 
                new SingleEntityWidget.EntityAdapter<User>() {
            @Override
            public String entityToString(User item) {
                return String.valueOf(item.getId());
            }
        };
        this.ownerWidget =
            (SingleEntityWidget) view.findViewById(R.id.post_owner_button);
        this.ownerWidget.setAdapter(this.ownerAdapter);
        this.ownerWidget.setTitle(R.string.post_owner_dialog_title);
        this.commentsAdapter = 
                new MultiEntityWidget.EntityAdapter<Comment>() {
            @Override
            public String entityToString(Comment item) {
                return String.valueOf(item.getId());
            }
        };
        this.commentsWidget =
            (MultiEntityWidget) view.findViewById(R.id.post_comments_button);
        this.commentsWidget.setAdapter(this.commentsAdapter);
        this.commentsWidget.setTitle(R.string.post_comments_dialog_title);
        this.groupsAdapter = 
                new MultiEntityWidget.EntityAdapter<Group>() {
            @Override
            public String entityToString(Group item) {
                return String.valueOf(item.getId());
            }
        };
        this.groupsWidget =
            (MultiEntityWidget) view.findViewById(R.id.post_groups_button);
        this.groupsWidget.setAdapter(this.groupsAdapter);
        this.groupsWidget.setTitle(R.string.post_groups_dialog_title);
        this.createdAtView =
                (DateTimeWidget) view.findViewById(R.id.post_createdat);
        this.updatedAtView =
                (DateTimeWidget) view.findViewById(R.id.post_updatedat);
        this.expiresAtView =
                (DateTimeWidget) view.findViewById(R.id.post_expiresat);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getTitle() != null) {
            this.titleView.setText(this.model.getTitle());
        }
        if (this.model.getContent() != null) {
            this.contentView.setText(this.model.getContent());
        }
        if (this.model.getCreatedAt() != null) {
            this.createdAtView.setDateTime(this.model.getCreatedAt());
        }
        if (this.model.getUpdatedAt() != null) {
            this.updatedAtView.setDateTime(this.model.getUpdatedAt());
        }
        if (this.model.getExpiresAt() != null) {
            this.expiresAtView.setDateTime(this.model.getExpiresAt());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setTitle(this.titleView.getEditableText().toString());

        this.model.setContent(this.contentView.getEditableText().toString());

        this.model.setOwner(this.ownerAdapter.getSelectedItem());

        this.model.setComments(this.commentsAdapter.getCheckedItems());

        this.model.setGroups(this.groupsAdapter.getCheckedItems());

        this.model.setCreatedAt(this.createdAtView.getDateTime());

        this.model.setUpdatedAt(this.updatedAtView.getDateTime());

        this.model.setExpiresAt(this.expiresAtView.getDateTime());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.titleView.getText().toString().trim())) {
            error = R.string.post_title_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.contentView.getText().toString().trim())) {
            error = R.string.post_content_invalid_field_error;
        }
        if (this.ownerAdapter.getSelectedItem() == null) {
            error = R.string.post_owner_invalid_field_error;
        }
        if (this.commentsAdapter.getCheckedItems().isEmpty()) {
            error = R.string.post_comments_invalid_field_error;
        }
        if (this.groupsAdapter.getCheckedItems().isEmpty()) {
            error = R.string.post_groups_invalid_field_error;
        }
        if (this.createdAtView.getDateTime() == null) {
            error = R.string.post_createdat_invalid_field_error;
        }
        if (this.updatedAtView.getDateTime() == null) {
            error = R.string.post_updatedat_invalid_field_error;
        }
        if (this.expiresAtView.getDateTime() == null) {
            error = R.string.post_expiresat_invalid_field_error;
        }
    
        if (error > 0) {
            Toast.makeText(this.getActivity(),
                this.getActivity().getString(error),
                Toast.LENGTH_SHORT).show();
        }
        return error == 0;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(
                R.layout.fragment_post_create,
                container,
                false);

        this.initializeComponent(view);
        this.loadData();
        return view;
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class CreateTask extends AsyncTask<Void, Void, Uri> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to persist. */
        private final Post entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final PostCreateFragment fragment,
                final Post entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.post_progress_save_title),
                    this.ctx.getString(
                            R.string.post_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new PostProviderUtils(this.ctx).insert(
                        this.entity);

            return result;
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (result != null) {
                final HarmonyFragmentActivity activity =
                                         (HarmonyFragmentActivity) this.ctx;
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.post_error_create));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int which) {

                            }
                        });
                builder.show();
            }

            this.progress.dismiss();
        }
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class LoadTask extends AsyncTask<Void, Void, Void> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Progress Dialog. */
        private ProgressDialog progress;
        /** Fragment. */
        private PostCreateFragment fragment;
        /** owner list. */
        private ArrayList<User> ownerList;
        /** comments list. */
        private ArrayList<Comment> commentsList;
        /** groups list. */
        private ArrayList<Group> groupsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PostCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.post_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.post_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.ownerList = 
                new UserProviderUtils(this.ctx).queryAll();
            this.commentsList = 
                new CommentProviderUtils(this.ctx).queryAll();
            this.groupsList = 
                new GroupProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.ownerAdapter.loadData(this.ownerList);
            this.fragment.commentsAdapter.loadData(this.commentsList);
            this.fragment.groupsAdapter.loadData(this.groupsList);
            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new CreateTask(this, this.model).execute();
        }
    }
}
