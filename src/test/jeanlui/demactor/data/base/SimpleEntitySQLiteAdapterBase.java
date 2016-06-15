
/**************************************************************************
 * SimpleEntitySQLiteAdapterBase.java, demactor Android
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
import com.jeanlui.demactor.data.SimpleEntitySQLiteAdapter;
import com.jeanlui.demactor.provider.contract.SimpleEntityContract;
import com.jeanlui.demactor.entity.SimpleEntity;


import com.jeanlui.demactor.DemactorApplication;



/** SimpleEntity adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit SimpleEntityAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class SimpleEntitySQLiteAdapterBase
                        extends SQLiteAdapter<SimpleEntity> {

    /** TAG for debug purpose. */
    protected static final String TAG = "SimpleEntityDBAdapter";


    /**
     * Get the table name used in DB for your SimpleEntity entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return SimpleEntityContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your SimpleEntity entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = SimpleEntityContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the SimpleEntity entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return SimpleEntityContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + SimpleEntityContract.TABLE_NAME    + " ("
        
         + SimpleEntityContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public SimpleEntitySQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert SimpleEntity entity to Content Values for database.
     * @param item SimpleEntity entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final SimpleEntity item) {
        return SimpleEntityContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to SimpleEntity entity.
     * @param cursor android.database.Cursor object
     * @return SimpleEntity entity
     */
    public SimpleEntity cursorToItem(final android.database.Cursor cursor) {
        return SimpleEntityContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to SimpleEntity entity.
     * @param cursor android.database.Cursor object
     * @param result SimpleEntity entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final SimpleEntity result) {
        SimpleEntityContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read SimpleEntity by id in database.
     *
     * @param id Identify of SimpleEntity
     * @return SimpleEntity entity
     */
    public SimpleEntity getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final SimpleEntity result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All SimpleEntitys entities.
     *
     * @return List of SimpleEntity entities
     */
    public ArrayList<SimpleEntity> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<SimpleEntity> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a SimpleEntity entity into database.
     *
     * @param item The SimpleEntity entity to persist
     * @return Id of the SimpleEntity entity
     */
    public long insert(final SimpleEntity item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + SimpleEntityContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                SimpleEntityContract.itemToContentValues(item);
        values.remove(SimpleEntityContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    SimpleEntityContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a SimpleEntity entity into database whether.
     * it already exists or not.
     *
     * @param item The SimpleEntity entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final SimpleEntity item) {
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
     * Update a SimpleEntity entity into database.
     *
     * @param item The SimpleEntity entity to persist
     * @return count of updated entities
     */
    public int update(final SimpleEntity item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + SimpleEntityContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                SimpleEntityContract.itemToContentValues(item);
        final String whereClause =
                 SimpleEntityContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a SimpleEntity entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + SimpleEntityContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                SimpleEntityContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param simpleEntity The entity to delete
     * @return count of updated entities
     */
    public int delete(final SimpleEntity simpleEntity) {
        return this.remove(simpleEntity.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the SimpleEntity corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                SimpleEntityContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                SimpleEntityContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a SimpleEntity entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = SimpleEntityContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                SimpleEntityContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

