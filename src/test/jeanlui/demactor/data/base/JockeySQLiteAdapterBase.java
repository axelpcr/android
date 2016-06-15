
/**************************************************************************
 * JockeySQLiteAdapterBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 4, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.jeanlui.demactor.data.SQLiteAdapter;
import com.jeanlui.demactor.data.JockeySQLiteAdapter;
import com.jeanlui.demactor.data.PoneytoJockeySQLiteAdapter;
import com.jeanlui.demactor.data.PoneySQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.provider.contract.PoneytoJockeyContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.entity.Jockey;
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.User;


import com.jeanlui.demactor.DemactorApplication;



/** Jockey adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit JockeyAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class JockeySQLiteAdapterBase
                        extends SQLiteAdapter<Jockey> {

    /** TAG for debug purpose. */
    protected static final String TAG = "JockeyDBAdapter";


    /**
     * Get the table name used in DB for your Jockey entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return JockeyContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Jockey entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = JockeyContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Jockey entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return JockeyContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + JockeyContract.TABLE_NAME    + " ("
        
         + JockeyContract.COL_FBGDFBDF    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + JockeyContract.COL_DFDFGDDDFGDFG    + " VARCHAR NOT NULL,"
         + JockeyContract.COL_DFGDFGDFGDFFG    + " VARCHAR NOT NULL,"
         + JockeyContract.COL_IUYTREZBA_ID1HNY    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + JockeyContract.COL_IUYTREZBA_ID1HNY + ") REFERENCES " 
             + UserContract.TABLE_NAME 
                + " (" + UserContract.COL_ID1HNY + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public JockeySQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Jockey entity to Content Values for database.
     * @param item Jockey entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Jockey item) {
        return JockeyContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Jockey entity.
     * @param cursor android.database.Cursor object
     * @return Jockey entity
     */
    public Jockey cursorToItem(final android.database.Cursor cursor) {
        return JockeyContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Jockey entity.
     * @param cursor android.database.Cursor object
     * @param result Jockey entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Jockey result) {
        JockeyContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Jockey by id in database.
     *
     * @param id Identify of Jockey
     * @return Jockey entity
     */
    public Jockey getByID(final int fbgDFbdf) {
        final android.database.Cursor cursor = this.getSingleCursor(fbgDFbdf);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Jockey result = this.cursorToItem(cursor);
        cursor.close();

        PoneytoJockeySQLiteAdapter poneytojockeyAdapter =
                new PoneytoJockeySQLiteAdapter(this.ctx);
        poneytojockeyAdapter.open(this.mDatabase);
        android.database.Cursor dzerzerbczeCursor = poneytojockeyAdapter.getByPoneyDFd1(
                            result.getFbgDFbdf(),
                            PoneyContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setDzerzerBCze(new PoneySQLiteAdapter(ctx).cursorToItems(dzerzerbczeCursor));

        dzerzerbczeCursor.close();
        if (result.getIuytrezBa() != null) {
            final UserSQLiteAdapter iuytrezBaAdapter =
                    new UserSQLiteAdapter(this.ctx);
            iuytrezBaAdapter.open(this.mDatabase);

            result.setIuytrezBa(iuytrezBaAdapter.getByID(
                            result.getIuytrezBa().getId1HNY()));
        }
        return result;
    }

    /**
     * Find & read Jockey by iuytrezBa.
     * @param iuytrezbaId iuytrezbaId
     * @param orderBy Order by string (can be null)
     * @return List of Jockey entities
     */
     public android.database.Cursor getByIuytrezBa(final int iuytrezbaId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = JockeyContract.COL_IUYTREZBA_ID1HNY + "= ?";
        String idSelectionArgs = String.valueOf(iuytrezbaId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }

    /**
     * Read All Jockeys entities.
     *
     * @return List of Jockey entities
     */
    public ArrayList<Jockey> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Jockey> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Jockey entity into database.
     *
     * @param item The Jockey entity to persist
     * @return Id of the Jockey entity
     */
    public long insert(final Jockey item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + JockeyContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                JockeyContract.itemToContentValues(item);
        values.remove(JockeyContract.COL_FBGDFBDF);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    JockeyContract.COL_FBGDFBDF,
                    values);
        }
        item.setFbgDFbdf(insertResult);
        if (item.getDzerzerBCze() != null) {
            PoneytoJockeySQLiteAdapterBase dzerzerBCzeAdapter =
                    new PoneytoJockeySQLiteAdapter(this.ctx);
            dzerzerBCzeAdapter.open(this.mDatabase);
            for (Poney i : item.getDzerzerBCze()) {
                dzerzerBCzeAdapter.insert(insertResult,
                        i.getIdlioEm1());
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Jockey entity into database whether.
     * it already exists or not.
     *
     * @param item The Jockey entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Jockey item) {
        int result = 0;
        if (this.getByID(item.getFbgDFbdf()) != null) {
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
     * Update a Jockey entity into database.
     *
     * @param item The Jockey entity to persist
     * @return count of updated entities
     */
    public int update(final Jockey item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + JockeyContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                JockeyContract.itemToContentValues(item);
        final String whereClause =
                 JockeyContract.COL_FBGDFBDF
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getFbgDFbdf()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Jockey entity of database.
     *
     * @param fbgDFbdf fbgDFbdf
     * @return count of updated entities
     */
    public int remove(final int fbgDFbdf) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + JockeyContract.TABLE_NAME
                    + ")"
                    + " id : "+ fbgDFbdf);
        }

        final String whereClause =
                JockeyContract.COL_FBGDFBDF
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(fbgDFbdf)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param jockey The entity to delete
     * @return count of updated entities
     */
    public int delete(final Jockey jockey) {
        return this.remove(jockey.getFbgDFbdf());
    }

    /**
     *  Internal android.database.Cursor.
     * @param fbgDFbdf fbgDFbdf
     *  @return A android.database.Cursor pointing to the Jockey corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int fbgDFbdf) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + fbgDFbdf);
        }

        final String whereClause =
                JockeyContract.ALIASED_COL_FBGDFBDF
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(fbgDFbdf)};

        return this.query(
                JockeyContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Jockey entity.
     *
     * @param fbgDFbdf The fbgDFbdf of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int fbgDFbdf) {

        String selection = JockeyContract.ALIASED_COL_FBGDFBDF + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(fbgDFbdf);

        return this.query(
                JockeyContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

