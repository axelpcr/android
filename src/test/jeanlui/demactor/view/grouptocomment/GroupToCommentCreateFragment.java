/**************************************************************************
 * GroupToCommentCreateFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.grouptocomment;

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
import com.jeanlui.demactor.entity.GroupToComment;
import com.jeanlui.demactor.entity.Group;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;

import com.jeanlui.demactor.harmony.widget.SingleEntityWidget;
import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;
import com.jeanlui.demactor.provider.utils.GroupToCommentProviderUtils;
import com.jeanlui.demactor.provider.utils.GroupProviderUtils;

/**
 * GroupToComment create fragment.
 *
 * This fragment gives you an interface to create a GroupToComment.
 */
public class GroupToCommentCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected GroupToComment model = new GroupToComment();

    /** Fields View. */
    /** id View. */
    protected EditText idView;
    /** displayName View. */
    protected EditText displayNameView;
    /** The group chooser component. */
    protected SingleEntityWidget groupWidget;
    /** The group Adapter. */
    protected SingleEntityWidget.EntityAdapter<Group> 
                groupAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.idView =
            (EditText) view.findViewById(R.id.grouptocomment_id);
        this.displayNameView =
            (EditText) view.findViewById(R.id.grouptocomment_displayname);
        this.groupAdapter = 
                new SingleEntityWidget.EntityAdapter<Group>() {
            @Override
            public String entityToString(Group item) {
                return String.valueOf(item.getId());
            }
        };
        this.groupWidget =
            (SingleEntityWidget) view.findViewById(R.id.grouptocomment_group_button);
        this.groupWidget.setAdapter(this.groupAdapter);
        this.groupWidget.setTitle(R.string.grouptocomment_group_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        this.idView.setText(String.valueOf(this.model.getId()));
        if (this.model.getDisplayName() != null) {
            this.displayNameView.setText(this.model.getDisplayName());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setId(Integer.parseInt(
                    this.idView.getEditableText().toString()));

        this.model.setDisplayName(this.displayNameView.getEditableText().toString());

        this.model.setGroup(this.groupAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.idView.getText().toString().trim())) {
            error = R.string.grouptocomment_id_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.displayNameView.getText().toString().trim())) {
            error = R.string.grouptocomment_displayname_invalid_field_error;
        }
        if (this.groupAdapter.getSelectedItem() == null) {
            error = R.string.grouptocomment_group_invalid_field_error;
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
                R.layout.fragment_grouptocomment_create,
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
        private final GroupToComment entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final GroupToCommentCreateFragment fragment,
                final GroupToComment entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.grouptocomment_progress_save_title),
                    this.ctx.getString(
                            R.string.grouptocomment_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new GroupToCommentProviderUtils(this.ctx).insert(
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
                                R.string.grouptocomment_error_create));
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
        private GroupToCommentCreateFragment fragment;
        /** group list. */
        private ArrayList<Group> groupList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final GroupToCommentCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.grouptocomment_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.grouptocomment_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.groupList = 
                new GroupProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.groupAdapter.loadData(this.groupList);
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
