/**************************************************************************
 * CommentContractBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.entity.GroupToComment;



import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.harmony.util.DateUtils;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class CommentContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            CommentContract.TABLE_NAME + "." + COL_ID;

    /** content. */
    public static final String COL_CONTENT =
            "content";
    /** Alias. */
    public static final String ALIASED_COL_CONTENT =
            CommentContract.TABLE_NAME + "." + COL_CONTENT;

    /** owner_id. */
    public static final String COL_OWNER_ID =
            "owner_id";
    /** Alias. */
    public static final String ALIASED_COL_OWNER_ID =
            CommentContract.TABLE_NAME + "." + COL_OWNER_ID;

    /** post_id. */
    public static final String COL_POST_ID =
            "post_id";
    /** Alias. */
    public static final String ALIASED_COL_POST_ID =
            CommentContract.TABLE_NAME + "." + COL_POST_ID;

    /** createdAt. */
    public static final String COL_CREATEDAT =
            "created_at";
    /** Alias. */
    public static final String ALIASED_COL_CREATEDAT =
            CommentContract.TABLE_NAME + "." + COL_CREATEDAT;

    /** validate. */
    public static final String COL_VALIDATE =
            "validate";
    /** Alias. */
    public static final String ALIASED_COL_VALIDATE =
            CommentContract.TABLE_NAME + "." + COL_VALIDATE;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Comment";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Comment";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        CommentContract.COL_ID,
        
        CommentContract.COL_CONTENT,
        
        CommentContract.COL_OWNER_ID,
        
        CommentContract.COL_POST_ID,
        
        CommentContract.COL_CREATEDAT,
        
        CommentContract.COL_VALIDATE,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        CommentContract.ALIASED_COL_ID,
        
        CommentContract.ALIASED_COL_CONTENT,
        
        CommentContract.ALIASED_COL_OWNER_ID,
        
        CommentContract.ALIASED_COL_POST_ID,
        
        CommentContract.ALIASED_COL_CREATEDAT,
        
        CommentContract.ALIASED_COL_VALIDATE,
        
    };


    /**
     * Converts a Comment into a content values.
     *
     * @param item The Comment to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Comment item) {
        final ContentValues result = new ContentValues();

             result.put(CommentContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getContent() != null) {
                result.put(CommentContract.COL_CONTENT,
                    item.getContent());
            }

             if (item.getOwner() != null) {
                result.put(CommentContract.COL_OWNER_ID,
                    item.getOwner().getId());
            }

             if (item.getPost() != null) {
                result.put(CommentContract.COL_POST_ID,
                    item.getPost().getId());
            }

             if (item.getCreatedAt() != null) {
                result.put(CommentContract.COL_CREATEDAT,
                    item.getCreatedAt().toString(ISODateTimeFormat.dateTime()));
            }

             result.put(CommentContract.COL_VALIDATE,
                item.isValidate() ? 1 : 0);

 
        return result;
    }

    /**
     * Converts a Cursor into a Comment.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Comment
     */
    public static Comment cursorToItem(final android.database.Cursor cursor) {
        Comment result = new Comment();
        CommentContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Comment entity.
     * @param cursor Cursor object
     * @param result Comment entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Comment result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(CommentContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(CommentContract.COL_CONTENT);

            if (index > -1) {
                result.setContent(cursor.getString(index));
            }
            if (result.getOwner() == null) {
                final User owner = new User();
                index = cursor.getColumnIndex(CommentContract.COL_OWNER_ID);

                if (index > -1) {
                    owner.setId(cursor.getInt(index));
                    result.setOwner(owner);
                }

            }
            if (result.getPost() == null) {
                final Post post = new Post();
                index = cursor.getColumnIndex(CommentContract.COL_POST_ID);

                if (index > -1) {
                    post.setId(cursor.getInt(index));
                    result.setPost(post);
                }

            }
            index = cursor.getColumnIndex(CommentContract.COL_CREATEDAT);

            if (index > -1) {
                final DateTime dtCreatedAt =
                        DateUtils.formatISOStringToDateTime(cursor.getString(index));
                if (dtCreatedAt != null) {
                        result.setCreatedAt(dtCreatedAt);
                } else {
                    result.setCreatedAt(new DateTime());
                }
            }
            index = cursor.getColumnIndex(CommentContract.COL_VALIDATE);

            if (index > -1) {
                result.setValidate(cursor.getInt(index) == 1);
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Comment entity.
     * @param cursor Cursor object
     * @return Array of Comment entity
     */
    public static ArrayList<Comment> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Comment> result = new ArrayList<Comment>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Comment item;
            do {
                item = CommentContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
