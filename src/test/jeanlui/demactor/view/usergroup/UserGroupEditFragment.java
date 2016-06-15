/**************************************************************************
 * UserGroupEditFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.usergroup;



import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;

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
import com.jeanlui.demactor.entity.UserGroup;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;

import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;

import com.jeanlui.demactor.provider.utils.UserGroupProviderUtils;
import com.jeanlui.demactor.provider.contract.UserGroupContract;

/** UserGroup create fragment.
 *
 * This fragment gives you an interface to edit a UserGroup.
 *
 * @see android.app.Fragment
 */
public class UserGroupEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected UserGroup model = new UserGroup();

    /** curr.fields View. */
    /** id View. */
    protected EditText idView;
    /** name View. */
    protected EditText nameView;
    /** writePermission View. */
    protected CheckBox writePermissionView;
    /** deletePermission View. */
    protected CheckBox deletePermissionView;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.idView = (EditText) view.findViewById(
                R.id.usergroup_id);
        this.nameView = (EditText) view.findViewById(
                R.id.usergroup_name);
        this.writePermissionView = (CheckBox) view.findViewById(
                R.id.usergroup_writepermission);
        this.deletePermissionView = (CheckBox) view.findViewById(
                R.id.usergroup_deletepermission);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        this.idView.setText(String.valueOf(this.model.getId()));
        if (this.model.getName() != null) {
            this.nameView.setText(this.model.getName());
        }
        this.writePermissionView.setChecked(this.model.isWritePermission());
        this.deletePermissionView.setChecked(this.model.isDeletePermission());


    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setId(Integer.parseInt(
                    this.idView.getEditableText().toString()));

        this.model.setName(this.nameView.getEditableText().toString());

        this.model.setWritePermission(this.writePermissionView.isChecked());

        this.model.setDeletePermission(this.deletePermissionView.isChecked());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.idView.getText().toString().trim())) {
            error = R.string.usergroup_id_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.nameView.getText().toString().trim())) {
            error = R.string.usergroup_name_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_usergroup_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (UserGroup) intent.getParcelableExtra(
                UserGroupContract.PARCEL);

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
        private final UserGroup entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final UserGroupEditFragment fragment,
                    final UserGroup entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.usergroup_progress_save_title),
                    this.ctx.getString(
                            R.string.usergroup_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new UserGroupProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("UserGroupEditFragment", e.getMessage());
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
                        R.string.usergroup_error_edit));
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



    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new EditTask(this, this.model).execute();
        }
    }

}
