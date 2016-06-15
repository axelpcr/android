/**************************************************************************
 * GroupEditFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.group;

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
import com.jeanlui.demactor.entity.Group;
import com.jeanlui.demactor.entity.GroupToComment;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.widget.MultiEntityWidget;
import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;
import com.jeanlui.demactor.provider.GroupProviderAdapter;
import com.jeanlui.demactor.provider.utils.GroupProviderUtils;
import com.jeanlui.demactor.provider.utils.GroupToCommentProviderUtils;
import com.jeanlui.demactor.provider.contract.GroupContract;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;

/** Group create fragment.
 *
 * This fragment gives you an interface to edit a Group.
 *
 * @see android.app.Fragment
 */
public class GroupEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Group model = new Group();

    /** curr.fields View. */
    /** id View. */
    protected EditText idView;
    /** name View. */
    protected EditText nameView;
    /** The comments chooser component. */
    protected MultiEntityWidget commentsWidget;
    /** The comments Adapter. */
    protected MultiEntityWidget.EntityAdapter<GroupToComment>
            commentsAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.idView = (EditText) view.findViewById(
                R.id.group_id);
        this.nameView = (EditText) view.findViewById(
                R.id.group_name);
        this.commentsAdapter =
                new MultiEntityWidget.EntityAdapter<GroupToComment>() {
            @Override
            public String entityToString(GroupToComment item) {
                return String.valueOf(item.getId());
            }
        };
        this.commentsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.group_comments_button);
        this.commentsWidget.setAdapter(this.commentsAdapter);
        this.commentsWidget.setTitle(R.string.group_comments_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        this.idView.setText(String.valueOf(this.model.getId()));
        if (this.model.getName() != null) {
            this.nameView.setText(this.model.getName());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setId(Integer.parseInt(
                    this.idView.getEditableText().toString()));

        this.model.setName(this.nameView.getEditableText().toString());

        this.model.setComments(this.commentsAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.idView.getText().toString().trim())) {
            error = R.string.group_id_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.nameView.getText().toString().trim())) {
            error = R.string.group_name_invalid_field_error;
        }
        if (this.commentsAdapter.getCheckedItems().isEmpty()) {
            error = R.string.group_comments_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_group_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Group) intent.getParcelableExtra(
                GroupContract.PARCEL);

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
        private final Group entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final GroupEditFragment fragment,
                    final Group entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.group_progress_save_title),
                    this.ctx.getString(
                            R.string.group_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new GroupProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("GroupEditFragment", e.getMessage());
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
                        R.string.group_error_edit));
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
        private GroupEditFragment fragment;
        /** comments list. */
        private ArrayList<GroupToComment> commentsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final GroupEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.group_progress_load_relations_title),
                this.ctx.getString(
                    R.string.group_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.commentsList = 
                new GroupToCommentProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onCommentsLoaded(this.commentsList);

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
     * Called when comments have been loaded.
     * @param items The loaded items
     */
    protected void onCommentsLoaded(ArrayList<GroupToComment> items) {
        this.commentsAdapter.loadData(items);
        ArrayList<GroupToComment> modelItems = new ArrayList<GroupToComment>();
        for (GroupToComment item : items) {
            if (item.getGroup() != null && item.getGroup().getId() == this.model.getId()) {
                modelItems.add(item);
                this.commentsAdapter.checkItem(item, true);
            }
        }
        this.model.setComments(modelItems);
    }
}
