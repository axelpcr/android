/**************************************************************************
 * UserContractBase.java, demactor Android
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

import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.Score;
import com.jeanlui.demactor.entity.Jockey;



import com.jeanlui.demactor.provider.contract.UserContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class UserContractBase {


        /** id1HNY. */
    public static final String COL_ID1HNY =
            "id1HNY";
    /** Alias. */
    public static final String ALIASED_COL_ID1HNY =
            UserContract.TABLE_NAME + "." + COL_ID1HNY;

    /** naFGHme1. */
    public static final String COL_NAFGHME1 =
            "naFGHme1";
    /** Alias. */
    public static final String ALIASED_COL_NAFGHME1 =
            UserContract.TABLE_NAME + "." + COL_NAFGHME1;

    /** surnFGHame1. */
    public static final String COL_SURNFGHAME1 =
            "surnFGHame1";
    /** Alias. */
    public static final String ALIASED_COL_SURNFGHAME1 =
            UserContract.TABLE_NAME + "." + COL_SURNFGHAME1;

    /** scoFGHre1_idFD1. */
    public static final String COL_SCOFGHRE1_IDFD1 =
            "scoFGHre1_idFD1";
    /** Alias. */
    public static final String ALIASED_COL_SCOFGHRE1_IDFD1 =
            UserContract.TABLE_NAME + "." + COL_SCOFGHRE1_IDFD1;

    /** jocFGHkey1_fbgDFbdf. */
    public static final String COL_JOCFGHKEY1_FBGDFBDF =
            "jocFGHkey1_fbgDFbdf";
    /** Alias. */
    public static final String ALIASED_COL_JOCFGHKEY1_FBGDFBDF =
            UserContract.TABLE_NAME + "." + COL_JOCFGHKEY1_FBGDFBDF;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "User";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "User";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        UserContract.COL_ID1HNY,
        
        UserContract.COL_NAFGHME1,
        
        UserContract.COL_SURNFGHAME1,
        
        UserContract.COL_SCOFGHRE1_IDFD1,
        
        UserContract.COL_JOCFGHKEY1_FBGDFBDF
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        UserContract.ALIASED_COL_ID1HNY,
        
        UserContract.ALIASED_COL_NAFGHME1,
        
        UserContract.ALIASED_COL_SURNFGHAME1,
        
        UserContract.ALIASED_COL_SCOFGHRE1_IDFD1,
        
        UserContract.ALIASED_COL_JOCFGHKEY1_FBGDFBDF
    };


    /**
     * Converts a User into a content values.
     *
     * @param item The User to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final User item) {
        final ContentValues result = new ContentValues();

             result.put(UserContract.COL_ID1HNY,
                String.valueOf(item.getId1HNY()));

             if (item.getNaFGHme1() != null) {
                result.put(UserContract.COL_NAFGHME1,
                    item.getNaFGHme1());
            }

             if (item.getSurnFGHame1() != null) {
                result.put(UserContract.COL_SURNFGHAME1,
                    item.getSurnFGHame1());
            }

             if (item.getScoFGHre1() != null) {
                result.put(UserContract.COL_SCOFGHRE1_IDFD1,
                    item.getScoFGHre1().getIdFD1());
            }

             if (item.getJocFGHkey1() != null) {
                result.put(UserContract.COL_JOCFGHKEY1_FBGDFBDF,
                    item.getJocFGHkey1().getFbgDFbdf());
            }


        return result;
    }

    /**
     * Converts a Cursor into a User.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted User
     */
    public static User cursorToItem(final android.database.Cursor cursor) {
        User result = new User();
        UserContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to User entity.
     * @param cursor Cursor object
     * @param result User entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final User result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(UserContract.COL_ID1HNY);

            if (index > -1) {
                result.setId1HNY(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(UserContract.COL_NAFGHME1);

            if (index > -1) {
                result.setNaFGHme1(cursor.getString(index));
            }
            index = cursor.getColumnIndex(UserContract.COL_SURNFGHAME1);

            if (index > -1) {
                result.setSurnFGHame1(cursor.getString(index));
            }
            if (result.getScoFGHre1() == null) {
                final Score scoFGHre1 = new Score();
                index = cursor.getColumnIndex(UserContract.COL_SCOFGHRE1_IDFD1);

                if (index > -1) {
                    scoFGHre1.setIdFD1(cursor.getInt(index));
                    result.setScoFGHre1(scoFGHre1);
                }

            }
            if (result.getJocFGHkey1() == null) {
                final Jockey jocFGHkey1 = new Jockey();
                index = cursor.getColumnIndex(UserContract.COL_JOCFGHKEY1_FBGDFBDF);

                if (index > -1) {
                    jocFGHkey1.setFbgDFbdf(cursor.getInt(index));
                    result.setJocFGHkey1(jocFGHkey1);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of User entity.
     * @param cursor Cursor object
     * @return Array of User entity
     */
    public static ArrayList<User> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<User> result = new ArrayList<User>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            User item;
            do {
                item = UserContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
