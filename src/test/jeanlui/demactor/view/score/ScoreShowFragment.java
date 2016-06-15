/**************************************************************************
 * ScoreShowFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.view.score;


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
import com.tactfactory.demact.entity.Score;
import com.tactfactory.demact.entity.Poney;
import com.tactfactory.demact.entity.User;
import com.jeanlui.demactor.harmony.view.DeleteDialog;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.view.MultiLoader;
import com.jeanlui.demactor.harmony.view.MultiLoader.UriLoadedCallback;
import com.jeanlui.demactor.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.jeanlui.demactor.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.jeanlui.demactor.provider.utils.ScoreProviderUtils;
import com.jeanlui.demactor.provider.ScoreProviderAdapter;
import com.jeanlui.demactor.provider.contract.ScoreContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.UserContract;

/** Score show fragment.
 *
 * This fragment gives you an interface to show a Score.
 * 
 * @see android.app.Fragment
 */
public class ScoreShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Score model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** moneFGHFGy1 View. */
    protected TextView moneFGHFGy1View;
    /** ponRTYeys1 View. */
    protected TextView ponRTYeys1View;
    /** useGHHNrs1 View. */
    protected TextView useGHHNrs1View;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Score. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.moneFGHFGy1View =
            (TextView) view.findViewById(
                    R.id.score_monefghfgy1);
        this.ponRTYeys1View =
            (TextView) view.findViewById(
                    R.id.score_ponrtyeys1);
        this.useGHHNrs1View =
            (TextView) view.findViewById(
                    R.id.score_useghhnrs1);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.score_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.score_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        this.moneFGHFGy1View.setText(String.valueOf(this.model.getMoneFGHFGy1()));
        if (this.model.getPonRTYeys1() != null) {
            String ponRTYeys1Value = "";
            for (Poney item : this.model.getPonRTYeys1()) {
                ponRTYeys1Value += item.getIdlioEm1() + ",";
            }
            this.ponRTYeys1View.setText(ponRTYeys1Value);
        }
        if (this.model.getUseGHHNrs1() != null) {
            String useGHHNrs1Value = "";
            for (User item : this.model.getUseGHHNrs1()) {
                useGHHNrs1Value += item.getId1HNY() + ",";
            }
            this.useGHHNrs1View.setText(useGHHNrs1Value);
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
                        R.layout.fragment_score_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Score) intent.getParcelableExtra(ScoreContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Score to get the data from.
     */
    public void update(Score item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    ScoreProviderAdapter.SCORE_URI 
                    + "/" 
                    + this.model.getIdFD1();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ScoreShowFragment.this.onScoreLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/ponrtyeys1"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ScoreShowFragment.this.onPonRTYeys1Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/useghhnrs1"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ScoreShowFragment.this.onUseGHHNrs1Loaded(c);
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
    public void onScoreLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            ScoreContract.cursorToItem(
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
    public void onPonRTYeys1Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setPonRTYeys1(PoneyContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setPonRTYeys1(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onUseGHHNrs1Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setUseGHHNrs1(UserContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setUseGHHNrs1(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the ScoreEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    ScoreEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(ScoreContract.PARCEL, this.model);
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
        private Score item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build ScoreSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Score item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new ScoreProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                ScoreShowFragment.this.onPostDelete();
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

