/**************************************************************************
 * ScoreCreateFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.view.score;

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
import com.tactfactory.demact.R;
import com.jeanlui.demactor.entity.Score;
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.User;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.widget.MultiEntityWidget;
import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;
import com.jeanlui.demactor.provider.utils.ScoreProviderUtils;
import com.jeanlui.demactor.provider.utils.PoneyProviderUtils;
import com.jeanlui.demactor.provider.utils.UserProviderUtils;

/**
 * Score create fragment.
 *
 * This fragment gives you an interface to create a Score.
 */
public class ScoreCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Score model = new Score();

    /** Fields View. */
    /** moneFGHFGy1 View. */
    protected EditText moneFGHFGy1View;
    /** The ponRTYeys1 chooser component. */
    protected MultiEntityWidget ponRTYeys1Widget;
    /** The ponRTYeys1 Adapter. */
    protected MultiEntityWidget.EntityAdapter<Poney> 
                ponRTYeys1Adapter;
    /** The useGHHNrs1 chooser component. */
    protected MultiEntityWidget useGHHNrs1Widget;
    /** The useGHHNrs1 Adapter. */
    protected MultiEntityWidget.EntityAdapter<User> 
                useGHHNrs1Adapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.moneFGHFGy1View =
            (EditText) view.findViewById(R.id.score_monefghfgy1);
        this.ponRTYeys1Adapter = 
                new MultiEntityWidget.EntityAdapter<Poney>() {
            @Override
            public String entityToString(Poney item) {
                return String.valueOf(item.getIdlioEm1());
            }
        };
        this.ponRTYeys1Widget =
            (MultiEntityWidget) view.findViewById(R.id.score_ponrtyeys1_button);
        this.ponRTYeys1Widget.setAdapter(this.ponRTYeys1Adapter);
        this.ponRTYeys1Widget.setTitle(R.string.score_ponrtyeys1_dialog_title);
        this.useGHHNrs1Adapter = 
                new MultiEntityWidget.EntityAdapter<User>() {
            @Override
            public String entityToString(User item) {
                return String.valueOf(item.getId1HNY());
            }
        };
        this.useGHHNrs1Widget =
            (MultiEntityWidget) view.findViewById(R.id.score_useghhnrs1_button);
        this.useGHHNrs1Widget.setAdapter(this.useGHHNrs1Adapter);
        this.useGHHNrs1Widget.setTitle(R.string.score_useghhnrs1_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        this.moneFGHFGy1View.setText(String.valueOf(this.model.getMoneFGHFGy1()));

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setMoneFGHFGy1(Integer.parseInt(
                    this.moneFGHFGy1View.getEditableText().toString()));

        this.model.setPonRTYeys1(this.ponRTYeys1Adapter.getCheckedItems());

        this.model.setUseGHHNrs1(this.useGHHNrs1Adapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.moneFGHFGy1View.getText().toString().trim())) {
            error = R.string.score_monefghfgy1_invalid_field_error;
        }
        if (this.ponRTYeys1Adapter.getCheckedItems().isEmpty()) {
            error = R.string.score_ponrtyeys1_invalid_field_error;
        }
        if (this.useGHHNrs1Adapter.getCheckedItems().isEmpty()) {
            error = R.string.score_useghhnrs1_invalid_field_error;
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
                R.layout.fragment_score_create,
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
        private final Score entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final ScoreCreateFragment fragment,
                final Score entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.score_progress_save_title),
                    this.ctx.getString(
                            R.string.score_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new ScoreProviderUtils(this.ctx).insert(
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
                                R.string.score_error_create));
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
        private ScoreCreateFragment fragment;
        /** ponRTYeys1 list. */
        private ArrayList<Poney> ponRTYeys1List;
        /** useGHHNrs1 list. */
        private ArrayList<User> useGHHNrs1List;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final ScoreCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.score_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.score_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.ponRTYeys1List = 
                new PoneyProviderUtils(this.ctx).queryAll();
            this.useGHHNrs1List = 
                new UserProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.ponRTYeys1Adapter.loadData(this.ponRTYeys1List);
            this.fragment.useGHHNrs1Adapter.loadData(this.useGHHNrs1List);
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
