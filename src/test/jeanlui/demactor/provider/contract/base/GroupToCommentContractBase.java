/**************************************************************************
 * GroupToCommentContractBase.java, demactor Android
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

import com.jeanlui.demactor.entity.GroupToComment;
import com.jeanlui.demactor.entity.Group;



import com.jeanlui.demactor.provider.contract.GroupToCommentContract;

/** Demactor contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class GroupToCommentContractBase {


        /** CommentgroupsInternal_id. */
    public static final String COL_COMMENTGROUPSINTERNAL_ID =
            "Comment_groups_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_COMMENTGROUPSINTERNAL_ID =
            GroupToCommentContract.TABLE_NAME + "." + COL_COMMENTGROUPSINTERNAL_ID;

    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            GroupToCommentContract.TABLE_NAME + "." + COL_ID;

    /** displayName. */
    public static final String COL_DISPLAYNAME =
            "displayName";
    /** Alias. */
    public static final String ALIASED_COL_DISPLAYNAME =
            GroupToCommentContract.TABLE_NAME + "." + COL_DISPLAYNAME;

    /** group_id. */
    public static final String COL_GROUP_ID =
            "group_id";
    /** Alias. */
    public static final String ALIASED_COL_GROUP_ID =
            GroupToCommentContract.TABLE_NAME + "." + COL_GROUP_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "GroupToComment";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "GroupToComment";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        GroupToCommentContract.COL_COMMENTGROUPSINTERNAL_ID,
        
        GroupToCommentContract.COL_ID,
        
        GroupToCommentContract.COL_DISPLAYNAME,
        
        GroupToCommentContract.COL_GROUP_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        GroupToCommentContract.ALIASED_COL_COMMENTGROUPSINTERNAL_ID,
        
        GroupToCommentContract.ALIASED_COL_ID,
        
        GroupToCommentContract.ALIASED_COL_DISPLAYNAME,
        
        GroupToCommentContract.ALIASED_COL_GROUP_ID
    };

    /** Convert GroupToComment entity to Content Values for database.
     *
     * @param item GroupToComment entity object
     * @param commentId comment id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final GroupToComment item,
                final int commentgroupsInternalId) {
        final ContentValues result = GroupToCommentContract.itemToContentValues(item);
        result.put(GroupToCommentContract.COL_COMMENTGROUPSINTERNAL_ID,
                String.valueOf(commentgroupsInternalId));
        return result;
    }

    /**
     * Converts a GroupToComment into a content values.
     *
     * @param item The GroupToComment to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final GroupToComment item) {
        final ContentValues result = new ContentValues();

              result.put(GroupToCommentContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getDisplayName() != null) {
                result.put(GroupToCommentContract.COL_DISPLAYNAME,
                    item.getDisplayName());
            }

             if (item.getGroup() != null) {
                result.put(GroupToCommentContract.COL_GROUP_ID,
                    item.getGroup().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a GroupToComment.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted GroupToComment
     */
    public static GroupToComment cursorToItem(final android.database.Cursor cursor) {
        GroupToComment result = new GroupToComment();
        GroupToCommentContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to GroupToComment entity.
     * @param cursor Cursor object
     * @param result GroupToComment entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final GroupToComment result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(GroupToCommentContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(GroupToCommentContract.COL_DISPLAYNAME);

            if (index > -1) {
                result.setDisplayName(cursor.getString(index));
            }
            if (result.getGroup() == null) {
                final Group group = new Group();
                index = cursor.getColumnIndex(GroupToCommentContract.COL_GROUP_ID);

                if (index > -1) {
                    group.setId(cursor.getInt(index));
                    result.setGroup(group);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of GroupToComment entity.
     * @param cursor Cursor object
     * @return Array of GroupToComment entity
     */
    public static ArrayList<GroupToComment> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<GroupToComment> result = new ArrayList<GroupToComment>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            GroupToComment item;
            do {
                item = GroupToCommentContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
