
/**************************************************************************
 * GroupToCommentSQLiteAdapterBase.java, demactor Android
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


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.jeanlui.demactor.data.SQLiteAdapter;
import com.jeanlui.demactor.data.GroupToCommentSQLiteAdapter;
import com.jeanlui.demactor.data.GroupSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.GroupContract;
import com.jeanlui.demactor.entity.GroupToComment;
import com.jeanlui.demactor.entity.Group;


import com.jeanlui.demactor.DemactorApplication;



/** GroupToComment adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit GroupToCommentAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class GroupToCommentSQLiteAdapterBase
                        extends SQLiteAdapter<GroupToComment> {

    /** TAG for debug purpose. */
    protected static final String TAG = "GroupToCommentDBAdapter";


    /**
     * Get the table name used in DB for your GroupToComment entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return GroupToCommentContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your GroupToComment entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = GroupToCommentContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the GroupToComment entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return GroupToCommentContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + GroupToCommentContract.TABLE_NAME    + " ("
        
         + GroupToCommentContract.COL_COMMENTGROUPSINTERNAL_ID    + " INTEGER,"
         + GroupToCommentContract.COL_ID    + " INTEGER PRIMARY KEY,"
         + GroupToCommentContract.COL_DISPLAYNAME    + " VARCHAR NOT NULL,"
         + GroupToCommentContract.COL_GROUP_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + GroupToCommentContract.COL_COMMENTGROUPSINTERNAL_ID + ") REFERENCES " 
             + CommentContract.TABLE_NAME 
                + " (" + CommentContract.COL_ID + "),"
         + "FOREIGN KEY(" + GroupToCommentContract.COL_GROUP_ID + ") REFERENCES " 
             + GroupContract.TABLE_NAME 
                + " (" + GroupContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public GroupToCommentSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert GroupToComment entity to Content Values for database.
     * @param item GroupToComment entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final GroupToComment item) {
        return GroupToCommentContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to GroupToComment entity.
     * @param cursor android.database.Cursor object
     * @return GroupToComment entity
     */
    public GroupToComment cursorToItem(final android.database.Cursor cursor) {
        return GroupToCommentContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to GroupToComment entity.
     * @param cursor android.database.Cursor object
     * @param result GroupToComment entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final GroupToComment result) {
        GroupToCommentContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read GroupToComment by id in database.
     *
     * @param id Identify of GroupToComment
     * @return GroupToComment entity
     */
    public GroupToComment getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final GroupToComment result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getGroup() != null) {
            final GroupSQLiteAdapter groupAdapter =
                    new GroupSQLiteAdapter(this.ctx);
            groupAdapter.open(this.mDatabase);

            result.setGroup(groupAdapter.getByID(
                            result.getGroup().getId()));
        }
        return result;
    }

    /**
     * Find & read GroupToComment by CommentgroupsInternal.
     * @param commentgroupsinternalId commentgroupsinternalId
     * @param orderBy Order by string (can be null)
     * @return List of GroupToComment entities
     */
     public android.database.Cursor getByCommentgroupsInternal(final int commentgroupsinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = GroupToCommentContract.COL_COMMENTGROUPSINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(commentgroupsinternalId);
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
     * Find & read GroupToComment by group.
     * @param groupId groupId
     * @param orderBy Order by string (can be null)
     * @return List of GroupToComment entities
     */
     public android.database.Cursor getByGroup(final int groupId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = GroupToCommentContract.COL_GROUP_ID + "= ?";
        String idSelectionArgs = String.valueOf(groupId);
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
     * Read All GroupToComments entities.
     *
     * @return List of GroupToComment entities
     */
    public ArrayList<GroupToComment> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<GroupToComment> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a GroupToComment entity into database.
     *
     * @param item The GroupToComment entity to persist
     * @return Id of the GroupToComment entity
     */
    public long insert(final GroupToComment item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + GroupToCommentContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                GroupToCommentContract.itemToContentValues(item, 0);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    GroupToCommentContract.COL_ID,
                    values);
        }
        return insertResult;
    }

    /**
     * Either insert or update a GroupToComment entity into database whether.
     * it already exists or not.
     *
     * @param item The GroupToComment entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final GroupToComment item) {
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
     * Update a GroupToComment entity into database.
     *
     * @param item The GroupToComment entity to persist
     * @return count of updated entities
     */
    public int update(final GroupToComment item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + GroupToCommentContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                GroupToCommentContract.itemToContentValues(item, 0);
        final String whereClause =
                 GroupToCommentContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a GroupToComment entity into database.
     *
     * @param item The GroupToComment entity to persist
     * @param commentId The comment id
     * @return count of updated entities
     */
    public int updateWithCommentGroups(
                    GroupToComment item,
                    int commentgroupsInternalId) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + GroupToCommentContract.TABLE_NAME + ")");
        }

        ContentValues values =
                GroupToCommentContract.itemToContentValues(item);
        values.put(
                GroupToCommentContract.COL_COMMENTGROUPSINTERNAL_ID,
                commentgroupsInternalId);
        String whereClause =
                 GroupToCommentContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a GroupToComment entity into database whether.
     * it already exists or not.
     *
     * @param item The GroupToComment entity to persist
     * @param commentId The comment id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithCommentGroups(
            GroupToComment item, int commentId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithCommentGroups(item,
                    commentId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithCommentGroups(item,
                    commentId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a GroupToComment entity into database.
     *
     * @param item The GroupToComment entity to persist
     * @param commentId The comment id
     * @return Id of the GroupToComment entity
     */
    public long insertWithCommentGroups(
            GroupToComment item, int commentId) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + GroupToCommentContract.TABLE_NAME + ")");
        }

        ContentValues values = GroupToCommentContract.itemToContentValues(item,
                commentId);
        int newid = (int) this.insert(
            null,
            values);


        return newid;
    }


    /**
     * Delete a GroupToComment entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + GroupToCommentContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                GroupToCommentContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param groupToComment The entity to delete
     * @return count of updated entities
     */
    public int delete(final GroupToComment groupToComment) {
        return this.remove(groupToComment.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the GroupToComment corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                GroupToCommentContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                GroupToCommentContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a GroupToComment entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = GroupToCommentContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                GroupToCommentContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

