
/**************************************************************************
 * PostSQLiteAdapterBase.java, demactor Android
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
import com.jeanlui.demactor.data.PostSQLiteAdapter;
import com.jeanlui.demactor.data.UserSQLiteAdapter;
import com.jeanlui.demactor.data.CommentSQLiteAdapter;
import com.jeanlui.demactor.data.PosttoGroupSQLiteAdapter;
import com.jeanlui.demactor.data.GroupSQLiteAdapter;
import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.provider.contract.UserContract;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.PosttoGroupContract;
import com.jeanlui.demactor.provider.contract.GroupContract;
import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.entity.Group;

import com.jeanlui.demactor.harmony.util.DateUtils;
import com.jeanlui.demactor.DemactorApplication;



/** Post adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PostAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PostSQLiteAdapterBase
                        extends SQLiteAdapter<Post> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PostDBAdapter";


    /**
     * Get the table name used in DB for your Post entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PostContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Post entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PostContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Post entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PostContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PostContract.TABLE_NAME    + " ("
        
         + PostContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PostContract.COL_TITLE    + " VARCHAR(140) NOT NULL,"
         + PostContract.COL_CONTENT    + " VARCHAR(40000) NOT NULL,"
         + PostContract.COL_OWNER_ID    + " INTEGER NOT NULL,"
         + PostContract.COL_CREATEDAT    + " DATETIME NOT NULL,"
         + PostContract.COL_UPDATEDAT    + " DATETIME NOT NULL,"
         + PostContract.COL_EXPIRESAT    + " DATETIME NOT NULL,"

        
         + "FOREIGN KEY(" + PostContract.COL_OWNER_ID + ") REFERENCES " 
             + UserContract.TABLE_NAME 
                + " (" + UserContract.COL_ID + ")"
        + ", UNIQUE(" + PostContract.COL_TITLE + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PostSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Post entity to Content Values for database.
     * @param item Post entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Post item) {
        return PostContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Post entity.
     * @param cursor android.database.Cursor object
     * @return Post entity
     */
    public Post cursorToItem(final android.database.Cursor cursor) {
        return PostContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Post entity.
     * @param cursor android.database.Cursor object
     * @param result Post entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Post result) {
        PostContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Post by id in database.
     *
     * @param id Identify of Post
     * @return Post entity
     */
    public Post getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Post result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getOwner() != null) {
            final UserSQLiteAdapter ownerAdapter =
                    new UserSQLiteAdapter(this.ctx);
            ownerAdapter.open(this.mDatabase);

            result.setOwner(ownerAdapter.getByID(
                            result.getOwner().getId()));
        }
        final CommentSQLiteAdapter commentsAdapter =
                new CommentSQLiteAdapter(this.ctx);
        commentsAdapter.open(this.mDatabase);
        android.database.Cursor commentsCursor = commentsAdapter
                    .getByPost(
                            result.getId(),
                            CommentContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setComments(commentsAdapter.cursorToItems(commentsCursor));

        commentsCursor.close();
        PosttoGroupSQLiteAdapter posttogroupAdapter =
                new PosttoGroupSQLiteAdapter(this.ctx);
        posttogroupAdapter.open(this.mDatabase);
        android.database.Cursor groupsCursor = posttogroupAdapter.getByPostInternalId(
                            result.getId(),
                            GroupContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setGroups(new GroupSQLiteAdapter(ctx).cursorToItems(groupsCursor));

        groupsCursor.close();
        return result;
    }

    /**
     * Find & read Post by owner.
     * @param ownerId ownerId
     * @param orderBy Order by string (can be null)
     * @return List of Post entities
     */
     public android.database.Cursor getByOwner(final int ownerId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PostContract.COL_OWNER_ID + "= ?";
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
     * Read All Posts entities.
     *
     * @return List of Post entities
     */
    public ArrayList<Post> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Post> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Post entity into database.
     *
     * @param item The Post entity to persist
     * @return Id of the Post entity
     */
    public long insert(final Post item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PostContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PostContract.itemToContentValues(item);
        values.remove(PostContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PostContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getComments() != null) {
            CommentSQLiteAdapterBase commentsAdapter =
                    new CommentSQLiteAdapter(this.ctx);
            commentsAdapter.open(this.mDatabase);
            for (Comment comment
                        : item.getComments()) {
                comment.setPost(item);
                commentsAdapter.insertOrUpdate(comment);
            }
        }
        if (item.getGroups() != null) {
            PosttoGroupSQLiteAdapterBase groupsAdapter =
                    new PosttoGroupSQLiteAdapter(this.ctx);
            groupsAdapter.open(this.mDatabase);
            for (Group i : item.getGroups()) {
                groupsAdapter.insert(insertResult,
                        i.getId());
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Post entity into database whether.
     * it already exists or not.
     *
     * @param item The Post entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Post item) {
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
     * Update a Post entity into database.
     *
     * @param item The Post entity to persist
     * @return count of updated entities
     */
    public int update(final Post item) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PostContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PostContract.itemToContentValues(item);
        final String whereClause =
                 PostContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Post entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PostContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PostContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param post The entity to delete
     * @return count of updated entities
     */
    public int delete(final Post post) {
        return this.remove(post.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Post corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (DemactorApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PostContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PostContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Post entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PostContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PostContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

