
/**************************************************************************
 * GroupSQLiteAdapterBase.java, demactor Android
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
import com.jeanlui.demactor.data.GroupSQLiteAdapter;
import com.jeanlui.demactor.data.GroupToCommentSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.GroupContract;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;
import com.jeanlui.demactor.entity.Group;
import com.jeanlui.demactor.entity.GroupToComment;


import com.jeanlui.demactor.DemactorApplication;



/** Group adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit GroupAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class GroupSQLiteAdapterBase
                        extends SQLiteAdapter<Group> {

    /** TAG for debug purpose. */
    protected static final String TAG = "GroupDBAdapter";


    /**
     * Get the table name used in DB for your Group entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return GroupContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Group entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = GroupContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Group entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return GroupContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + GroupContract.TABLE_NAME    + " ("
        
         + GroupContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + GroupContract.COL_NAME    + " VARCHAR NOT NULL DEFAULT 'Default Group Name'"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public GroupSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Group entity to Content Values for database.
     * @param item Group entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Group item) {
        return GroupContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Group entity.
     * @param cursor android.database.Cursor object
     * @return Group entity
     */
    public Group cursorToItem(final android.database.Cursor cursor) {
        return GroupContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Group entity.
     * @param cursor android.database.Cursor object
     * @param result Group entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Group result) {
        GroupContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Group by id in database.
     *
     * @param id Identify of Group
     * @return Group entity
     */
    public Group getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Group result = this.cursorToItem(cursor);
        cursor.close();

        final GroupToCommentSQLiteAdapter commentsAdapter =
                new GroupToCommentSQLiteAdapter(this.ctx);
        commentsAdapter.open(this.mDatabase);
        android.database.Cursor commentsCursor = commentsAdapter
                    .getByGroup(
                            result.getId(),
                            GroupToCommentContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setComments(commentsAdapter.cursorToItems(commentsCursor));

        commentsCursor.close();
        return result;
    }


    /**
     * Read All Groups entities.
     *
     * @return List of Group entities
     */
    public ArrayList<Group> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Group> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Group entity into database.
     *
     * @param item The Group entity to persist
     * @return Id of the Group entity
     */
    public long insert(final Group item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + GroupContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                GroupContract.itemToContentValues(item);
        values.remove(GroupContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    GroupContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getComments() != null) {
            GroupToCommentSQLiteAdapterBase commentsAdapter =
                    new GroupToCommentSQLiteAdapter(this.ctx);
            commentsAdapter.open(this.mDatabase);
            for (GroupToComment grouptocomment
                        : item.getComments()) {
                grouptocomment.setGroup(item);
                commentsAdapter.insertOrUpdate(grouptocomment);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Group entity into database whether.
     * it already exists or not.
     *
     * @param item The Group entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Group item) {
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
     * Update a Group entity into database.
     *
     * @param item The Group entity to persist
     * @return count of updated entities
     */
    public int update(final Group item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + GroupContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                GroupContract.itemToContentValues(item);
        final String whereClause =
                 GroupContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Group entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + GroupContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                GroupContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param group The entity to delete
     * @return count of updated entities
     */
    public int delete(final Group group) {
        return this.remove(group.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Group corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                GroupContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                GroupContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Group entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = GroupContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                GroupContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

