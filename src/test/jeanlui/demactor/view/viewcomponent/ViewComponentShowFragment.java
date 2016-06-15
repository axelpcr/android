/**************************************************************************
 * ViewComponentShowFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.viewcomponent;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeanlui.demactor.R;
import com.jeanlui.demactor.entity.ViewComponent;
import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.harmony.view.DeleteDialog;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.view.MultiLoader;
import com.jeanlui.demactor.harmony.view.MultiLoader.UriLoadedCallback;
import com.jeanlui.demactor.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.jeanlui.demactor.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.jeanlui.demactor.provider.utils.ViewComponentProviderUtils;
import com.jeanlui.demactor.provider.ViewComponentProviderAdapter;
import com.jeanlui.demactor.provider.contract.ViewComponentContract;

/** ViewComponent show fragment.
 *
 * This fragment gives you an interface to show a ViewComponent.
 * 
 * @see android.app.Fragment
 */
public class ViewComponentShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected ViewComponent model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** stringField View. */
    protected TextView stringFieldView;
    /** text View. */
    protected TextView textView;
    /** dateTime View. */
    protected TextView dateTimeView;
    /** date View. */
    protected TextView dateView;
    /** time View. */
    protected TextView timeView;
    /** login View. */
    protected TextView loginView;
    /** password View. */
    protected TextView passwordView;
    /** email View. */
    protected TextView emailView;
    /** phone View. */
    protected TextView phoneView;
    /** city View. */
    protected TextView cityView;
    /** zipCode View. */
    protected TextView zipCodeView;
    /** country View. */
    protected TextView countryView;
    /** byteField View. */
    protected TextView byteFieldView;
    /** charField View. */
    protected TextView charFieldView;
    /** shortField View. */
    protected TextView shortFieldView;
    /** character View. */
    protected TextView characterView;
    /** choice View. */
    protected TextView choiceView;
    /** booleanObject View. */
    protected CheckBox booleanObjectView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no ViewComponent. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.stringFieldView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_stringfield);
        this.textView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_text);
        this.dateTimeView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_datetime);
        this.dateView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_date);
        this.timeView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_time);
        this.loginView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_login);
        this.passwordView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_password);
        this.emailView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_email);
        this.phoneView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_phone);
        this.cityView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_city);
        this.zipCodeView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_zipcode);
        this.countryView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_country);
        this.byteFieldView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_bytefield);
        this.charFieldView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_charfield);
        this.shortFieldView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_shortfield);
        this.characterView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_character);
        this.choiceView =
            (TextView) view.findViewById(
                    R.id.viewcomponent_choice);
        this.booleanObjectView =
            (CheckBox) view.findViewById(
                    R.id.viewcomponent_booleanobject);
        this.booleanObjectView.setEnabled(false);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.viewcomponent_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.viewcomponent_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getStringField() != null) {
            this.stringFieldView.setText(this.model.getStringField());
        }
        if (this.model.getText() != null) {
            this.textView.setText(this.model.getText());
        }
        if (this.model.getDateTime() != null) {
            this.dateTimeView.setText(
                    DateUtils.formatDateTimeToString(
                            this.model.getDateTime()));
        }
        if (this.model.getDate() != null) {
            this.dateView.setText(
                    DateUtils.formatDateToString(
                            this.model.getDate()));
        }
        if (this.model.getTime() != null) {
            this.timeView.setText(
                    DateUtils.formatTimeToString(
                            this.model.getTime()));
        }
        if (this.model.getLogin() != null) {
            this.loginView.setText(this.model.getLogin());
        }
        if (this.model.getPassword() != null) {
            this.passwordView.setText(this.model.getPassword());
        }
        if (this.model.getEmail() != null) {
            this.emailView.setText(this.model.getEmail());
        }
        if (this.model.getPhone() != null) {
            this.phoneView.setText(this.model.getPhone());
        }
        if (this.model.getCity() != null) {
            this.cityView.setText(this.model.getCity());
        }
        this.zipCodeView.setText(String.valueOf(this.model.getZipCode()));
        if (this.model.getCountry() != null) {
            this.countryView.setText(this.model.getCountry());
        }
        this.byteFieldView.setText(String.valueOf(this.model.getByteField()));
        this.charFieldView.setText(String.valueOf(this.model.getCharField()));
        this.shortFieldView.setText(String.valueOf(this.model.getShortField()));
        if (this.model.getCharacter() != null) {
            this.characterView.setText(String.valueOf(this.model.getCharacter()));
        }
        if (this.model.getChoice() != null) {
            this.choiceView.setText(this.model.getChoice().toString());
        }
        if (this.model.isBooleanObject() != null) {
            this.booleanObjectView.setChecked(this.model.isBooleanObject());
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
                        R.layout.fragment_viewcomponent_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((ViewComponent) intent.getParcelableExtra(ViewComponentContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The ViewComponent to get the data from.
     */
    public void update(ViewComponent item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    ViewComponentProviderAdapter.VIEWCOMPONENT_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    ViewComponentShowFragment.this.onViewComponentLoaded(c);
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
    public void onViewComponentLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            ViewComponentContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }

    /**
     * Calls the ViewComponentEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    ViewComponentEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(ViewComponentContract.PARCEL, this.model);
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
        private ViewComponent item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build ViewComponentSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final ViewComponent item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new ViewComponentProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                ViewComponentShowFragment.this.onPostDelete();
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

