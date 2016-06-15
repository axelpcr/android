/**************************************************************************
 * CommentEditFragment.java, demactor Android
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
import android.content.Intent;
import android.database.sqlite.SQLiteException;
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
import com.jeanlui.demactor.provider.CommentProviderAdapter;
import com.jeanlui.demactor.provider.utils.CommentProviderUtils;
import com.jeanlui.demactor.provider.utils.UserProviderUtils;
import com.jeanlui.demactor.provider.utils.PostProviderUtils;
import com.jeanlui.demactor.provider.utils.GroupToCommentProviderUtils;
import com.jeanlui.demactor.data.GroupToCommentSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;

/** Comment create fragment.
 *
 * This fragment gives you an interface to edit a Comment.
 *
 * @see android.app.Fragment
 */
public class CommentEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Comment model = new Comment();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.idView = (EditText) view.findViewById(
                R.id.comment_id);
        this.contentView = (EditText) view.findViewById(
                R.id.comment_content);
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
        this.createdAtView = (DateTimeWidget) view.findViewById(
                R.id.comment_createdat);
        this.validateView = (CheckBox) view.findViewById(
                R.id.comment_validate);
        this.groupsAdapter =
                new MultiEntityWidget.EntityAdapter<GroupToComment>() {
            @Override
            public String entityToString(GroupToComment item) {
                return String.valueOf(item.getId());
            }
        };
        this.groupsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.comment_groups_button);
        this.groupsWidget.setAdapter(this.groupsAdapter);
        this.groupsWidget.setTitle(R.string.comment_groups_dialog_title);
    }

    /** Load data from model to curr.fields view. */
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

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_comment_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Comment) intent.getParcelableExtra(
                CommentContract.PARCEL);

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
        private final Comment entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final CommentEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new CommentProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("CommentEditFragment", e.getMessage());
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
                        R.string.comment_error_edit));
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
        private CommentEditFragment fragment;
        /** owner list. */
        private ArrayList<User> ownerList;
        /** post list. */
        private ArrayList<Post> postList;
        /** groups list. */
        private ArrayList<GroupToComment> groupsList;
    /** groups list. */
        private ArrayList<GroupToComment> associatedGroupsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final CommentEditFragment fragment) {
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
            Uri groupsUri = CommentProviderAdapter.COMMENT_URI;
            groupsUri = Uri.withAppendedPath(groupsUri, 
                                    String.valueOf(this.fragment.model.getId()));
            groupsUri = Uri.withAppendedPath(groupsUri, "groups");
            android.database.Cursor groupsCursor = 
                    this.ctx.getContentResolver().query(
                            groupsUri,
                            new String[]{GroupToCommentContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedGroupsList = new ArrayList<GroupToComment>();
            if (groupsCursor != null && groupsCursor.getCount() > 0) {
                while (groupsCursor.moveToNext()) {
                    int groupsId = groupsCursor.getInt(
                            groupsCursor.getColumnIndex(GroupToCommentContract.COL_ID));
                    for (GroupToComment groups : this.groupsList) {
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
            this.fragment.onPostLoaded(this.postList);
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
     * Called when post have been loaded.
     * @param items The loaded items
     */
    protected void onPostLoaded(ArrayList<Post> items) {
        this.postAdapter.loadData(items);
        
        if (this.model.getPost() != null) {
            for (Post item : items) {
                if (item.getId() == this.model.getPost().getId()) {
                    this.postAdapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when groups have been loaded.
     * @param items The loaded items
     */
    protected void onGroupsLoaded(ArrayList<GroupToComment> items) {
        this.groupsAdapter.loadData(items);
        this.groupsAdapter.setCheckedItems(this.model.getGroups());
    }
}
