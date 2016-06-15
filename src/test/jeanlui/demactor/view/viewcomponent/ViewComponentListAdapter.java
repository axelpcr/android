/**************************************************************************
 * ViewComponentListAdapter.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.view.viewcomponent;


import com.jeanlui.demactor.R;
import android.widget.CheckBox;
import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.entity.ViewComponent;
import com.jeanlui.demactor.harmony.view.HarmonyCursorAdapter;
import com.jeanlui.demactor.harmony.view.HarmonyViewHolder;
import com.jeanlui.demactor.provider.contract.ViewComponentContract;

/**
 * List adapter for ViewComponent entity.
 */
public class ViewComponentListAdapter extends HarmonyCursorAdapter<ViewComponent> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public ViewComponentListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public ViewComponentListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected ViewComponent cursorToItem(Cursor cursor) {
        return ViewComponentContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return ViewComponentContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<ViewComponent> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<ViewComponent> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_viewcomponent);
        }

        /**
         * Populate row with a {@link ViewComponent}.
         *
         * @param model {@link ViewComponent} data
         */
        public void populate(final ViewComponent model) {
            TextView stringFieldView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_stringfield);
                    
            TextView textView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_text);
                    
            TextView dateTimeView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_datetime);
                    
            TextView dateView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_date);
                    
            TextView timeView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_time);
                    
            TextView loginView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_login);
                    
            TextView passwordView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_password);
                    
            TextView emailView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_email);
                    
            TextView phoneView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_phone);
                    
            TextView cityView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_city);
                    
            TextView zipCodeView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_zipcode);
                    
            TextView countryView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_country);
                    
            TextView byteFieldView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_bytefield);
                    
            TextView charFieldView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_charfield);
                    
            TextView shortFieldView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_shortfield);
                    
            TextView characterView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_character);
                    
            TextView choiceView = (TextView) this.getView().findViewById(
                    R.id.row_viewcomponent_choice);
                    
            CheckBox booleanObjectView = (CheckBox) this.getView().findViewById(
                    R.id.row_viewcomponent_booleanobject);
            booleanObjectView.setEnabled(false);
            

            if (model.getStringField() != null) {
                stringFieldView.setText(model.getStringField());
            }
            if (model.getText() != null) {
                textView.setText(model.getText());
            }
            if (model.getDateTime() != null) {
                dateTimeView.setText(DateUtils.formatDateTimeToString(model.getDateTime()));
            }
            if (model.getDate() != null) {
                dateView.setText(DateUtils.formatDateToString(model.getDate()));
            }
            if (model.getTime() != null) {
                timeView.setText(DateUtils.formatTimeToString(model.getTime()));
            }
            if (model.getLogin() != null) {
                loginView.setText(model.getLogin());
            }
            if (model.getPassword() != null) {
                passwordView.setText(model.getPassword());
            }
            if (model.getEmail() != null) {
                emailView.setText(model.getEmail());
            }
            if (model.getPhone() != null) {
                phoneView.setText(model.getPhone());
            }
            if (model.getCity() != null) {
                cityView.setText(model.getCity());
            }
            zipCodeView.setText(String.valueOf(model.getZipCode()));
            if (model.getCountry() != null) {
                countryView.setText(model.getCountry());
            }
            byteFieldView.setText(String.valueOf(model.getByteField()));
            charFieldView.setText(String.valueOf(model.getCharField()));
            shortFieldView.setText(String.valueOf(model.getShortField()));
            if (model.getCharacter() != null) {
                characterView.setText(String.valueOf(model.getCharacter()));
            }
            if (model.getChoice() != null) {
                choiceView.setText(model.getChoice().name());
            }
            if (model.isBooleanObject() != null) {
                booleanObjectView.setChecked(model.isBooleanObject());
            }
        }
    }
}
