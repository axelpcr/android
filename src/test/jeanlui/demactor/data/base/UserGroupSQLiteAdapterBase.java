
/**************************************************************************
 * UserGroupSQLiteAdapterBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.jeanlui.demactor.data.SQLiteAdapter;
import com.jeanlui.demactor.data.UserGroupSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.UserGroupContract;
import com.jeanlui.demactor.entity.UserGroup;


import com.jeanlui.demactor.DemactorApplication;



/** UserGroup adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit UserGroupAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class UserGroupSQLiteAdapterBase
                        extends SQLiteAdapter<UserGroup> {

    /** TAG for debug purpose. */
    protected static final String TAG = "UserGroupDBAdapter";


    /**
     * Get the table name used in DB for your UserGroup entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return UserGroupContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your UserGroup entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = UserGroupContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the UserGroup entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return UserGroupContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + UserGroupContract.TABLE_NAME    + " ("
        
         + UserGroupContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + UserGroupContract.COL_NAME    + " VARCHAR NOT NULL,"
         + UserGroupContract.COL_WRITEPERMISSION    + " BOOLEAN NOT NULL,"
         + UserGroupContract.COL_DELETEPERMISSION    + " BOOLEAN NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public UserGroupSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert UserGroup entity to Content Values for database.
     * @param item UserGroup entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final UserGroup item) {
        return UserGroupContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to UserGroup entity.
     * @param cursor android.database.Cursor object
     * @return UserGroup entity
     */
    public UserGroup cursorToItem(final android.database.Cursor cursor) {
        return UserGroupContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to UserGroup entity.
     * @param cursor android.database.Cursor object
     * @param result UserGroup entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final UserGroup result) {
        UserGroupContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read UserGroup by id in database.
     *
     * @param id Identify of UserGroup
     * @return UserGroup entity
     */
    public UserGroup getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final UserGroup result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All UserGroups entities.
     *
     * @return List of UserGroup entities
     */
    public ArrayList<UserGroup> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<UserGroup> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a UserGroup entity into database.
     *
     * @param item The UserGroup entity to persist
     * @return Id of the UserGroup entity
     */
    public long insert(final UserGroup item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + UserGroupContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                UserGroupContract.itemToContentValues(item);
        values.remove(UserGroupContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    UserGroupContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a UserGroup entity into database whether.
     * it already exists or not.
     *
     * @param item The UserGroup entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final UserGroup item) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.update(item);
        } else {
            // Item doesn't exist => create it
            final long id = this.insert(item);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }

    /**
     * Update a UserGroup entity into database.
     *
     * @param item The UserGroup entity to persist
     * @return count of updated entities
     */
    public int update(final UserGroup item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + UserGroupContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                UserGroupContract.itemToContentValues(item);
        final String whereClause =
                 UserGroupContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a UserGroup entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + UserGroupContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                UserGroupContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param userGroup The entity to delete
     * @return count of updated entities
     */
    public int delete(final UserGroup userGroup) {
        return this.remove(userGroup.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the UserGroup corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                UserGroupContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                UserGroupContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a UserGroup entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = UserGroupContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                UserGroupContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

