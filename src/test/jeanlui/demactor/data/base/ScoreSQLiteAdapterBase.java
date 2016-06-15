
/**************************************************************************
 * ScoreSQLiteAdapterBase.java, demactor Android
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


import com.jeanlui.demactor.data.SQLiteAdapter;
import com.jeanlui.demactor.data.ScoreSQLiteAdapter;
import com.jeanlui.demactor.data.PoneySQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.ScoreContract;
import com.jeanlui.demactor.provider.contract.PoneyContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.entity.Score;
import com.jeanlui.demactor.entity.Poney;
import com.jeanlui.demactor.entity.User;


import com.jeanlui.demactor.DemactorApplication;



/** Score adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ScoreAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ScoreSQLiteAdapterBase
                        extends SQLiteAdapter<Score> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ScoreDBAdapter";


    /**
     * Get the table name used in DB for your Score entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ScoreContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Score entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ScoreContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Score entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ScoreContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ScoreContract.TABLE_NAME    + " ("
        
         + ScoreContract.COL_IDFD1    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + ScoreContract.COL_MONEFGHFGY1    + " INTEGER NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ScoreSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Score entity to Content Values for database.
     * @param item Score entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Score item) {
        return ScoreContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Score entity.
     * @param cursor android.database.Cursor object
     * @return Score entity
     */
    public Score cursorToItem(final android.database.Cursor cursor) {
        return ScoreContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Score entity.
     * @param cursor android.database.Cursor object
     * @param result Score entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Score result) {
        ScoreContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Score by id in database.
     *
     * @param id Identify of Score
     * @return Score entity
     */
    public Score getByID(final int idFD1) {
        final android.database.Cursor cursor = this.getSingleCursor(idFD1);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Score result = this.cursorToItem(cursor);
        cursor.close();

        final PoneySQLiteAdapter ponRTYeys1Adapter =
                new PoneySQLiteAdapter(this.ctx);
        ponRTYeys1Adapter.open(this.mDatabase);
        android.database.Cursor ponrtyeys1Cursor = ponRTYeys1Adapter
                    .getByScorvbnBe1(
                            result.getIdFD1(),
                            PoneyContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setPonRTYeys1(ponRTYeys1Adapter.cursorToItems(ponrtyeys1Cursor));

        ponrtyeys1Cursor.close();
        final UserSQLiteAdapter useGHHNrs1Adapter =
                new UserSQLiteAdapter(this.ctx);
        useGHHNrs1Adapter.open(this.mDatabase);
        android.database.Cursor useghhnrs1Cursor = useGHHNrs1Adapter
                    .getByScoFGHre1(
                            result.getIdFD1(),
                            UserContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setUseGHHNrs1(useGHHNrs1Adapter.cursorToItems(useghhnrs1Cursor));

        useghhnrs1Cursor.close();
        return result;
    }


    /**
     * Read All Scores entities.
     *
     * @return List of Score entities
     */
    public ArrayList<Score> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Score> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Score entity into database.
     *
     * @param item The Score entity to persist
     * @return Id of the Score entity
     */
    public long insert(final Score item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ScoreContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ScoreContract.itemToContentValues(item);
        values.remove(ScoreContract.COL_IDFD1);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    ScoreContract.COL_IDFD1,
                    values);
        }
        item.setIdFD1(insertResult);
        if (item.getPonRTYeys1() != null) {
            PoneySQLiteAdapterBase ponRTYeys1Adapter =
                    new PoneySQLiteAdapter(this.ctx);
            ponRTYeys1Adapter.open(this.mDatabase);
            for (Poney poney
                        : item.getPonRTYeys1()) {
                poney.setScorvbnBe1(item);
                ponRTYeys1Adapter.insertOrUpdate(poney);
            }
        }
        if (item.getUseGHHNrs1() != null) {
            UserSQLiteAdapterBase useGHHNrs1Adapter =
                    new UserSQLiteAdapter(this.ctx);
            useGHHNrs1Adapter.open(this.mDatabase);
            for (User user
                        : item.getUseGHHNrs1()) {
                user.setScoFGHre1(item);
                useGHHNrs1Adapter.insertOrUpdate(user);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Score entity into database whether.
     * it already exists or not.
     *
     * @param item The Score entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Score item) {
        int result = 0;
        if (this.getByID(item.getIdFD1()) != null) {
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
     * Update a Score entity into database.
     *
     * @param item The Score entity to persist
     * @return count of updated entities
     */
    public int update(final Score item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ScoreContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ScoreContract.itemToContentValues(item);
        final String whereClause =
                 ScoreContract.COL_IDFD1
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getIdFD1()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Score entity of database.
     *
     * @param idFD1 idFD1
     * @return count of updated entities
     */
    public int remove(final int idFD1) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + ScoreContract.TABLE_NAME
                    + ")"
                    + " id : "+ idFD1);
        }

        final String whereClause =
                ScoreContract.COL_IDFD1
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(idFD1)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param score The entity to delete
     * @return count of updated entities
     */
    public int delete(final Score score) {
        return this.remove(score.getIdFD1());
    }

    /**
     *  Internal android.database.Cursor.
     * @param idFD1 idFD1
     *  @return A android.database.Cursor pointing to the Score corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int idFD1) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + idFD1);
        }

        final String whereClause =
                ScoreContract.ALIASED_COL_IDFD1
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(idFD1)};

        return this.query(
                ScoreContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Score entity.
     *
     * @param idFD1 The idFD1 of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int idFD1) {

        String selection = ScoreContract.ALIASED_COL_IDFD1 + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(idFD1);

        return this.query(
                ScoreContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

