/**************************************************************************
 * JockeyEditFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.view.jockey;

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
import com.jeanlui.demactor.entity.Jockey;
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.User;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.widget.MultiEntityWidget;
import com.jeanlui.demactor.harmony.widget.SingleEntityWidget;
import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;
import com.jeanlui.demactor.provider.JockeyProviderAdapter;
import com.jeanlui.demactor.provider.utils.JockeyProviderUtils;
import com.jeanlui.demactor.provider.utils.PoneyProviderUtils;
import com.jeanlui.demactor.provider.utils.UserProviderUtils;
import com.jeanlui.demactor.data.PoneySQLiteAdapter;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.UserContract;

/** Jockey create fragment.
 *
 * This fragment gives you an interface to edit a Jockey.
 *
 * @see android.app.Fragment
 */
public class JockeyEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected Jockey model = new Jockey();

    /** curr.fields View. */
    /** dfdfgdDDfgdfg View. */
    protected EditText dfdfgdDDfgdfgView;
    /** dfgdfgdfgdfFg View. */
    protected EditText dfgdfgdfgdfFgView;
    /** The dzerzerBCze chooser component. */
    protected MultiEntityWidget dzerzerBCzeWidget;
    /** The dzerzerBCze Adapter. */
    protected MultiEntityWidget.EntityAdapter<Poney>
            dzerzerBCzeAdapter;
    /** The iuytrezBa chooser component. */
    protected SingleEntityWidget iuytrezBaWidget;
    /** The iuytrezBa Adapter. */
    protected SingleEntityWidget.EntityAdapter<User>
            iuytrezBaAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.dfdfgdDDfgdfgView = (EditText) view.findViewById(
                R.id.jockey_dfdfgdddfgdfg);
        this.dfgdfgdfgdfFgView = (EditText) view.findViewById(
                R.id.jockey_dfgdfgdfgdffg);
        this.dzerzerBCzeAdapter =
                new MultiEntityWidget.EntityAdapter<Poney>() {
            @Override
            public String entityToString(Poney item) {
                return String.valueOf(item.getIdlioEm1());
            }
        };
        this.dzerzerBCzeWidget = (MultiEntityWidget) view.findViewById(
                        R.id.jockey_dzerzerbcze_button);
        this.dzerzerBCzeWidget.setAdapter(this.dzerzerBCzeAdapter);
        this.dzerzerBCzeWidget.setTitle(R.string.jockey_dzerzerbcze_dialog_title);
        this.iuytrezBaAdapter =
                new SingleEntityWidget.EntityAdapter<User>() {
            @Override
            public String entityToString(User item) {
                return String.valueOf(item.getId1HNY());
            }
        };
        this.iuytrezBaWidget =
            (SingleEntityWidget) view.findViewById(R.id.jockey_iuytrezba_button);
        this.iuytrezBaWidget.setAdapter(this.iuytrezBaAdapter);
        this.iuytrezBaWidget.setTitle(R.string.jockey_iuytrezba_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getDfdfgdDDfgdfg() != null) {
            this.dfdfgdDDfgdfgView.setText(this.model.getDfdfgdDDfgdfg());
        }
        if (this.model.getDfgdfgdfgdfFg() != null) {
            this.dfgdfgdfgdfFgView.setText(this.model.getDfgdfgdfgdfFg());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setDfdfgdDDfgdfg(this.dfdfgdDDfgdfgView.getEditableText().toString());

        this.model.setDfgdfgdfgdfFg(this.dfgdfgdfgdfFgView.getEditableText().toString());

        this.model.setDzerzerBCze(this.dzerzerBCzeAdapter.getCheckedItems());

        this.model.setIuytrezBa(this.iuytrezBaAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.dfdfgdDDfgdfgView.getText().toString().trim())) {
            error = R.string.jockey_dfdfgdddfgdfg_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.dfgdfgdfgdfFgView.getText().toString().trim())) {
            error = R.string.jockey_dfgdfgdfgdffg_invalid_field_error;
        }
        if (this.dzerzerBCzeAdapter.getCheckedItems().isEmpty()) {
            error = R.string.jockey_dzerzerbcze_invalid_field_error;
        }
        if (this.iuytrezBaAdapter.getSelectedItem() == null) {
            error = R.string.jockey_iuytrezba_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_jockey_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (Jockey) intent.getParcelableExtra(
                JockeyContract.PARCEL);

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
        private final Jockey entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final JockeyEditFragment fragment,
                    final Jockey entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.jockey_progress_save_title),
                    this.ctx.getString(
                            R.string.jockey_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new JockeyProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("JockeyEditFragment", e.getMessage());
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
                        R.string.jockey_error_edit));
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
        private JockeyEditFragment fragment;
        /** dzerzerBCze list. */
        private ArrayList<Poney> dzerzerBCzeList;
    /** dzerzerBCze list. */
        private ArrayList<Poney> associatedDzerzerBCzeList;
        /** iuytrezBa list. */
        private ArrayList<User> iuytrezBaList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final JockeyEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.jockey_progress_load_relations_title),
                this.ctx.getString(
                    R.string.jockey_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.dzerzerBCzeList = 
                new PoneyProviderUtils(this.ctx).queryAll();
            Uri dzerzerBCzeUri = JockeyProviderAdapter.JOCKEY_URI;
            dzerzerBCzeUri = Uri.withAppendedPath(dzerzerBCzeUri, 
                                    String.valueOf(this.fragment.model.getFbgDFbdf()));
            dzerzerBCzeUri = Uri.withAppendedPath(dzerzerBCzeUri, "dzerzerBCze");
            android.database.Cursor dzerzerBCzeCursor = 
                    this.ctx.getContentResolver().query(
                            dzerzerBCzeUri,
                            new String[]{PoneyContract.ALIASED_COL_IDLIOEM1},
                            null,
                            null, 
                            null);
            
            this.associatedDzerzerBCzeList = new ArrayList<Poney>();
            if (dzerzerBCzeCursor != null && dzerzerBCzeCursor.getCount() > 0) {
                while (dzerzerBCzeCursor.moveToNext()) {
                    int dzerzerBCzeIdlioEm1 = dzerzerBCzeCursor.getInt(
                            dzerzerBCzeCursor.getColumnIndex(PoneyContract.COL_IDLIOEM1));
                    for (Poney dzerzerBCze : this.dzerzerBCzeList) {
                        if (dzerzerBCze.getIdlioEm1() ==  dzerzerBCzeIdlioEm1) {
                            this.associatedDzerzerBCzeList.add(dzerzerBCze);
                        }
                    }
                }
                dzerzerBCzeCursor.close();
            }
            this.iuytrezBaList = 
                new UserProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setDzerzerBCze(this.associatedDzerzerBCzeList);
            this.fragment.onDzerzerBCzeLoaded(this.dzerzerBCzeList);
            this.fragment.onIuytrezBaLoaded(this.iuytrezBaList);

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
     * Called when dzerzerBCze have been loaded.
     * @param items The loaded items
     */
    protected void onDzerzerBCzeLoaded(ArrayList<Poney> items) {
        this.dzerzerBCzeAdapter.loadData(items);
        this.dzerzerBCzeAdapter.setCheckedItems(this.model.getDzerzerBCze());
    }
    /**
     * Called when iuytrezBa have been loaded.
     * @param items The loaded items
     */
    protected void onIuytrezBaLoaded(ArrayList<User> items) {
        this.iuytrezBaAdapter.loadData(items);
        
        if (this.model.getIuytrezBa() != null) {
            for (User item : items) {
                if (item.getId1HNY() == this.model.getIuytrezBa().getId1HNY()) {
                    this.iuytrezBaAdapter.selectItem(item);
                }
            }
        }
    }
}
