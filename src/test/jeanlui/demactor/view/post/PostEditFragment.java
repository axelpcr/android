/**************************************************************************
 * PostEditFragment.java, demactor Android
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
import android.content.Intent;
import android.database.sqlite.SQLiteException;
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
import com.jeanlui.demactor.provider.PostProviderAdapter;
import com.jeanlui.demactor.provider.utils.PostProviderUtils;
import com.jeanlui.demactor.provider.utils.UserProviderUtils;
import com.jeanlui.demactor.provider.utils.CommentProviderUtils;
import com.jeanlui.demactor.provider.utils.GroupProviderUtils;
import com.jeanlui.demactor.data.GroupSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.GroupContract;

/** Post create fragment.
 *
 * This fragment gives you an interface to edit a Post.
 *
 * @see android.app.Fragment
 */
public class PostEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Post model = new Post();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.titleView = (EditText) view.findViewById(
                R.id.post_title);
        this.contentView = (EditText) view.findViewById(
                R.id.post_content);
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
        this.commentsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.post_comments_button);
        this.commentsWidget.setAdapter(this.commentsAdapter);
        this.commentsWidget.setTitle(R.string.post_comments_dialog_title);
        this.groupsAdapter =
                new MultiEntityWidget.EntityAdapter<Group>() {
            @Override
            public String entityToString(Group item) {
                return String.valueOf(item.getId());
            }
        };
        this.groupsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.post_groups_button);
        this.groupsWidget.setAdapter(this.groupsAdapter);
        this.groupsWidget.setTitle(R.string.post_groups_dialog_title);
        this.createdAtView = (DateTimeWidget) view.findViewById(
                R.id.post_createdat);
        this.updatedAtView = (DateTimeWidget) view.findViewById(
                R.id.post_updatedat);
        this.expiresAtView = (DateTimeWidget) view.findViewById(
                R.id.post_expiresat);
    }

    /** Load data from model to curr.fields view. */
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

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_post_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Post) intent.getParcelableExtra(
                PostContract.PARCEL);

        this.initializeComponent(view);
        this.loadData();

        return view;
    }

    /**
     * This class will update the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class EditTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final Post entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PostEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PostProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PostEditFragment", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result > 0) {
                final HarmonyFragmentActivity activity =
                        (HarmonyFragmentActivity) this.ctx;
                activity.setResult(HarmonyFragmentActivity.RESULT_OK);
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(this.ctx.getString(
                        R.string.post_error_edit));
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
        private PostEditFragment fragment;
        /** owner list. */
        private ArrayList<User> ownerList;
        /** comments list. */
        private ArrayList<Comment> commentsList;
        /** groups list. */
        private ArrayList<Group> groupsList;
    /** groups list. */
        private ArrayList<Group> associatedGroupsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PostEditFragment fragment) {
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
            Uri groupsUri = PostProviderAdapter.POST_URI;
            groupsUri = Uri.withAppendedPath(groupsUri, 
                                    String.valueOf(this.fragment.model.getId()));
            groupsUri = Uri.withAppendedPath(groupsUri, "groups");
            android.database.Cursor groupsCursor = 
                    this.ctx.getContentResolver().query(
                            groupsUri,
                            new String[]{GroupContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedGroupsList = new ArrayList<Group>();
            if (groupsCursor != null && groupsCursor.getCount() > 0) {
                while (groupsCursor.moveToNext()) {
                    int groupsId = groupsCursor.getInt(
                            groupsCursor.getColumnIndex(GroupContract.COL_ID));
                    for (Group groups : this.groupsList) {
                        if (groups.getId() ==  groupsId) {
                            this.associatedGroupsList.add(groups);
                        }
                    }
                }
                groupsCursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onOwnerLoaded(this.ownerList);
            this.fragment.onCommentsLoaded(this.commentsList);
            this.fragment.model.setGroups(this.associatedGroupsList);
            this.fragment.onGroupsLoaded(this.groupsList);

            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new EditTask(this, this.model).execute();
        }
    }

    /**
     * Called when owner have been loaded.
     * @param items The loaded items
     */
    protected void onOwnerLoaded(ArrayList<User> items) {
        this.ownerAdapter.loadData(items);
        
        if (this.model.getOwner() != null) {
            for (User item : items) {
                if (item.getId() == this.model.getOwner().getId()) {
                    this.ownerAdapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when comments have been loaded.
     * @param items The loaded items
     */
    protected void onCommentsLoaded(ArrayList<Comment> items) {
        this.commentsAdapter.loadData(items);
        ArrayList<Comment> modelItems = new ArrayList<Comment>();
        for (Comment item : items) {
            if (item.getPost() != null && item.getPost().getId() == this.model.getId()) {
                modelItems.add(item);
                this.commentsAdapter.checkItem(item, true);
            }
        }
        this.model.setComments(modelItems);
    }
    /**
     * Called when groups have been loaded.
     * @param items The loaded items
     */
    protected void onGroupsLoaded(ArrayList<Group> items) {
        this.groupsAdapter.loadData(items);
        this.groupsAdapter.setCheckedItems(this.model.getGroups());
    }
}
