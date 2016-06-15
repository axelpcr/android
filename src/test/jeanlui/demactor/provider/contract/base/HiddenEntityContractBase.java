/**************************************************************************
 * HiddenEntityContractBase.java, demactor Android
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

import com.jeanlui.demactor.entity.HiddenEntity;



import com.jeanlui.demactor.provider.contract.HiddenEntityContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class HiddenEntityContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            HiddenEntityContract.TABLE_NAME + "." + COL_ID;

    /** content. */
    public static final String COL_CONTENT =
            "content";
    /** Alias. */
    public static final String ALIASED_COL_CONTENT =
            HiddenEntityContract.TABLE_NAME + "." + COL_CONTENT;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "HiddenEntity";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "HiddenEntity";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        HiddenEntityContract.COL_ID,
        
        HiddenEntityContract.COL_CONTENT
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        HiddenEntityContract.ALIASED_COL_ID,
        
        HiddenEntityContract.ALIASED_COL_CONTENT
    };


    /**
     * Converts a HiddenEntity into a content values.
     *
     * @param item The HiddenEntity to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final HiddenEntity item) {
        final ContentValues result = new ContentValues();

             result.put(HiddenEntityContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getContent() != null) {
                result.put(HiddenEntityContract.COL_CONTENT,
                    item.getContent());
            }


        return result;
    }

    /**
     * Converts a Cursor into a HiddenEntity.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted HiddenEntity
     */
    public static HiddenEntity cursorToItem(final android.database.Cursor cursor) {
        HiddenEntity result = new HiddenEntity();
        HiddenEntityContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to HiddenEntity entity.
     * @param cursor Cursor object
     * @param result HiddenEntity entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final HiddenEntity result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(HiddenEntityContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(HiddenEntityContract.COL_CONTENT);

            if (index > -1) {
                result.setContent(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of HiddenEntity entity.
     * @param cursor Cursor object
     * @return Array of HiddenEntity entity
     */
    public static ArrayList<HiddenEntity> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<HiddenEntity> result = new ArrayList<HiddenEntity>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            HiddenEntity item;
            do {
                item = HiddenEntityContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
