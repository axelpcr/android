/**************************************************************************
 * GroupContractBase.java, demactor Android
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

import com.jeanlui.demactor.entity.Group;
import com.jeanlui.demactor.entity.GroupToComment;



import com.jeanlui.demactor.provider.contract.GroupContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class GroupContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            GroupContract.TABLE_NAME + "." + COL_ID;

    /** name. */
    public static final String COL_NAME =
            "name";
    /** Alias. */
    public static final String ALIASED_COL_NAME =
            GroupContract.TABLE_NAME + "." + COL_NAME;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Group";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Group";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        GroupContract.COL_ID,
        
        GroupContract.COL_NAME,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        GroupContract.ALIASED_COL_ID,
        
        GroupContract.ALIASED_COL_NAME,
        
    };


    /**
     * Converts a Group into a content values.
     *
     * @param item The Group to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Group item) {
        final ContentValues result = new ContentValues();

             result.put(GroupContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getName() != null) {
                result.put(GroupContract.COL_NAME,
                    item.getName());
            }

 
        return result;
    }

    /**
     * Converts a Cursor into a Group.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Group
     */
    public static Group cursorToItem(final android.database.Cursor cursor) {
        Group result = new Group();
        GroupContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Group entity.
     * @param cursor Cursor object
     * @param result Group entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Group result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(GroupContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(GroupContract.COL_NAME);

            if (index > -1) {
                result.setName(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Group entity.
     * @param cursor Cursor object
     * @return Array of Group entity
     */
    public static ArrayList<Group> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Group> result = new ArrayList<Group>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Group item;
            do {
                item = GroupContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
