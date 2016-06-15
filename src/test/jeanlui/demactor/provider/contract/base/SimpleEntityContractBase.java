/**************************************************************************
 * SimpleEntityContractBase.java, demactor Android
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

import com.jeanlui.demactor.entity.SimpleEntity;



import com.jeanlui.demactor.provider.contract.SimpleEntityContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class SimpleEntityContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            SimpleEntityContract.TABLE_NAME + "." + COL_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "SimpleEntity";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "SimpleEntity";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        SimpleEntityContract.COL_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        SimpleEntityContract.ALIASED_COL_ID
    };


    /**
     * Converts a SimpleEntity into a content values.
     *
     * @param item The SimpleEntity to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final SimpleEntity item) {
        final ContentValues result = new ContentValues();

             result.put(SimpleEntityContract.COL_ID,
                String.valueOf(item.getId()));


        return result;
    }

    /**
     * Converts a Cursor into a SimpleEntity.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted SimpleEntity
     */
    public static SimpleEntity cursorToItem(final android.database.Cursor cursor) {
        SimpleEntity result = new SimpleEntity();
        SimpleEntityContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to SimpleEntity entity.
     * @param cursor Cursor object
     * @param result SimpleEntity entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final SimpleEntity result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(SimpleEntityContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of SimpleEntity entity.
     * @param cursor Cursor object
     * @return Array of SimpleEntity entity
     */
    public static ArrayList<SimpleEntity> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<SimpleEntity> result = new ArrayList<SimpleEntity>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            SimpleEntity item;
            do {
                item = SimpleEntityContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
