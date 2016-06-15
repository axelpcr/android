/**************************************************************************
 * ClientContractBase.java, demactor Android
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

import com.jeanlui.demactor.entity.Client;


import com.jeanlui.demactor.provider.contract.UserContract;

import com.jeanlui.demactor.provider.contract.ClientContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ClientContractBase {


        /** adress. */
    public static final String COL_ADRESS =
            "adress";
    /** Alias. */
    public static final String ALIASED_COL_ADRESS =
            ClientContract.TABLE_NAME + "." + COL_ADRESS;

    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            ClientContract.TABLE_NAME + "." + COL_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Client";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Client";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        ClientContract.COL_ADRESS,
        
        UserContract.COL_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        ClientContract.ALIASED_COL_ADRESS,
        
        UserContract.ALIASED_COL_ID,
        
        UserContract.ALIASED_COL_LOGIN,
        
        UserContract.ALIASED_COL_PASSWORD,
        
        UserContract.ALIASED_COL_FIRSTNAME,
        
        UserContract.ALIASED_COL_LASTNAME,
        
        UserContract.ALIASED_COL_CREATEDAT,
        
        UserContract.ALIASED_COL_BIRTHDATE,
        
        UserContract.ALIASED_COL_USERGROUP_ID,
        
        UserContract.ALIASED_COL_TITLE,
        
        UserContract.ALIASED_COL_FULLNAME,
        
    };


    /**
     * Converts a Client into a content values.
     *
     * @param item The Client to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Client item) {
        final ContentValues result = new ContentValues();
        result.putAll(UserContract.itemToContentValues(item));

             if (item.getAdress() != null) {
                result.put(ClientContract.COL_ADRESS,
                    item.getAdress());
            }

             result.put(UserContract.COL_ID,
                String.valueOf(item.getId()));


        return result;
    }

    /**
     * Converts a Cursor into a Client.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Client
     */
    public static Client cursorToItem(final android.database.Cursor cursor) {
        Client result = new Client();
        ClientContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Client entity.
     * @param cursor Cursor object
     * @param result Client entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Client result) {
        if (cursor.getCount() != 0) {
            UserContract.cursorToItem(cursor, result);

            int index;

            index = cursor.getColumnIndex(ClientContract.COL_ADRESS);

            if (index > -1) {
                result.setAdress(cursor.getString(index));
            }
            index = cursor.getColumnIndex(UserContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Client entity.
     * @param cursor Cursor object
     * @return Array of Client entity
     */
    public static ArrayList<Client> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Client> result = new ArrayList<Client>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Client item;
            do {
                item = ClientContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
