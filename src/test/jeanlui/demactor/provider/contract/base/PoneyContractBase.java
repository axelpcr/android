/**************************************************************************
 * PoneyContractBase.java, demactor Android
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

import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.Jockey;
import com.jeanlui.demactor.entity.Score;



import com.jeanlui.demactor.provider.contract.PoneyContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PoneyContractBase {


        /** idlioEm1. */
    public static final String COL_IDLIOEM1 =
            "idlioEm1";
    /** Alias. */
    public static final String ALIASED_COL_IDLIOEM1 =
            PoneyContract.TABLE_NAME + "." + COL_IDLIOEM1;

    /** iomAiome1. */
    public static final String COL_IOMAIOME1 =
            "iomAiome1";
    /** Alias. */
    public static final String ALIASED_COL_IOMAIOME1 =
            PoneyContract.TABLE_NAME + "." + COL_IOMAIOME1;

    /** scorvbnBe1_idFD1. */
    public static final String COL_SCORVBNBE1_IDFD1 =
            "scorvbnBe1_idFD1";
    /** Alias. */
    public static final String ALIASED_COL_SCORVBNBE1_IDFD1 =
            PoneyContract.TABLE_NAME + "." + COL_SCORVBNBE1_IDFD1;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Poney";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Poney";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PoneyContract.COL_IDLIOEM1,
        
        PoneyContract.COL_IOMAIOME1,
        
        PoneyContract.COL_SCORVBNBE1_IDFD1
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PoneyContract.ALIASED_COL_IDLIOEM1,
        
        PoneyContract.ALIASED_COL_IOMAIOME1,
        
        
        PoneyContract.ALIASED_COL_SCORVBNBE1_IDFD1
    };


    /**
     * Converts a Poney into a content values.
     *
     * @param item The Poney to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Poney item) {
        final ContentValues result = new ContentValues();

             result.put(PoneyContract.COL_IDLIOEM1,
                String.valueOf(item.getIdlioEm1()));

             if (item.getIomAiome1() != null) {
                result.put(PoneyContract.COL_IOMAIOME1,
                    item.getIomAiome1());
            }

              if (item.getScorvbnBe1() != null) {
                result.put(PoneyContract.COL_SCORVBNBE1_IDFD1,
                    item.getScorvbnBe1().getIdFD1());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Poney.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Poney
     */
    public static Poney cursorToItem(final android.database.Cursor cursor) {
        Poney result = new Poney();
        PoneyContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Poney entity.
     * @param cursor Cursor object
     * @param result Poney entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Poney result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PoneyContract.COL_IDLIOEM1);

            if (index > -1) {
                result.setIdlioEm1(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PoneyContract.COL_IOMAIOME1);

            if (index > -1) {
                result.setIomAiome1(cursor.getString(index));
            }
            if (result.getScorvbnBe1() == null) {
                final Score scorvbnBe1 = new Score();
                index = cursor.getColumnIndex(PoneyContract.COL_SCORVBNBE1_IDFD1);

                if (index > -1) {
                    scorvbnBe1.setIdFD1(cursor.getInt(index));
                    result.setScorvbnBe1(scorvbnBe1);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Poney entity.
     * @param cursor Cursor object
     * @return Array of Poney entity
     */
    public static ArrayList<Poney> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Poney> result = new ArrayList<Poney>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Poney item;
            do {
                item = PoneyContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
