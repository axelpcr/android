/**************************************************************************
 * CommentCreateFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.comment;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.jeanlui.demactor.R;
import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.entity.GroupToComment;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.widget.DateTimeWidget;
import com.jeanlui.demactor.harmony.widget.MultiEntityWidget;
import com.jeanlui.demactor.harmony.widget.SingleEntityWidget;
import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;
import com.jeanlui.demactor.provider.utils.CommentProviderUtils;
import com.jeanlui.demactor.provider.utils.UserProviderUtils;
import com.jeanlui.demactor.provider.utils.PostProviderUtils;
import com.jeanlui.demactor.provider.utils.GroupToCommentProviderUtils;

/**
 * Comment create fragment.
 *
 * This fragment gives you an interface to create a Comment.
 */
public class CommentCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Comment model = new Comment();

    /** Fields View. */
    /** id View. */
    protected EditText idView;
    /** content View. */
    protected EditText contentView;
    /** The owner chooser component. */
    protected SingleEntityWidget ownerWidget;
    /** The owner Adapter. */
    protected SingleEntityWidget.EntityAdapter<User> 
                ownerAdapter;
    /** The post chooser component. */
    protected SingleEntityWidget postWidget;
    /** The post Adapter. */
    protected SingleEntityWidget.EntityAdapter<Post> 
                postAdapter;
    /** createdAt DateTime View. */
    protected DateTimeWidget createdAtView;
    /** validate View. */
    protected CheckBox validateView;
    /** The groups chooser component. */
    protected MultiEntityWidget groupsWidget;
    /** The groups Adapter. */
    protected MultiEntityWidget.EntityAdapter<GroupToComment> 
                groupsAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.idView =
            (EditText) view.findViewById(R.id.comment_id);
        this.contentView =
            (EditText) view.findViewById(R.id.comment_content);
        this.ownerAdapter = 
                new SingleEntityWidget.EntityAdapter<User>() {
            @Override
            public String entityToString(User item) {
                return String.valueOf(item.getId());
            }
        };
        this.ownerWidget =
            (SingleEntityWidget) view.findViewById(R.id.comment_owner_button);
        this.ownerWidget.setAdapter(this.ownerAdapter);
        this.ownerWidget.setTitle(R.string.comment_owner_dialog_title);
        this.postAdapter = 
                new SingleEntityWidget.EntityAdapter<Post>() {
            @Override
            public String entityToString(Post item) {
                return String.valueOf(item.getId());
            }
        };
        this.postWidget =
            (SingleEntityWidget) view.findViewById(R.id.comment_post_button);
        this.postWidget.setAdapter(this.postAdapter);
        this.postWidget.setTitle(R.string.comment_post_dialog_title);
        this.createdAtView =
                (DateTimeWidget) view.findViewById(R.id.comment_createdat);
        this.validateView =
                (CheckBox) view.findViewById(R.id.comment_validate);
        this.groupsAdapter = 
                new MultiEntityWidget.EntityAdapter<GroupToComment>() {
            @Override
            public String entityToString(GroupToComment item) {
                return String.valueOf(item.getId());
            }
        };
        this.groupsWidget =
            (MultiEntityWidget) view.findViewById(R.id.comment_groups_button);
        this.groupsWidget.setAdapter(this.groupsAdapter);
        this.groupsWidget.setTitle(R.string.comment_groups_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        this.idView.setText(String.valueOf(this.model.getId()));
        if (this.model.getContent() != null) {
            this.contentView.setText(this.model.getContent());
        }
        if (this.model.getCreatedAt() != null) {
            this.createdAtView.setDateTime(this.model.getCreatedAt());
        }
        this.validateView.setChecked(this.model.isValidate());

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setId(Integer.parseInt(
                    this.idView.getEditableText().toString()));

        this.model.setContent(this.contentView.getEditableText().toString());

        this.model.setOwner(this.ownerAdapter.getSelectedItem());

        this.model.setPost(this.postAdapter.getSelectedItem());

        this.model.setCreatedAt(this.createdAtView.getDateTime());

        this.model.setValidate(this.validateView.isChecked());

        this.model.setGroups(this.groupsAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.idView.getText().toString().trim())) {
            error = R.string.comment_id_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.contentView.getText().toString().trim())) {
            error = R.string.comment_content_invalid_field_error;
        }
        if (this.ownerAdapter.getSelectedItem() == null) {
            error = R.string.comment_owner_invalid_field_error;
        }
        if (this.postAdapter.getSelectedItem() == null) {
            error = R.string.comment_post_invalid_field_error;
        }
        if (this.createdAtView.getDateTime() == null) {
            error = R.string.comment_createdat_invalid_field_error;
        }
        if (this.groupsAdapter.getCheckedItems().isEmpty()) {
            error = R.string.comment_groups_invalid_field_error;
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
                R.layout.fragment_comment_create,
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
        private final Comment entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final CommentCreateFragment fragment,
                final Comment entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.comment_progress_save_title),
                    this.ctx.getString(
                            R.string.comment_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new CommentProviderUtils(this.ctx).insert(
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
                                R.string.comment_error_create));
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
        private CommentCreateFragment fragment;
        /** owner list. */
        private ArrayList<User> ownerList;
        /** post list. */
        private ArrayList<Post> postList;
        /** groups list. */
        private ArrayList<GroupToComment> groupsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final CommentCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.comment_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.comment_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.ownerList = 
                new UserProviderUtils(this.ctx).queryAll();
            this.postList = 
                new PostProviderUtils(this.ctx).queryAll();
            this.groupsList = 
                new GroupToCommentProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.ownerAdapter.loadData(this.ownerList);
            this.fragment.postAdapter.loadData(this.postList);
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
