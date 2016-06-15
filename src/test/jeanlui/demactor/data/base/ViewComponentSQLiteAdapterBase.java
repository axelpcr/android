
/**************************************************************************
 * ViewComponentSQLiteAdapterBase.java, demactor Android
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
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.jeanlui.demactor.data.SQLiteAdapter;
import com.jeanlui.demactor.data.ViewComponentSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.ViewComponentContract;
import com.jeanlui.demactor.entity.ViewComponent;
import com.jeanlui.demactor.entity.ViewComponent.Choice;

import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.DemactorApplication;



/** ViewComponent adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ViewComponentAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ViewComponentSQLiteAdapterBase
                        extends SQLiteAdapter<ViewComponent> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ViewComponentDBAdapter";


    /**
     * Get the table name used in DB for your ViewComponent entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ViewComponentContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your ViewComponent entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ViewComponentContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the ViewComponent entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ViewComponentContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ViewComponentContract.TABLE_NAME    + " ("
        
         + ViewComponentContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + ViewComponentContract.COL_STRINGFIELD    + " VARCHAR NOT NULL,"
         + ViewComponentContract.COL_TEXT    + " VARCHAR NOT NULL,"
         + ViewComponentContract.COL_DATETIME    + " DATETIME NOT NULL,"
         + ViewComponentContract.COL_DATE    + " DATE NOT NULL,"
         + ViewComponentContract.COL_TIME    + " DATETIME NOT NULL,"
         + ViewComponentContract.COL_LOGIN    + " VARCHAR NOT NULL,"
         + ViewComponentContract.COL_PASSWORD    + " VARCHAR NOT NULL,"
         + ViewComponentContract.COL_EMAIL    + " VARCHAR NOT NULL,"
         + ViewComponentContract.COL_PHONE    + " VARCHAR NOT NULL,"
         + ViewComponentContract.COL_CITY    + " VARCHAR NOT NULL,"
         + ViewComponentContract.COL_ZIPCODE    + " VARCHAR NOT NULL,"
         + ViewComponentContract.COL_COUNTRY    + " VARCHAR NOT NULL,"
         + ViewComponentContract.COL_BYTEFIELD    + " STRING NOT NULL,"
         + ViewComponentContract.COL_CHARFIELD    + " STRING NOT NULL,"
         + ViewComponentContract.COL_SHORTFIELD    + " SHORT NOT NULL,"
         + ViewComponentContract.COL_CHARACTER    + " STRING NOT NULL,"
         + ViewComponentContract.COL_CHOICE    + " INTEGER NOT NULL,"
         + ViewComponentContract.COL_BOOLEANOBJECT    + " BOOLEAN NOT NULL"

        
        + ", UNIQUE(" + ViewComponentContract.COL_LOGIN + ")"
        + ", UNIQUE(" + ViewComponentContract.COL_EMAIL + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ViewComponentSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert ViewComponent entity to Content Values for database.
     * @param item ViewComponent entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final ViewComponent item) {
        return ViewComponentContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to ViewComponent entity.
     * @param cursor android.database.Cursor object
     * @return ViewComponent entity
     */
    public ViewComponent cursorToItem(final android.database.Cursor cursor) {
        return ViewComponentContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to ViewComponent entity.
     * @param cursor android.database.Cursor object
     * @param result ViewComponent entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final ViewComponent result) {
        ViewComponentContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read ViewComponent by id in database.
     *
     * @param id Identify of ViewComponent
     * @return ViewComponent entity
     */
    public ViewComponent getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final ViewComponent result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All ViewComponents entities.
     *
     * @return List of ViewComponent entities
     */
    public ArrayList<ViewComponent> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<ViewComponent> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a ViewComponent entity into database.
     *
     * @param item The ViewComponent entity to persist
     * @return Id of the ViewComponent entity
     */
    public long insert(final ViewComponent item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ViewComponentContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ViewComponentContract.itemToContentValues(item);
        values.remove(ViewComponentContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    ViewComponentContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a ViewComponent entity into database whether.
     * it already exists or not.
     *
     * @param item The ViewComponent entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final ViewComponent item) {
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
     * Update a ViewComponent entity into database.
     *
     * @param item The ViewComponent entity to persist
     * @return count of updated entities
     */
    public int update(final ViewComponent item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ViewComponentContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ViewComponentContract.itemToContentValues(item);
        final String whereClause =
                 ViewComponentContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a ViewComponent entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + ViewComponentContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                ViewComponentContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param viewComponent The entity to delete
     * @return count of updated entities
     */
    public int delete(final ViewComponent viewComponent) {
        return this.remove(viewComponent.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the ViewComponent corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                ViewComponentContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                ViewComponentContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a ViewComponent entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = ViewComponentContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                ViewComponentContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

