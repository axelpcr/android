
/**************************************************************************
 * CommentSQLiteAdapterBase.java, demactor Android
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


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.jeanlui.demactor.data.SQLiteAdapter;
import com.jeanlui.demactor.data.CommentSQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.data.PostSQLiteAdapter;
import com.jeanlui.demactor.data.GroupToCommentSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;
import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.entity.GroupToComment;

import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.DemactorApplication;



/** Comment adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit CommentAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class CommentSQLiteAdapterBase
                        extends SQLiteAdapter<Comment> {

    /** TAG for debug purpose. */
    protected static final String TAG = "CommentDBAdapter";


    /**
     * Get the table name used in DB for your Comment entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return CommentContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Comment entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = CommentContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Comment entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return CommentContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + CommentContract.TABLE_NAME    + " ("
        
         + CommentContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + CommentContract.COL_CONTENT    + " VARCHAR(2000) NOT NULL,"
         + CommentContract.COL_OWNER_ID    + " INTEGER NOT NULL,"
         + CommentContract.COL_POST_ID    + " INTEGER NOT NULL,"
         + CommentContract.COL_CREATEDAT    + " DATETIME NOT NULL,"
         + CommentContract.COL_VALIDATE    + " BOOLEAN NOT NULL,"

        
         + "FOREIGN KEY(" + CommentContract.COL_OWNER_ID + ") REFERENCES " 
             + UserContract.TABLE_NAME 
                + " (" + UserContract.COL_ID + "),"
         + "FOREIGN KEY(" + CommentContract.COL_POST_ID + ") REFERENCES " 
             + PostContract.TABLE_NAME 
                + " (" + PostContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public CommentSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Comment entity to Content Values for database.
     * @param item Comment entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Comment item) {
        return CommentContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Comment entity.
     * @param cursor android.database.Cursor object
     * @return Comment entity
     */
    public Comment cursorToItem(final android.database.Cursor cursor) {
        return CommentContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Comment entity.
     * @param cursor android.database.Cursor object
     * @param result Comment entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Comment result) {
        CommentContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Comment by id in database.
     *
     * @param id Identify of Comment
     * @return Comment entity
     */
    public Comment getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Comment result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getOwner() != null) {
            final UserSQLiteAdapter ownerAdapter =
                    new UserSQLiteAdapter(this.ctx);
            ownerAdapter.open(this.mDatabase);

            result.setOwner(ownerAdapter.getByID(
                            result.getOwner().getId()));
        }
        if (result.getPost() != null) {
            final PostSQLiteAdapter postAdapter =
                    new PostSQLiteAdapter(this.ctx);
            postAdapter.open(this.mDatabase);

            result.setPost(postAdapter.getByID(
                            result.getPost().getId()));
        }
        final GroupToCommentSQLiteAdapter groupsAdapter =
                new GroupToCommentSQLiteAdapter(this.ctx);
        groupsAdapter.open(this.mDatabase);
        android.database.Cursor groupsCursor = groupsAdapter
                    .getByCommentgroupsInternal(
                            result.getId(),
                            GroupToCommentContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setGroups(groupsAdapter.cursorToItems(groupsCursor));

        groupsCursor.close();
        return result;
    }

    /**
     * Find & read Comment by owner.
     * @param ownerId ownerId
     * @param orderBy Order by string (can be null)
     * @return List of Comment entities
     */
     public android.database.Cursor getByOwner(final int ownerId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = CommentContract.COL_OWNER_ID + "= ?";
        String idSelectionArgs = String.valueOf(ownerId);
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
     * Find & read Comment by post.
     * @param postId postId
     * @param orderBy Order by string (can be null)
     * @return List of Comment entities
     */
     public android.database.Cursor getByPost(final int postId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = CommentContract.COL_POST_ID + "= ?";
        String idSelectionArgs = String.valueOf(postId);
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
     * Read All Comments entities.
     *
     * @return List of Comment entities
     */
    public ArrayList<Comment> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Comment> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Comment entity into database.
     *
     * @param item The Comment entity to persist
     * @return Id of the Comment entity
     */
    public long insert(final Comment item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + CommentContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                CommentContract.itemToContentValues(item);
        values.remove(CommentContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    CommentContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getGroups() != null) {
            GroupToCommentSQLiteAdapterBase groupsAdapter =
                    new GroupToCommentSQLiteAdapter(this.ctx);
            groupsAdapter.open(this.mDatabase);
            for (GroupToComment grouptocomment
                        : item.getGroups()) {
                groupsAdapter.insertOrUpdateWithCommentGroups(
                                    grouptocomment,
                                    insertResult);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Comment entity into database whether.
     * it already exists or not.
     *
     * @param item The Comment entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Comment item) {
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
     * Update a Comment entity into database.
     *
     * @param item The Comment entity to persist
     * @return count of updated entities
     */
    public int update(final Comment item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + CommentContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                CommentContract.itemToContentValues(item);
        final String whereClause =
                 CommentContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Comment entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + CommentContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                CommentContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param comment The entity to delete
     * @return count of updated entities
     */
    public int delete(final Comment comment) {
        return this.remove(comment.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Comment corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                CommentContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                CommentContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Comment entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = CommentContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                CommentContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

