/**************************************************************************
 * ViewComponentEditFragment.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.viewcomponent;



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
import com.jeanlui.demactor.entity.ViewComponent;
import com.jeanlui.demactor.entity.ViewComponent.Choice;

import com.jeanlui.demactor.harmony.view.HarmonyFragmentActivity;
import com.jeanlui.demactor.harmony.view.HarmonyFragment;
import com.jeanlui.demactor.harmony.widget.DateWidget;
import com.jeanlui.demactor.harmony.widget.TimeWidget;
import com.jeanlui.demactor.harmony.widget.DateTimeWidget;

import com.jeanlui.demactor.harmony.widget.EnumSpinner;
import com.jeanlui.demactor.menu.SaveMenuWrapper.SaveMenuInterface;

import com.jeanlui.demactor.provider.utils.ViewComponentProviderUtils;
import com.jeanlui.demactor.provider.contract.ViewComponentContract;

/** ViewComponent create fragment.
 *
 * This fragment gives you an interface to edit a ViewComponent.
 *
 * @see android.app.Fragment
 */
public class ViewComponentEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected ViewComponent model = new ViewComponent();

    /** curr.fields View. */
    /** stringField View. */
    protected EditText stringFieldView;
    /** text View. */
    protected EditText textView;
    /** dateTime DateTime View. */
    protected DateTimeWidget dateTimeView;
    /** date Date View. */
    protected DateWidget dateView;
    /** time Time View. */
    protected TimeWidget timeView;
    /** login View. */
    protected EditText loginView;
    /** password View. */
    protected EditText passwordView;
    /** email View. */
    protected EditText emailView;
    /** phone View. */
    protected EditText phoneView;
    /** city View. */
    protected EditText cityView;
    /** zipCode View. */
    protected EditText zipCodeView;
    /** country View. */
    protected EditText countryView;
    /** byteField View. */
    protected EditText byteFieldView;
    /** charField View. */
    protected EditText charFieldView;
    /** shortField View. */
    protected EditText shortFieldView;
    /** character View. */
    protected EditText characterView;
    /** choice View. */
    protected EnumSpinner choiceView;
    /** booleanObject View. */
    protected CheckBox booleanObjectView;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.stringFieldView = (EditText) view.findViewById(
                R.id.viewcomponent_stringfield);
        this.textView = (EditText) view.findViewById(
                R.id.viewcomponent_text);
        this.dateTimeView = (DateTimeWidget) view.findViewById(
                R.id.viewcomponent_datetime);
        this.dateView = (DateWidget) view.findViewById(
                R.id.viewcomponent_date);
        this.timeView = (TimeWidget) view.findViewById(
                R.id.viewcomponent_time);
        this.loginView = (EditText) view.findViewById(
                R.id.viewcomponent_login);
        this.passwordView = (EditText) view.findViewById(
                R.id.viewcomponent_password);
        this.emailView = (EditText) view.findViewById(
                R.id.viewcomponent_email);
        this.phoneView = (EditText) view.findViewById(
                R.id.viewcomponent_phone);
        this.cityView = (EditText) view.findViewById(
                R.id.viewcomponent_city);
        this.zipCodeView = (EditText) view.findViewById(
                R.id.viewcomponent_zipcode);
        this.countryView = (EditText) view.findViewById(
                R.id.viewcomponent_country);
        this.byteFieldView = (EditText) view.findViewById(
                R.id.viewcomponent_bytefield);
        this.charFieldView = (EditText) view.findViewById(
                R.id.viewcomponent_charfield);
        this.shortFieldView = (EditText) view.findViewById(
                R.id.viewcomponent_shortfield);
        this.characterView = (EditText) view.findViewById(
                R.id.viewcomponent_character);
        this.choiceView = (EnumSpinner) view.findViewById(
                R.id.viewcomponent_choice);
        this.choiceView.setEnum(Choice.class);
        this.booleanObjectView = (CheckBox) view.findViewById(
                R.id.viewcomponent_booleanobject);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getStringField() != null) {
            this.stringFieldView.setText(this.model.getStringField());
        }
        if (this.model.getText() != null) {
            this.textView.setText(this.model.getText());
        }
        if (this.model.getDateTime() != null) {
            this.dateTimeView.setDateTime(this.model.getDateTime());
        }
        if (this.model.getDate() != null) {
            this.dateView.setDate(this.model.getDate());
        }
        if (this.model.getTime() != null) {
            this.timeView.setTime(this.model.getTime());
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
            this.choiceView.setSelectedItem(this.model.getChoice());
        }
        if (this.model.isBooleanObject() != null) {
            this.booleanObjectView.setChecked(this.model.isBooleanObject());
        }


    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setStringField(this.stringFieldView.getEditableText().toString());

        this.model.setText(this.textView.getEditableText().toString());

        this.model.setDateTime(this.dateTimeView.getDateTime());

        this.model.setDate(this.dateView.getDate());

        this.model.setTime(this.timeView.getTime());

        this.model.setLogin(this.loginView.getEditableText().toString());

        this.model.setPassword(this.passwordView.getEditableText().toString());

        this.model.setEmail(this.emailView.getEditableText().toString());

        this.model.setPhone(this.phoneView.getEditableText().toString());

        this.model.setCity(this.cityView.getEditableText().toString());

        this.model.setZipCode(Integer.parseInt(
                    this.zipCodeView.getEditableText().toString()));

        this.model.setCountry(this.countryView.getEditableText().toString());

        this.model.setByteField(Byte.parseByte(
                    this.byteFieldView.getEditableText().toString()));

        this.model.setCharField(
                    this.charFieldView.getEditableText().toString().charAt(0));

        this.model.setShortField(Short.parseShort(
                    this.shortFieldView.getEditableText().toString()));

        this.model.setCharacter(
                    this.characterView.getEditableText().toString().charAt(0));

        this.model.setChoice((Choice) this.choiceView.getSelectedItem());

        this.model.setBooleanObject(this.booleanObjectView.isChecked());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.stringFieldView.getText().toString().trim())) {
            error = R.string.viewcomponent_stringfield_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.textView.getText().toString().trim())) {
            error = R.string.viewcomponent_text_invalid_field_error;
        }
        if (this.dateTimeView.getDateTime() == null) {
            error = R.string.viewcomponent_datetime_invalid_field_error;
        }
        if (this.dateView.getDate() == null) {
            error = R.string.viewcomponent_date_invalid_field_error;
        }
        if (this.timeView.getTime() == null) {
            error = R.string.viewcomponent_time_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.loginView.getText().toString().trim())) {
            error = R.string.viewcomponent_login_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.passwordView.getText().toString().trim())) {
            error = R.string.viewcomponent_password_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.emailView.getText().toString().trim())) {
            error = R.string.viewcomponent_email_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.phoneView.getText().toString().trim())) {
            error = R.string.viewcomponent_phone_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.cityView.getText().toString().trim())) {
            error = R.string.viewcomponent_city_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.zipCodeView.getText().toString().trim())) {
            error = R.string.viewcomponent_zipcode_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.countryView.getText().toString().trim())) {
            error = R.string.viewcomponent_country_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.byteFieldView.getText().toString().trim())) {
            error = R.string.viewcomponent_bytefield_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.charFieldView.getText().toString().trim())) {
            error = R.string.viewcomponent_charfield_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.shortFieldView.getText().toString().trim())) {
            error = R.string.viewcomponent_shortfield_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.characterView.getText().toString().trim())) {
            error = R.string.viewcomponent_character_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_viewcomponent_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (ViewComponent) intent.getParcelableExtra(
                ViewComponentContract.PARCEL);

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
        private final ViewComponent entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final ViewComponentEditFragment fragment,
                    final ViewComponent entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.viewcomponent_progress_save_title),
                    this.ctx.getString(
                            R.string.viewcomponent_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new ViewComponentProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("ViewComponentEditFragment", e.getMessage());
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
                        R.string.viewcomponent_error_edit));
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
