/**************************************************************************
 * JockeyContractBase.java, demactor Android
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

import com.jeanlui.demactor.entity.Jockey;
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.User;



import com.jeanlui.demactor.provider.contract.JockeyContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class JockeyContractBase {


        /** fbgDFbdf. */
    public static final String COL_FBGDFBDF =
            "fbgDFbdf";
    /** Alias. */
    public static final String ALIASED_COL_FBGDFBDF =
            JockeyContract.TABLE_NAME + "." + COL_FBGDFBDF;

    /** dfdfgdDDfgdfg. */
    public static final String COL_DFDFGDDDFGDFG =
            "dfdfgdDDfgdfg";
    /** Alias. */
    public static final String ALIASED_COL_DFDFGDDDFGDFG =
            JockeyContract.TABLE_NAME + "." + COL_DFDFGDDDFGDFG;

    /** dfgdfgdfgdfFg. */
    public static final String COL_DFGDFGDFGDFFG =
            "dfgdfgdfgdfFg";
    /** Alias. */
    public static final String ALIASED_COL_DFGDFGDFGDFFG =
            JockeyContract.TABLE_NAME + "." + COL_DFGDFGDFGDFFG;

    /** iuytrezBa_id1HNY. */
    public static final String COL_IUYTREZBA_ID1HNY =
            "iuytrezBa_id1HNY";
    /** Alias. */
    public static final String ALIASED_COL_IUYTREZBA_ID1HNY =
            JockeyContract.TABLE_NAME + "." + COL_IUYTREZBA_ID1HNY;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Jockey";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Jockey";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        JockeyContract.COL_FBGDFBDF,
        
        JockeyContract.COL_DFDFGDDDFGDFG,
        
        JockeyContract.COL_DFGDFGDFGDFFG,
        
        JockeyContract.COL_IUYTREZBA_ID1HNY
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        JockeyContract.ALIASED_COL_FBGDFBDF,
        
        JockeyContract.ALIASED_COL_DFDFGDDDFGDFG,
        
        JockeyContract.ALIASED_COL_DFGDFGDFGDFFG,
        
        
        JockeyContract.ALIASED_COL_IUYTREZBA_ID1HNY
    };


    /**
     * Converts a Jockey into a content values.
     *
     * @param item The Jockey to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Jockey item) {
        final ContentValues result = new ContentValues();

             result.put(JockeyContract.COL_FBGDFBDF,
                String.valueOf(item.getFbgDFbdf()));

             if (item.getDfdfgdDDfgdfg() != null) {
                result.put(JockeyContract.COL_DFDFGDDDFGDFG,
                    item.getDfdfgdDDfgdfg());
            }

             if (item.getDfgdfgdfgdfFg() != null) {
                result.put(JockeyContract.COL_DFGDFGDFGDFFG,
                    item.getDfgdfgdfgdfFg());
            }

              if (item.getIuytrezBa() != null) {
                result.put(JockeyContract.COL_IUYTREZBA_ID1HNY,
                    item.getIuytrezBa().getId1HNY());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Jockey.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Jockey
     */
    public static Jockey cursorToItem(final android.database.Cursor cursor) {
        Jockey result = new Jockey();
        JockeyContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Jockey entity.
     * @param cursor Cursor object
     * @param result Jockey entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Jockey result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(JockeyContract.COL_FBGDFBDF);

            if (index > -1) {
                result.setFbgDFbdf(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(JockeyContract.COL_DFDFGDDDFGDFG);

            if (index > -1) {
                result.setDfdfgdDDfgdfg(cursor.getString(index));
            }
            index = cursor.getColumnIndex(JockeyContract.COL_DFGDFGDFGDFFG);

            if (index > -1) {
                result.setDfgdfgdfgdfFg(cursor.getString(index));
            }
            if (result.getIuytrezBa() == null) {
                final User iuytrezBa = new User();
                index = cursor.getColumnIndex(JockeyContract.COL_IUYTREZBA_ID1HNY);

                if (index > -1) {
                    iuytrezBa.setId1HNY(cursor.getInt(index));
                    result.setIuytrezBa(iuytrezBa);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Jockey entity.
     * @param cursor Cursor object
     * @return Array of Jockey entity
     */
    public static ArrayList<Jockey> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Jockey> result = new ArrayList<Jockey>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Jockey item;
            do {
                item = JockeyContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
