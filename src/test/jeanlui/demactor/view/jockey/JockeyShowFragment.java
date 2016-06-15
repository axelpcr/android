/**************************************************************************
 * JockeyShowFragment.java, demactor Android
 *
 * Copyright 2016
 * Description :
 * Author(s)   : Harmony
 * Licence     :
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.tactfactory.demact.view.jockey;


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
import com.tactfactory.demact.entity.Jockey;
import com.tactfactory.demact.entity.Poney;
import com.jeanlui.demactor.harmony.view.DeleteDialog;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.view.MultiLoader;
import com.jeanlui.demactor.harmony.view.MultiLoader.UriLoadedCallback;
import com.jeanlui.demactor.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.jeanlui.demactor.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.jeanlui.demactor.provider.utils.JockeyProviderUtils;
import com.jeanlui.demactor.provider.JockeyProviderAdapter;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.UserContract;

/** Jockey show fragment.
 *
 * This fragment gives you an interface to show a Jockey.
 *
 * @see android.app.Fragment
 */
public class JockeyShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Jockey model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** dfdfgdDDfgdfg View. */
    protected TextView dfdfgdDDfgdfgView;
    /** dfgdfgdfgdfFg View. */
    protected TextView dfgdfgdfgdfFgView;
    /** dzerzerBCze View. */
    protected TextView dzerzerBCzeView;
    /** iuytrezBa View. */
    protected TextView iuytrezBaView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Jockey. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.dfdfgdDDfgdfgView =
            (TextView) view.findViewById(
                    R.id.jockey_dfdfgdddfgdfg);
        this.dfgdfgdfgdfFgView =
            (TextView) view.findViewById(
                    R.id.jockey_dfgdfgdfgdffg);
        this.dzerzerBCzeView =
            (TextView) view.findViewById(
                    R.id.jockey_dzerzerbcze);
        this.iuytrezBaView =
            (TextView) view.findViewById(
                    R.id.jockey_iuytrezba);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.jockey_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.poney_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getDfdfgdDDfgdfg() != null) {
            this.dfdfgdDDfgdfgView.setText(this.model.getDfdfgdDDfgdfg());
        }
        if (this.model.getDfgdfgdfgdfFg() != null) {
            this.dfgdfgdfgdfFgView.setText(this.model.getDfgdfgdfgdfFg());
        }
        if (this.model.getDzerzerBCze() != null) {
            String dzerzerBCzeValue = "";
            for (Poney item : this.model.getDzerzerBCze()) {
                dzerzerBCzeValue += item.getIdlioEm1() + ",";
            }
            this.dzerzerBCzeView.setText(dzerzerBCzeValue);
        }
        if (this.model.getIuytrezBa() != null) {
            this.iuytrezBaView.setText(
                    String.valueOf(this.model.getIuytrezBa().getId1HNY()));
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
                        R.layout.fragment_jockey_show,
                        container,
                        false);
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);

        final Intent intent =  getActivity().getIntent();
        this.update((Jockey) intent.getParcelableExtra(JockeyContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Jockey to get the data from.
     */
    public void update(Jockey item) {
        this.model = item;

        this.loadData();

        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri =
                    JockeyProviderAdapter.JOCKEY_URI
                    + "/"
                    + this.model.getFbgDFbdf();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    JockeyShowFragment.this.onJockeyLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/dzerzerbcze"),
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    JockeyShowFragment.this.onDzerzerBCzeLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/iuytrezba"),
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    JockeyShowFragment.this.onIuytrezBaLoaded(c);
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
    public void onJockeyLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();

            JockeyContract.cursorToItem(
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
    public void onDzerzerBCzeLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setDzerzerBCze(PoneyContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setDzerzerBCze(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     *
     * @param c The cursor of this relation
     */
    public void onIuytrezBaLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setIuytrezBa(UserContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setIuytrezBa(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the JockeyEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    JockeyEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(JockeyContract.PARCEL, this.model);
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
        private Jockey item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build JockeySQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Jockey item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new JockeyProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                JockeyShowFragment.this.onPostDelete();
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

