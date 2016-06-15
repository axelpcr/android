/**************************************************************************
 * ScoreContractBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.jeanlui.demactor.entity.Score;
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.User;



import com.jeanlui.demactor.provider.contract.ScoreContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ScoreContractBase {


        /** idFD1. */
    public static final String COL_IDFD1 =
            "idFD1";
    /** Alias. */
    public static final String ALIASED_COL_IDFD1 =
            ScoreContract.TABLE_NAME + "." + COL_IDFD1;

    /** moneFGHFGy1. */
    public static final String COL_MONEFGHFGY1 =
            "moneFGHFGy1";
    /** Alias. */
    public static final String ALIASED_COL_MONEFGHFGY1 =
            ScoreContract.TABLE_NAME + "." + COL_MONEFGHFGY1;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Score";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Score";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        ScoreContract.COL_IDFD1,
        
        ScoreContract.COL_MONEFGHFGY1,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        ScoreContract.ALIASED_COL_IDFD1,
        
        ScoreContract.ALIASED_COL_MONEFGHFGY1,
        
        
    };


    /**
     * Converts a Score into a content values.
     *
     * @param item The Score to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Score item) {
        final ContentValues result = new ContentValues();

             result.put(ScoreContract.COL_IDFD1,
                String.valueOf(item.getIdFD1()));

             result.put(ScoreContract.COL_MONEFGHFGY1,
                String.valueOf(item.getMoneFGHFGy1()));

  
        return result;
    }

    /**
     * Converts a Cursor into a Score.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Score
     */
    public static Score cursorToItem(final android.database.Cursor cursor) {
        Score result = new Score();
        ScoreContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Score entity.
     * @param cursor Cursor object
     * @param result Score entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Score result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(ScoreContract.COL_IDFD1);

            if (index > -1) {
                result.setIdFD1(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(ScoreContract.COL_MONEFGHFGY1);

            if (index > -1) {
                result.setMoneFGHFGy1(cursor.getInt(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Score entity.
     * @param cursor Cursor object
     * @return Array of Score entity
     */
    public static ArrayList<Score> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Score> result = new ArrayList<Score>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Score item;
            do {
                item = ScoreContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
