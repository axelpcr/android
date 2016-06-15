/**************************************************************************
 * PoneyEditFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.view.poney;

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
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.Jockey;
import com.jeanlui.demactor.entity.Score;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.widget.MultiEntityWidget;
import com.jeanlui.demactor.harmony.widget.SingleEntityWidget;
import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;
import com.jeanlui.demactor.provider.PoneyProviderAdapter;
import com.jeanlui.demactor.provider.utils.PoneyProviderUtils;
import com.jeanlui.demactor.provider.utils.JockeyProviderUtils;
import com.jeanlui.demactor.provider.utils.ScoreProviderUtils;
import com.jeanlui.demactor.data.JockeySQLiteAdapter;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.provider.contract.ScoreContract;

/** Poney create fragment.
 *
 * This fragment gives you an interface to edit a Poney.
 *
 * @see android.app.Fragment
 */
public class PoneyEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Poney model = new Poney();

    /** curr.fields View. */
    /** iomAiome1 View. */
    protected EditText iomAiome1View;
    /** The jockgFhjeys1 chooser component. */
    protected MultiEntityWidget jockgFhjeys1Widget;
    /** The jockgFhjeys1 Adapter. */
    protected MultiEntityWidget.EntityAdapter<Jockey>
            jockgFhjeys1Adapter;
    /** The scorvbnBe1 chooser component. */
    protected SingleEntityWidget scorvbnBe1Widget;
    /** The scorvbnBe1 Adapter. */
    protected SingleEntityWidget.EntityAdapter<Score>
            scorvbnBe1Adapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.iomAiome1View = (EditText) view.findViewById(
                R.id.poney_iomaiome1);
        this.jockgFhjeys1Adapter =
                new MultiEntityWidget.EntityAdapter<Jockey>() {
            @Override
            public String entityToString(Jockey item) {
                return String.valueOf(item.getFbgDFbdf());
            }
        };
        this.jockgFhjeys1Widget = (MultiEntityWidget) view.findViewById(
                        R.id.poney_jockgfhjeys1_button);
        this.jockgFhjeys1Widget.setAdapter(this.jockgFhjeys1Adapter);
        this.jockgFhjeys1Widget.setTitle(R.string.poney_jockgfhjeys1_dialog_title);
        this.scorvbnBe1Adapter =
                new SingleEntityWidget.EntityAdapter<Score>() {
            @Override
            public String entityToString(Score item) {
                return String.valueOf(item.getIdFD1());
            }
        };
        this.scorvbnBe1Widget =
            (SingleEntityWidget) view.findViewById(R.id.poney_scorvbnbe1_button);
        this.scorvbnBe1Widget.setAdapter(this.scorvbnBe1Adapter);
        this.scorvbnBe1Widget.setTitle(R.string.poney_scorvbnbe1_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getIomAiome1() != null) {
            this.iomAiome1View.setText(this.model.getIomAiome1());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setIomAiome1(this.iomAiome1View.getEditableText().toString());

        this.model.setJockgFhjeys1(this.jockgFhjeys1Adapter.getCheckedItems());

        this.model.setScorvbnBe1(this.scorvbnBe1Adapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.iomAiome1View.getText().toString().trim())) {
            error = R.string.poney_iomaiome1_invalid_field_error;
        }
        if (this.jockgFhjeys1Adapter.getCheckedItems().isEmpty()) {
            error = R.string.poney_jockgfhjeys1_invalid_field_error;
        }
        if (this.scorvbnBe1Adapter.getSelectedItem() == null) {
            error = R.string.poney_scorvbnbe1_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_poney_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Poney) intent.getParcelableExtra(
                PoneyContract.PARCEL);

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
        private final Poney entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PoneyEditFragment fragment,
                    final Poney entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.poney_progress_save_title),
                    this.ctx.getString(
                            R.string.poney_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PoneyProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PoneyEditFragment", e.getMessage());
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
                        R.string.poney_error_edit));
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
        private PoneyEditFragment fragment;
        /** jockgFhjeys1 list. */
        private ArrayList<Jockey> jockgFhjeys1List;
    /** jockgFhjeys1 list. */
        private ArrayList<Jockey> associatedJockgFhjeys1List;
        /** scorvbnBe1 list. */
        private ArrayList<Score> scorvbnBe1List;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PoneyEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.poney_progress_load_relations_title),
                this.ctx.getString(
                    R.string.poney_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.jockgFhjeys1List = 
                new JockeyProviderUtils(this.ctx).queryAll();
            Uri jockgFhjeys1Uri = PoneyProviderAdapter.PONEY_URI;
            jockgFhjeys1Uri = Uri.withAppendedPath(jockgFhjeys1Uri, 
                                    String.valueOf(this.fragment.model.getIdlioEm1()));
            jockgFhjeys1Uri = Uri.withAppendedPath(jockgFhjeys1Uri, "jockgFhjeys1");
            android.database.Cursor jockgFhjeys1Cursor = 
                    this.ctx.getContentResolver().query(
                            jockgFhjeys1Uri,
                            new String[]{JockeyContract.ALIASED_COL_FBGDFBDF},
                            null,
                            null, 
                            null);
            
            this.associatedJockgFhjeys1List = new ArrayList<Jockey>();
            if (jockgFhjeys1Cursor != null && jockgFhjeys1Cursor.getCount() > 0) {
                while (jockgFhjeys1Cursor.moveToNext()) {
                    int jockgFhjeys1FbgDFbdf = jockgFhjeys1Cursor.getInt(
                            jockgFhjeys1Cursor.getColumnIndex(JockeyContract.COL_FBGDFBDF));
                    for (Jockey jockgFhjeys1 : this.jockgFhjeys1List) {
                        if (jockgFhjeys1.getFbgDFbdf() ==  jockgFhjeys1FbgDFbdf) {
                            this.associatedJockgFhjeys1List.add(jockgFhjeys1);
                        }
                    }
                }
                jockgFhjeys1Cursor.close();
            }
            this.scorvbnBe1List = 
                new ScoreProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setJockgFhjeys1(this.associatedJockgFhjeys1List);
            this.fragment.onJockgFhjeys1Loaded(this.jockgFhjeys1List);
            this.fragment.onScorvbnBe1Loaded(this.scorvbnBe1List);

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
     * Called when jockgFhjeys1 have been loaded.
     * @param items The loaded items
     */
    protected void onJockgFhjeys1Loaded(ArrayList<Jockey> items) {
        this.jockgFhjeys1Adapter.loadData(items);
        this.jockgFhjeys1Adapter.setCheckedItems(this.model.getJockgFhjeys1());
    }
    /**
     * Called when scorvbnBe1 have been loaded.
     * @param items The loaded items
     */
    protected void onScorvbnBe1Loaded(ArrayList<Score> items) {
        this.scorvbnBe1Adapter.loadData(items);
        
        if (this.model.getScorvbnBe1() != null) {
            for (Score item : items) {
                if (item.getIdFD1() == this.model.getScorvbnBe1().getIdFD1()) {
                    this.scorvbnBe1Adapter.selectItem(item);
                }
            }
        }
    }
}
