/**************************************************************************
 * PoneyShowFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.view.poney;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tactfactory.demact.R;
import com.tactfactory.demact.entity.Poney;
import com.tactfactory.demact.entity.Jockey;
import com.jeanlui.demactor.harmony.view.DeleteDialog;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.view.MultiLoader;
import com.jeanlui.demactor.harmony.view.MultiLoader.UriLoadedCallback;
import com.jeanlui.demactor.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.jeanlui.demactor.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.jeanlui.demactor.provider.utils.PoneyProviderUtils;
import com.jeanlui.demactor.provider.PoneyProviderAdapter;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.provider.contract.ScoreContract;

/** Poney show fragment.
 *
 * This fragment gives you an interface to show a Poney.
 * 
 * @see android.app.Fragment
 */
public class PoneyShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Poney model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** iomAiome1 View. */
    protected TextView iomAiome1View;
    /** jockgFhjeys1 View. */
    protected TextView jockgFhjeys1View;
    /** scorvbnBe1 View. */
    protected TextView scorvbnBe1View;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Poney. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.iomAiome1View =
            (TextView) view.findViewById(
                    R.id.poney_iomaiome1);
        this.jockgFhjeys1View =
            (TextView) view.findViewById(
                    R.id.poney_jockgfhjeys1);
        this.scorvbnBe1View =
            (TextView) view.findViewById(
                    R.id.poney_scorvbnbe1);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.poney_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.poney_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getIomAiome1() != null) {
            this.iomAiome1View.setText(this.model.getIomAiome1());
        }
        if (this.model.getJockgFhjeys1() != null) {
            String jockgFhjeys1Value = "";
            for (Jockey item : this.model.getJockgFhjeys1()) {
                jockgFhjeys1Value += item.getFbgDFbdf() + ",";
            }
            this.jockgFhjeys1View.setText(jockgFhjeys1Value);
        }
        if (this.model.getScorvbnBe1() != null) {
            this.scorvbnBe1View.setText(
                    String.valueOf(this.model.getScorvbnBe1().getIdFD1()));
        }
        } else {
            this.dataLayout.setVisibility(View.GONE);
            this.emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(
                        R.layout.fragment_poney_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Poney) intent.getParcelableExtra(PoneyContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Poney to get the data from.
     */
    public void update(Poney item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PoneyProviderAdapter.PONEY_URI 
                    + "/" 
                    + this.model.getIdlioEm1();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PoneyShowFragment.this.onPoneyLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/jockgfhjeys1"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PoneyShowFragment.this.onJockgFhjeys1Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/scorvbnbe1"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PoneyShowFragment.this.onScorvbnBe1Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.init();
        }
    }

    /**
     * Called when the entity has been loaded.
     * 
     * @param c The cursor of this entity
     */
    public void onPoneyLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PoneyContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onJockgFhjeys1Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setJockgFhjeys1(JockeyContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setJockgFhjeys1(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onScorvbnBe1Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setScorvbnBe1(ScoreContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setScorvbnBe1(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PoneyEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PoneyEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PoneyContract.PARCEL, this.model);
        intent.putExtras(extras);

        this.getActivity().startActivity(intent);
    }
    /**
     * Shows a confirmation dialog.
     */
    @Override
    public void onClickDelete() {
        new DeleteDialog(this.getActivity(), this).show();
    }

    @Override
    public void onDeleteDialogClose(boolean ok) {
        if (ok) {
            new DeleteTask(this.getActivity(), this.model).execute();
        }
    }
    
    /** 
     * Called when delete task is done.
     */    
    public void onPostDelete() {
        if (this.deleteCallback != null) {
            this.deleteCallback.onItemDeleted();
        }
    }

    /**
     * This class will remove the entity into the DB.
     * It runs asynchronously.
     */
    private class DeleteTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private android.content.Context ctx;
        /** Entity to delete. */
        private Poney item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PoneySQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Poney item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PoneyProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PoneyShowFragment.this.onPostDelete();
            }
            super.onPostExecute(result);
        }
        
        

    }

    /**
     * Callback for item deletion.
     */ 
    public interface DeleteCallback {
        /** Called when current item has been deleted. */
        void onItemDeleted();
    }
}

