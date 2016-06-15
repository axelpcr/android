/**************************************************************************
 * HiddenEntityCreateFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.hiddenentity;



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
import com.jeanlui.demactor.entity.HiddenEntity;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;

import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;
import com.jeanlui.demactor.provider.utils.HiddenEntityProviderUtils;

/**
 * HiddenEntity create fragment.
 *
 * This fragment gives you an interface to create a HiddenEntity.
 */
public class HiddenEntityCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected HiddenEntity model = new HiddenEntity();

    /** Fields View. */
    /** id View. */
    protected EditText idView;
    /** content View. */
    protected EditText contentView;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.idView =
            (EditText) view.findViewById(R.id.hiddenentity_id);
        this.contentView =
            (EditText) view.findViewById(R.id.hiddenentity_content);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        this.idView.setText(String.valueOf(this.model.getId()));
        if (this.model.getContent() != null) {
            this.contentView.setText(this.model.getContent());
        }


    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setId(Integer.parseInt(
                    this.idView.getEditableText().toString()));

        this.model.setContent(this.contentView.getEditableText().toString());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.idView.getText().toString().trim())) {
            error = R.string.hiddenentity_id_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.contentView.getText().toString().trim())) {
            error = R.string.hiddenentity_content_invalid_field_error;
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
                R.layout.fragment_hiddenentity_create,
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
        private final HiddenEntity entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final HiddenEntityCreateFragment fragment,
                final HiddenEntity entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.hiddenentity_progress_save_title),
                    this.ctx.getString(
                            R.string.hiddenentity_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new HiddenEntityProviderUtils(this.ctx).insert(
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
                                R.string.hiddenentity_error_create));
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
            new CreateTask(this, this.model).execute();
        }
    }
}
