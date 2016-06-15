/**************************************************************************
 * ScoreEditFragment.java, demactor Android
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
import com.tactfactory.demact.R;
import com.jeanlui.demactor.entity.Score;
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.User;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.widget.MultiEntityWidget;
import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;
import com.jeanlui.demactor.provider.ScoreProviderAdapter;
import com.jeanlui.demactor.provider.utils.ScoreProviderUtils;
import com.jeanlui.demactor.provider.utils.PoneyProviderUtils;
import com.jeanlui.demactor.provider.utils.UserProviderUtils;
import com.jeanlui.demactor.provider.contract.ScoreContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.UserContract;

/** Score create fragment.
 *
 * This fragment gives you an interface to edit a Score.
 *
 * @see android.app.Fragment
 */
public class ScoreEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Score model = new Score();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.moneFGHFGy1View = (EditText) view.findViewById(
                R.id.score_monefghfgy1);
        this.ponRTYeys1Adapter =
                new MultiEntityWidget.EntityAdapter<Poney>() {
            @Override
            public String entityToString(Poney item) {
                return String.valueOf(item.getIdlioEm1());
            }
        };
        this.ponRTYeys1Widget = (MultiEntityWidget) view.findViewById(
                        R.id.score_ponrtyeys1_button);
        this.ponRTYeys1Widget.setAdapter(this.ponRTYeys1Adapter);
        this.ponRTYeys1Widget.setTitle(R.string.score_ponrtyeys1_dialog_title);
        this.useGHHNrs1Adapter =
                new MultiEntityWidget.EntityAdapter<User>() {
            @Override
            public String entityToString(User item) {
                return String.valueOf(item.getId1HNY());
            }
        };
        this.useGHHNrs1Widget = (MultiEntityWidget) view.findViewById(
                        R.id.score_useghhnrs1_button);
        this.useGHHNrs1Widget.setAdapter(this.useGHHNrs1Adapter);
        this.useGHHNrs1Widget.setTitle(R.string.score_useghhnrs1_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        this.moneFGHFGy1View.setText(String.valueOf(this.model.getMoneFGHFGy1()));

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_score_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Score) intent.getParcelableExtra(
                ScoreContract.PARCEL);

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
        private final Score entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final ScoreEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new ScoreProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("ScoreEditFragment", e.getMessage());
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
                        R.string.score_error_edit));
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
        private ScoreEditFragment fragment;
        /** ponRTYeys1 list. */
        private ArrayList<Poney> ponRTYeys1List;
        /** useGHHNrs1 list. */
        private ArrayList<User> useGHHNrs1List;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final ScoreEditFragment fragment) {
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
            this.fragment.onPonRTYeys1Loaded(this.ponRTYeys1List);
            this.fragment.onUseGHHNrs1Loaded(this.useGHHNrs1List);

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
     * Called when ponRTYeys1 have been loaded.
     * @param items The loaded items
     */
    protected void onPonRTYeys1Loaded(ArrayList<Poney> items) {
        this.ponRTYeys1Adapter.loadData(items);
        ArrayList<Poney> modelItems = new ArrayList<Poney>();
        for (Poney item : items) {
            if (item.getScorvbnBe1() != null && item.getScorvbnBe1().getId() == this.model.getId()) {
                modelItems.add(item);
                this.ponRTYeys1Adapter.checkItem(item, true);
            }
        }
        this.model.setPonRTYeys1(modelItems);
    }
    /**
     * Called when useGHHNrs1 have been loaded.
     * @param items The loaded items
     */
    protected void onUseGHHNrs1Loaded(ArrayList<User> items) {
        this.useGHHNrs1Adapter.loadData(items);
        ArrayList<User> modelItems = new ArrayList<User>();
        for (User item : items) {
            if (item.getScoFGHre1() != null && item.getScoFGHre1().getId() == this.model.getId()) {
                modelItems.add(item);
                this.useGHHNrs1Adapter.checkItem(item, true);
            }
        }
        this.model.setUseGHHNrs1(modelItems);
    }
}
