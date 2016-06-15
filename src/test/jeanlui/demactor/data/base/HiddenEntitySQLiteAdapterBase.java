
/**************************************************************************
 * HiddenEntitySQLiteAdapterBase.java, demactor Android
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
import com.jeanlui.demactor.data.HiddenEntitySQLiteAdapter;
import com.jeanlui.demactor.provider.contract.HiddenEntityContract;
import com.jeanlui.demactor.entity.HiddenEntity;


import com.jeanlui.demactor.DemactorApplication;



/** HiddenEntity adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit HiddenEntityAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class HiddenEntitySQLiteAdapterBase
                        extends SQLiteAdapter<HiddenEntity> {

    /** TAG for debug purpose. */
    protected static final String TAG = "HiddenEntityDBAdapter";


    /**
     * Get the table name used in DB for your HiddenEntity entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return HiddenEntityContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your HiddenEntity entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = HiddenEntityContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the HiddenEntity entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return HiddenEntityContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + HiddenEntityContract.TABLE_NAME    + " ("
        
         + HiddenEntityContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + HiddenEntityContract.COL_CONTENT    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public HiddenEntitySQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert HiddenEntity entity to Content Values for database.
     * @param item HiddenEntity entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final HiddenEntity item) {
        return HiddenEntityContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to HiddenEntity entity.
     * @param cursor android.database.Cursor object
     * @return HiddenEntity entity
     */
    public HiddenEntity cursorToItem(final android.database.Cursor cursor) {
        return HiddenEntityContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to HiddenEntity entity.
     * @param cursor android.database.Cursor object
     * @param result HiddenEntity entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final HiddenEntity result) {
        HiddenEntityContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read HiddenEntity by id in database.
     *
     * @param id Identify of HiddenEntity
     * @return HiddenEntity entity
     */
    public HiddenEntity getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final HiddenEntity result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All HiddenEntitys entities.
     *
     * @return List of HiddenEntity entities
     */
    public ArrayList<HiddenEntity> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<HiddenEntity> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a HiddenEntity entity into database.
     *
     * @param item The HiddenEntity entity to persist
     * @return Id of the HiddenEntity entity
     */
    public long insert(final HiddenEntity item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + HiddenEntityContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                HiddenEntityContract.itemToContentValues(item);
        values.remove(HiddenEntityContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    HiddenEntityContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a HiddenEntity entity into database whether.
     * it already exists or not.
     *
     * @param item The HiddenEntity entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final HiddenEntity item) {
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
     * Update a HiddenEntity entity into database.
     *
     * @param item The HiddenEntity entity to persist
     * @return count of updated entities
     */
    public int update(final HiddenEntity item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + HiddenEntityContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                HiddenEntityContract.itemToContentValues(item);
        final String whereClause =
                 HiddenEntityContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a HiddenEntity entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + HiddenEntityContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                HiddenEntityContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param hiddenEntity The entity to delete
     * @return count of updated entities
     */
    public int delete(final HiddenEntity hiddenEntity) {
        return this.remove(hiddenEntity.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the HiddenEntity corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                HiddenEntityContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                HiddenEntityContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a HiddenEntity entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = HiddenEntityContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                HiddenEntityContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

