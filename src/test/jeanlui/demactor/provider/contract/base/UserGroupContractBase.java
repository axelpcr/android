/**************************************************************************
 * UserGroupContractBase.java, demactor Android
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

import com.jeanlui.demactor.entity.UserGroup;



import com.jeanlui.demactor.provider.contract.UserGroupContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class UserGroupContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            UserGroupContract.TABLE_NAME + "." + COL_ID;

    /** name. */
    public static final String COL_NAME =
            "name";
    /** Alias. */
    public static final String ALIASED_COL_NAME =
            UserGroupContract.TABLE_NAME + "." + COL_NAME;

    /** writePermission. */
    public static final String COL_WRITEPERMISSION =
            "writePermission";
    /** Alias. */
    public static final String ALIASED_COL_WRITEPERMISSION =
            UserGroupContract.TABLE_NAME + "." + COL_WRITEPERMISSION;

    /** deletePermission. */
    public static final String COL_DELETEPERMISSION =
            "deletePermission";
    /** Alias. */
    public static final String ALIASED_COL_DELETEPERMISSION =
            UserGroupContract.TABLE_NAME + "." + COL_DELETEPERMISSION;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "UserGroup";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "UserGroup";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        UserGroupContract.COL_ID,
        
        UserGroupContract.COL_NAME,
        
        UserGroupContract.COL_WRITEPERMISSION,
        
        UserGroupContract.COL_DELETEPERMISSION
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        UserGroupContract.ALIASED_COL_ID,
        
        UserGroupContract.ALIASED_COL_NAME,
        
        UserGroupContract.ALIASED_COL_WRITEPERMISSION,
        
        UserGroupContract.ALIASED_COL_DELETEPERMISSION
    };


    /**
     * Converts a UserGroup into a content values.
     *
     * @param item The UserGroup to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final UserGroup item) {
        final ContentValues result = new ContentValues();

             result.put(UserGroupContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getName() != null) {
                result.put(UserGroupContract.COL_NAME,
                    item.getName());
            }

             result.put(UserGroupContract.COL_WRITEPERMISSION,
                item.isWritePermission() ? 1 : 0);

             result.put(UserGroupContract.COL_DELETEPERMISSION,
                item.isDeletePermission() ? 1 : 0);


        return result;
    }

    /**
     * Converts a Cursor into a UserGroup.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted UserGroup
     */
    public static UserGroup cursorToItem(final android.database.Cursor cursor) {
        UserGroup result = new UserGroup();
        UserGroupContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to UserGroup entity.
     * @param cursor Cursor object
     * @param result UserGroup entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final UserGroup result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(UserGroupContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(UserGroupContract.COL_NAME);

            if (index > -1) {
                result.setName(cursor.getString(index));
            }
            index = cursor.getColumnIndex(UserGroupContract.COL_WRITEPERMISSION);

            if (index > -1) {
                result.setWritePermission(cursor.getInt(index) == 1);
            }
            index = cursor.getColumnIndex(UserGroupContract.COL_DELETEPERMISSION);

            if (index > -1) {
                result.setDeletePermission(cursor.getInt(index) == 1);
            }

        }
    }

    /**
     * Convert Cursor of database to Array of UserGroup entity.
     * @param cursor Cursor object
     * @return Array of UserGroup entity
     */
    public static ArrayList<UserGroup> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<UserGroup> result = new ArrayList<UserGroup>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            UserGroup item;
            do {
                item = UserGroupContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
