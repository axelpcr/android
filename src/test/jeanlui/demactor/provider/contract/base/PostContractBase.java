/**************************************************************************
 * PostContractBase.java, demactor Android
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
import com.jeanlui.demactor.entity.Post;
import com.jeanlui.demactor.entity.User;
import com.jeanlui.demactor.entity.Comment;
import com.jeanlui.demactor.entity.Group;



import com.jeanlui.demactor.provider.contract.PostContract;
import com.jeanlui.demactor.harmony.util.DateUtils;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PostContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PostContract.TABLE_NAME + "." + COL_ID;

    /** title. */
    public static final String COL_TITLE =
            "title";
    /** Alias. */
    public static final String ALIASED_COL_TITLE =
            PostContract.TABLE_NAME + "." + COL_TITLE;

    /** content. */
    public static final String COL_CONTENT =
            "content";
    /** Alias. */
    public static final String ALIASED_COL_CONTENT =
            PostContract.TABLE_NAME + "." + COL_CONTENT;

    /** owner_id. */
    public static final String COL_OWNER_ID =
            "owner_id";
    /** Alias. */
    public static final String ALIASED_COL_OWNER_ID =
            PostContract.TABLE_NAME + "." + COL_OWNER_ID;

    /** createdAt. */
    public static final String COL_CREATEDAT =
            "created_at";
    /** Alias. */
    public static final String ALIASED_COL_CREATEDAT =
            PostContract.TABLE_NAME + "." + COL_CREATEDAT;

    /** updatedAt. */
    public static final String COL_UPDATEDAT =
            "updated_at";
    /** Alias. */
    public static final String ALIASED_COL_UPDATEDAT =
            PostContract.TABLE_NAME + "." + COL_UPDATEDAT;

    /** expiresAt. */
    public static final String COL_EXPIRESAT =
            "expires_at";
    /** Alias. */
    public static final String ALIASED_COL_EXPIRESAT =
            PostContract.TABLE_NAME + "." + COL_EXPIRESAT;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Post";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Post";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PostContract.COL_ID,
        
        PostContract.COL_TITLE,
        
        PostContract.COL_CONTENT,
        
        PostContract.COL_OWNER_ID,
        
        PostContract.COL_CREATEDAT,
        
        PostContract.COL_UPDATEDAT,
        
        PostContract.COL_EXPIRESAT
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PostContract.ALIASED_COL_ID,
        
        PostContract.ALIASED_COL_TITLE,
        
        PostContract.ALIASED_COL_CONTENT,
        
        PostContract.ALIASED_COL_OWNER_ID,
        
        
        
        PostContract.ALIASED_COL_CREATEDAT,
        
        PostContract.ALIASED_COL_UPDATEDAT,
        
        PostContract.ALIASED_COL_EXPIRESAT
    };


    /**
     * Converts a Post into a content values.
     *
     * @param item The Post to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Post item) {
        final ContentValues result = new ContentValues();

             result.put(PostContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getTitle() != null) {
                result.put(PostContract.COL_TITLE,
                    item.getTitle());
            }

             if (item.getContent() != null) {
                result.put(PostContract.COL_CONTENT,
                    item.getContent());
            }

             if (item.getOwner() != null) {
                result.put(PostContract.COL_OWNER_ID,
                    item.getOwner().getId());
            }

               if (item.getCreatedAt() != null) {
                result.put(PostContract.COL_CREATEDAT,
                    item.getCreatedAt().toString(ISODateTimeFormat.dateTime()));
            }

             if (item.getUpdatedAt() != null) {
                result.put(PostContract.COL_UPDATEDAT,
                    item.getUpdatedAt().toString(ISODateTimeFormat.dateTime()));
            }

             if (item.getExpiresAt() != null) {
                result.put(PostContract.COL_EXPIRESAT,
                    item.getExpiresAt().toString(ISODateTimeFormat.dateTime()));
            }


        return result;
    }

    /**
     * Converts a Cursor into a Post.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Post
     */
    public static Post cursorToItem(final android.database.Cursor cursor) {
        Post result = new Post();
        PostContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Post entity.
     * @param cursor Cursor object
     * @param result Post entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Post result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PostContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PostContract.COL_TITLE);

            if (index > -1) {
                result.setTitle(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PostContract.COL_CONTENT);

            if (index > -1) {
                result.setContent(cursor.getString(index));
            }
            if (result.getOwner() == null) {
                final User owner = new User();
                index = cursor.getColumnIndex(PostContract.COL_OWNER_ID);

                if (index > -1) {
                    owner.setId(cursor.getInt(index));
                    result.setOwner(owner);
                }

            }
            index = cursor.getColumnIndex(PostContract.COL_CREATEDAT);

            if (index > -1) {
                final DateTime dtCreatedAt =
                        DateUtils.formatISOStringToDateTime(cursor.getString(index));
                if (dtCreatedAt != null) {
                        result.setCreatedAt(dtCreatedAt);
                } else {
                    result.setCreatedAt(new DateTime());
                }
            }
            index = cursor.getColumnIndex(PostContract.COL_UPDATEDAT);

            if (index > -1) {
                final DateTime dtUpdatedAt =
                        DateUtils.formatISOStringToDateTime(cursor.getString(index));
                if (dtUpdatedAt != null) {
                        result.setUpdatedAt(dtUpdatedAt);
                } else {
                    result.setUpdatedAt(new DateTime());
                }
            }
            index = cursor.getColumnIndex(PostContract.COL_EXPIRESAT);

            if (index > -1) {
                final DateTime dtExpiresAt =
                        DateUtils.formatISOStringToDateTime(cursor.getString(index));
                if (dtExpiresAt != null) {
                        result.setExpiresAt(dtExpiresAt);
                } else {
                    result.setExpiresAt(new DateTime());
                }
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Post entity.
     * @param cursor Cursor object
     * @return Array of Post entity
     */
    public static ArrayList<Post> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Post> result = new ArrayList<Post>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Post item;
            do {
                item = PostContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
