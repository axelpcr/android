/**************************************************************************
 * ViewComponentContractBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import com.jeanlui.demactor.entity.ViewComponent;
import com.jeanlui.demactor.entity.ViewComponent.Choice;



import com.jeanlui.demactor.provider.contract.ViewComponentContract;
import com.jeanlui.demactor.harmony.util.DateUtils;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ViewComponentContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            ViewComponentContract.TABLE_NAME + "." + COL_ID;

    /** stringField. */
    public static final String COL_STRINGFIELD =
            "stringField";
    /** Alias. */
    public static final String ALIASED_COL_STRINGFIELD =
            ViewComponentContract.TABLE_NAME + "." + COL_STRINGFIELD;

    /** text. */
    public static final String COL_TEXT =
            "text";
    /** Alias. */
    public static final String ALIASED_COL_TEXT =
            ViewComponentContract.TABLE_NAME + "." + COL_TEXT;

    /** dateTime. */
    public static final String COL_DATETIME =
            "dateTime";
    /** Alias. */
    public static final String ALIASED_COL_DATETIME =
            ViewComponentContract.TABLE_NAME + "." + COL_DATETIME;

    /** date. */
    public static final String COL_DATE =
            "date";
    /** Alias. */
    public static final String ALIASED_COL_DATE =
            ViewComponentContract.TABLE_NAME + "." + COL_DATE;

    /** time. */
    public static final String COL_TIME =
            "time";
    /** Alias. */
    public static final String ALIASED_COL_TIME =
            ViewComponentContract.TABLE_NAME + "." + COL_TIME;

    /** login. */
    public static final String COL_LOGIN =
            "login";
    /** Alias. */
    public static final String ALIASED_COL_LOGIN =
            ViewComponentContract.TABLE_NAME + "." + COL_LOGIN;

    /** password. */
    public static final String COL_PASSWORD =
            "password";
    /** Alias. */
    public static final String ALIASED_COL_PASSWORD =
            ViewComponentContract.TABLE_NAME + "." + COL_PASSWORD;

    /** email. */
    public static final String COL_EMAIL =
            "email";
    /** Alias. */
    public static final String ALIASED_COL_EMAIL =
            ViewComponentContract.TABLE_NAME + "." + COL_EMAIL;

    /** phone. */
    public static final String COL_PHONE =
            "phone";
    /** Alias. */
    public static final String ALIASED_COL_PHONE =
            ViewComponentContract.TABLE_NAME + "." + COL_PHONE;

    /** city. */
    public static final String COL_CITY =
            "city";
    /** Alias. */
    public static final String ALIASED_COL_CITY =
            ViewComponentContract.TABLE_NAME + "." + COL_CITY;

    /** zipCode. */
    public static final String COL_ZIPCODE =
            "zipCode";
    /** Alias. */
    public static final String ALIASED_COL_ZIPCODE =
            ViewComponentContract.TABLE_NAME + "." + COL_ZIPCODE;

    /** country. */
    public static final String COL_COUNTRY =
            "country";
    /** Alias. */
    public static final String ALIASED_COL_COUNTRY =
            ViewComponentContract.TABLE_NAME + "." + COL_COUNTRY;

    /** byteField. */
    public static final String COL_BYTEFIELD =
            "byteField";
    /** Alias. */
    public static final String ALIASED_COL_BYTEFIELD =
            ViewComponentContract.TABLE_NAME + "." + COL_BYTEFIELD;

    /** charField. */
    public static final String COL_CHARFIELD =
            "charField";
    /** Alias. */
    public static final String ALIASED_COL_CHARFIELD =
            ViewComponentContract.TABLE_NAME + "." + COL_CHARFIELD;

    /** shortField. */
    public static final String COL_SHORTFIELD =
            "shortField";
    /** Alias. */
    public static final String ALIASED_COL_SHORTFIELD =
            ViewComponentContract.TABLE_NAME + "." + COL_SHORTFIELD;

    /** character. */
    public static final String COL_CHARACTER =
            "character";
    /** Alias. */
    public static final String ALIASED_COL_CHARACTER =
            ViewComponentContract.TABLE_NAME + "." + COL_CHARACTER;

    /** choice. */
    public static final String COL_CHOICE =
            "choice";
    /** Alias. */
    public static final String ALIASED_COL_CHOICE =
            ViewComponentContract.TABLE_NAME + "." + COL_CHOICE;

    /** booleanObject. */
    public static final String COL_BOOLEANOBJECT =
            "booleanObject";
    /** Alias. */
    public static final String ALIASED_COL_BOOLEANOBJECT =
            ViewComponentContract.TABLE_NAME + "." + COL_BOOLEANOBJECT;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "ViewComponent";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "ViewComponent";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        ViewComponentContract.COL_ID,
        
        ViewComponentContract.COL_STRINGFIELD,
        
        ViewComponentContract.COL_TEXT,
        
        ViewComponentContract.COL_DATETIME,
        
        ViewComponentContract.COL_DATE,
        
        ViewComponentContract.COL_TIME,
        
        ViewComponentContract.COL_LOGIN,
        
        ViewComponentContract.COL_PASSWORD,
        
        ViewComponentContract.COL_EMAIL,
        
        ViewComponentContract.COL_PHONE,
        
        ViewComponentContract.COL_CITY,
        
        ViewComponentContract.COL_ZIPCODE,
        
        ViewComponentContract.COL_COUNTRY,
        
        ViewComponentContract.COL_BYTEFIELD,
        
        ViewComponentContract.COL_CHARFIELD,
        
        ViewComponentContract.COL_SHORTFIELD,
        
        ViewComponentContract.COL_CHARACTER,
        
        ViewComponentContract.COL_CHOICE,
        
        ViewComponentContract.COL_BOOLEANOBJECT
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        ViewComponentContract.ALIASED_COL_ID,
        
        ViewComponentContract.ALIASED_COL_STRINGFIELD,
        
        ViewComponentContract.ALIASED_COL_TEXT,
        
        ViewComponentContract.ALIASED_COL_DATETIME,
        
        ViewComponentContract.ALIASED_COL_DATE,
        
        ViewComponentContract.ALIASED_COL_TIME,
        
        ViewComponentContract.ALIASED_COL_LOGIN,
        
        ViewComponentContract.ALIASED_COL_PASSWORD,
        
        ViewComponentContract.ALIASED_COL_EMAIL,
        
        ViewComponentContract.ALIASED_COL_PHONE,
        
        ViewComponentContract.ALIASED_COL_CITY,
        
        ViewComponentContract.ALIASED_COL_ZIPCODE,
        
        ViewComponentContract.ALIASED_COL_COUNTRY,
        
        ViewComponentContract.ALIASED_COL_BYTEFIELD,
        
        ViewComponentContract.ALIASED_COL_CHARFIELD,
        
        ViewComponentContract.ALIASED_COL_SHORTFIELD,
        
        ViewComponentContract.ALIASED_COL_CHARACTER,
        
        ViewComponentContract.ALIASED_COL_CHOICE,
        
        ViewComponentContract.ALIASED_COL_BOOLEANOBJECT
    };


    /**
     * Converts a ViewComponent into a content values.
     *
     * @param item The ViewComponent to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final ViewComponent item) {
        final ContentValues result = new ContentValues();

             result.put(ViewComponentContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getStringField() != null) {
                result.put(ViewComponentContract.COL_STRINGFIELD,
                    item.getStringField());
            }

             if (item.getText() != null) {
                result.put(ViewComponentContract.COL_TEXT,
                    item.getText());
            }

             if (item.getDateTime() != null) {
                result.put(ViewComponentContract.COL_DATETIME,
                    item.getDateTime().toString(ISODateTimeFormat.dateTime()));
            }

             if (item.getDate() != null) {
                result.put(ViewComponentContract.COL_DATE,
                    item.getDate().toString(ISODateTimeFormat.dateTime()));
            }

             if (item.getTime() != null) {
                result.put(ViewComponentContract.COL_TIME,
                    item.getTime().toString(ISODateTimeFormat.dateTime()));
            }

             if (item.getLogin() != null) {
                result.put(ViewComponentContract.COL_LOGIN,
                    item.getLogin());
            }

             if (item.getPassword() != null) {
                result.put(ViewComponentContract.COL_PASSWORD,
                    item.getPassword());
            }

             if (item.getEmail() != null) {
                result.put(ViewComponentContract.COL_EMAIL,
                    item.getEmail());
            }

             if (item.getPhone() != null) {
                result.put(ViewComponentContract.COL_PHONE,
                    item.getPhone());
            }

             if (item.getCity() != null) {
                result.put(ViewComponentContract.COL_CITY,
                    item.getCity());
            }

             result.put(ViewComponentContract.COL_ZIPCODE,
                String.valueOf(item.getZipCode()));

             if (item.getCountry() != null) {
                result.put(ViewComponentContract.COL_COUNTRY,
                    item.getCountry());
            }

             result.put(ViewComponentContract.COL_BYTEFIELD,
                String.valueOf(item.getByteField()));

             result.put(ViewComponentContract.COL_CHARFIELD,
                String.valueOf(item.getCharField()));

             result.put(ViewComponentContract.COL_SHORTFIELD,
                String.valueOf(item.getShortField()));

             if (item.getCharacter() != null) {
                result.put(ViewComponentContract.COL_CHARACTER,
                    String.valueOf(item.getCharacter()));
            }

             if (item.getChoice() != null) {
                result.put(ViewComponentContract.COL_CHOICE,
                    item.getChoice().getValue());
            }

             if (item.isBooleanObject() != null) {
                result.put(ViewComponentContract.COL_BOOLEANOBJECT,
                item.isBooleanObject() ? 1 : 0);

            }


        return result;
    }

    /**
     * Converts a Cursor into a ViewComponent.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted ViewComponent
     */
    public static ViewComponent cursorToItem(final android.database.Cursor cursor) {
        ViewComponent result = new ViewComponent();
        ViewComponentContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to ViewComponent entity.
     * @param cursor Cursor object
     * @param result ViewComponent entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final ViewComponent result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(ViewComponentContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_STRINGFIELD);

            if (index > -1) {
                result.setStringField(cursor.getString(index));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_TEXT);

            if (index > -1) {
                result.setText(cursor.getString(index));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_DATETIME);

            if (index > -1) {
                final DateTime dtDateTime =
                        DateUtils.formatISOStringToDateTime(cursor.getString(index));
                if (dtDateTime != null) {
                        result.setDateTime(dtDateTime);
                } else {
                    result.setDateTime(new DateTime());
                }
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_DATE);

            if (index > -1) {
                final DateTime dtDate =
                        DateUtils.formatISOStringToDateTime(cursor.getString(index));
                if (dtDate != null) {
                        result.setDate(dtDate);
                } else {
                    result.setDate(new DateTime());
                }
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_TIME);

            if (index > -1) {
                final DateTime dtTime =
                        DateUtils.formatISOStringToDateTime(cursor.getString(index));
                if (dtTime != null) {
                        result.setTime(dtTime);
                } else {
                    result.setTime(new DateTime());
                }
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_LOGIN);

            if (index > -1) {
                result.setLogin(cursor.getString(index));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_PASSWORD);

            if (index > -1) {
                result.setPassword(cursor.getString(index));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_EMAIL);

            if (index > -1) {
                result.setEmail(cursor.getString(index));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_PHONE);

            if (index > -1) {
                result.setPhone(cursor.getString(index));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_CITY);

            if (index > -1) {
                result.setCity(cursor.getString(index));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_ZIPCODE);

            if (index > -1) {
                result.setZipCode(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_COUNTRY);

            if (index > -1) {
                result.setCountry(cursor.getString(index));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_BYTEFIELD);

            if (index > -1) {
                result.setByteField(Byte.valueOf(cursor.getString(index)));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_CHARFIELD);

            if (index > -1) {
                String charFieldDB = cursor.getString(index);
                if (charFieldDB != null
                    && charFieldDB.length() > 0) {
                    result.setCharField(charFieldDB.charAt(0));
                }
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_SHORTFIELD);

            if (index > -1) {
                result.setShortField(cursor.getShort(index));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_CHARACTER);

            if (index > -1) {
                String characterDB = cursor.getString(index);
                if (characterDB != null
                    && characterDB.length() > 0) {
                    result.setCharacter(characterDB.charAt(0));
                }
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_CHOICE);

            if (index > -1) {
                result.setChoice(Choice.fromValue(cursor.getInt(index)));
            }
            index = cursor.getColumnIndex(ViewComponentContract.COL_BOOLEANOBJECT);

            if (index > -1) {
                result.setBooleanObject(cursor.getInt(index) == 1);
            }

        }
    }

    /**
     * Convert Cursor of database to Array of ViewComponent entity.
     * @param cursor Cursor object
     * @return Array of ViewComponent entity
     */
    public static ArrayList<ViewComponent> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<ViewComponent> result = new ArrayList<ViewComponent>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            ViewComponent item;
            do {
                item = ViewComponentContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
