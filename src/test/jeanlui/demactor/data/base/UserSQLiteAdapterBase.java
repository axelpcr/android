
/**************************************************************************
 * UserSQLiteAdapterBase.java, demactor Android
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
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.data.ScoreSQLiteAdapter;
import com.jeanlui.demactor.data.JockeySQLiteAdapter;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.ScoreContract;
import com.jeanlui.demactor.provider.contract.JockeyContract;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.Score;
import com.jeanlui.demactor.entity.Jockey;


import com.jeanlui.demactor.DemactorApplication;



/** User adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit UserAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class UserSQLiteAdapterBase
                        extends SQLiteAdapter<User> {

    /** TAG for debug purpose. */
    protected static final String TAG = "UserDBAdapter";


    /**
     * Get the table name used in DB for your User entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return UserContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your User entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = UserContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the User entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return UserContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + UserContract.TABLE_NAME    + " ("
        
         + UserContract.COL_ID1HNY    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + UserContract.COL_NAFGHME1    + " VARCHAR NOT NULL,"
         + UserContract.COL_SURNFGHAME1    + " VARCHAR NOT NULL,"
         + UserContract.COL_SCOFGHRE1_IDFD1    + " INTEGER NOT NULL,"
         + UserContract.COL_JOCFGHKEY1_FBGDFBDF    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + UserContract.COL_SCOFGHRE1_IDFD1 + ") REFERENCES " 
             + ScoreContract.TABLE_NAME 
                + " (" + ScoreContract.COL_IDFD1 + "),"
         + "FOREIGN KEY(" + UserContract.COL_JOCFGHKEY1_FBGDFBDF + ") REFERENCES " 
             + JockeyContract.TABLE_NAME 
                + " (" + JockeyContract.COL_FBGDFBDF + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public UserSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert User entity to Content Values for database.
     * @param item User entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final User item) {
        return UserContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to User entity.
     * @param cursor android.database.Cursor object
     * @return User entity
     */
    public User cursorToItem(final android.database.Cursor cursor) {
        return UserContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to User entity.
     * @param cursor android.database.Cursor object
     * @param result User entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final User result) {
        UserContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read User by id in database.
     *
     * @param id Identify of User
     * @return User entity
     */
    public User getByID(final int id1HNY) {
        final android.database.Cursor cursor = this.getSingleCursor(id1HNY);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final User result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getScoFGHre1() != null) {
            final ScoreSQLiteAdapter scoFGHre1Adapter =
                    new ScoreSQLiteAdapter(this.ctx);
            scoFGHre1Adapter.open(this.mDatabase);

            result.setScoFGHre1(scoFGHre1Adapter.getByID(
                            result.getScoFGHre1().getIdFD1()));
        }
        if (result.getJocFGHkey1() != null) {
            final JockeySQLiteAdapter jocFGHkey1Adapter =
                    new JockeySQLiteAdapter(this.ctx);
            jocFGHkey1Adapter.open(this.mDatabase);

            result.setJocFGHkey1(jocFGHkey1Adapter.getByID(
                            result.getJocFGHkey1().getFbgDFbdf()));
        }
        return result;
    }

    /**
     * Find & read User by scoFGHre1.
     * @param scofghre1Id scofghre1Id
     * @param orderBy Order by string (can be null)
     * @return List of User entities
     */
     public android.database.Cursor getByScoFGHre1(final int scofghre1Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = UserContract.COL_SCOFGHRE1_IDFD1 + "= ?";
        String idSelectionArgs = String.valueOf(scofghre1Id);
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
     * Find & read User by jocFGHkey1.
     * @param jocfghkey1Id jocfghkey1Id
     * @param orderBy Order by string (can be null)
     * @return List of User entities
     */
     public android.database.Cursor getByJocFGHkey1(final int jocfghkey1Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = UserContract.COL_JOCFGHKEY1_FBGDFBDF + "= ?";
        String idSelectionArgs = String.valueOf(jocfghkey1Id);
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
     * Read All Users entities.
     *
     * @return List of User entities
     */
    public ArrayList<User> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<User> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a User entity into database.
     *
     * @param item The User entity to persist
     * @return Id of the User entity
     */
    public long insert(final User item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + UserContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                UserContract.itemToContentValues(item);
        values.remove(UserContract.COL_ID1HNY);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    UserContract.COL_ID1HNY,
                    values);
        }
        item.setId1HNY(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a User entity into database whether.
     * it already exists or not.
     *
     * @param item The User entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final User item) {
        int result = 0;
        if (this.getByID(item.getId1HNY()) != null) {
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
     * Update a User entity into database.
     *
     * @param item The User entity to persist
     * @return count of updated entities
     */
    public int update(final User item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + UserContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                UserContract.itemToContentValues(item);
        final String whereClause =
                 UserContract.COL_ID1HNY
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId1HNY()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a User entity of database.
     *
     * @param id1HNY id1HNY
     * @return count of updated entities
     */
    public int remove(final int id1HNY) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + UserContract.TABLE_NAME
                    + ")"
                    + " id : "+ id1HNY);
        }

        final String whereClause =
                UserContract.COL_ID1HNY
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id1HNY)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param user The entity to delete
     * @return count of updated entities
     */
    public int delete(final User user) {
        return this.remove(user.getId1HNY());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id1HNY id1HNY
     *  @return A android.database.Cursor pointing to the User corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id1HNY) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id1HNY);
        }

        final String whereClause =
                UserContract.ALIASED_COL_ID1HNY
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id1HNY)};

        return this.query(
                UserContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a User entity.
     *
     * @param id1HNY The id1HNY of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id1HNY) {

        String selection = UserContract.ALIASED_COL_ID1HNY + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id1HNY);

        return this.query(
                UserContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

