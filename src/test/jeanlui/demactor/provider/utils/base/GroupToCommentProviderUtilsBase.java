/**************************************************************************
 * GroupToCommentProviderUtilsBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;


import com.jeanlui.demactor.provider.utils.ProviderUtils;
import com.jeanlui.demactor.criterias.base.CriteriaExpression;
import com.jeanlui.demactor.criterias.base.CriteriaExpression.GroupType;

import com.jeanlui.demactor.entity.GroupToComment;
import com.jeanlui.demactor.entity.Group;

import com.jeanlui.demactor.provider.GroupToCommentProviderAdapter;
import com.jeanlui.demactor.provider.GroupProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;
import com.jeanlui.demactor.provider.contract.CommentContract;
import com.jeanlui.demactor.provider.contract.GroupContract;

/**
 * GroupToComment Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class GroupToCommentProviderUtilsBase
            extends ProviderUtils<GroupToComment> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "GroupToCommentProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public GroupToCommentProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final GroupToComment item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = GroupToCommentContract.itemToContentValues(item);

        operations.add(ContentProviderOperation.newInsert(
                GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI)
                        .withValues(itemValues)
                        .build());


        try {
            ContentProviderResult[] results =
                    prov.applyBatch(DemactorProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getPathSegments().get(1)));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /**
     * Insert into DB.
     * @param item GroupToComment to insert
     * @param commentgroupsInternalId commentgroupsInternal Id
     * @return number of rows affected
     */
    public Uri insert(final GroupToComment item,
                             final int commentgroupsInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = GroupToCommentContract.itemToContentValues(item,
                    commentgroupsInternalId);

        operations.add(ContentProviderOperation.newInsert(
                GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI)
                    .withValues(itemValues)
                    .build());



        try {
            ContentProviderResult[] results =
                prov.applyBatch(DemactorProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /**
     * Delete from DB.
     * @param item GroupToComment
     * @return number of row affected
     */
    public int delete(final GroupToComment item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return GroupToComment
     */
    public GroupToComment query(final GroupToComment item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return GroupToComment
     */
    public GroupToComment query(final int id) {
        GroupToComment result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(GroupToCommentContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI,
            GroupToCommentContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = GroupToCommentContract.cursorToItem(cursor);

            if (result.getGroup() != null) {
                result.setGroup(
                    this.getAssociateGroup(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<GroupToComment>
     */
    public ArrayList<GroupToComment> queryAll() {
        ArrayList<GroupToComment> result =
                    new ArrayList<GroupToComment>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI,
                GroupToCommentContract.ALIASED_COLS,
                null,
                null,
                null);

        result = GroupToCommentContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<GroupToComment>
     */
    public ArrayList<GroupToComment> query(CriteriaExpression expression) {
        ArrayList<GroupToComment> result =
                    new ArrayList<GroupToComment>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI,
                GroupToCommentContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = GroupToCommentContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item GroupToComment
     * @return number of rows updated
     */
    public int update(final GroupToComment item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = GroupToCommentContract.itemToContentValues(item);

        Uri uri = GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());



        try {
            ContentProviderResult[] results = prov.applyBatch(DemactorProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /**
     * Updates the DB.
     * @param item GroupToComment
     * @param commentgroupsInternalId commentgroupsInternal Id
     * @return number of rows updated
     */
    public int update(final GroupToComment item,
                             final int commentgroupsInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = GroupToCommentContract.itemToContentValues(
                item,
                commentgroupsInternalId);

        Uri uri = GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());



        try {
            ContentProviderResult[] results = prov.applyBatch(DemactorProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /** Relations operations. */
    /**
     * Get associate Group.
     * @param item GroupToComment
     * @return Group
     */
    public Group getAssociateGroup(
            final GroupToComment item) {
        Group result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor groupCursor = prov.query(
                GroupProviderAdapter.GROUP_URI,
                GroupContract.ALIASED_COLS,
                GroupContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getGroup().getId())},
                null);

        if (groupCursor.getCount() > 0) {
            groupCursor.moveToFirst();
            result = GroupContract.cursorToItem(groupCursor);
        } else {
            result = null;
        }
        groupCursor.close();

        return result;
    }

}
