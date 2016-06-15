/**************************************************************************
 * GroupProviderUtilsBase.java, demactor Android
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
import com.jeanlui.demactor.criterias.base.Criterion;
import com.jeanlui.demactor.criterias.base.Criterion.Type;
import com.jeanlui.demactor.criterias.base.value.ArrayValue;
import com.jeanlui.demactor.criterias.base.CriteriaExpression;
import com.jeanlui.demactor.criterias.base.CriteriaExpression.GroupType;

import com.jeanlui.demactor.entity.Group;
import com.jeanlui.demactor.entity.GroupToComment;

import com.jeanlui.demactor.provider.GroupProviderAdapter;
import com.jeanlui.demactor.provider.GroupToCommentProviderAdapter;
import com.jeanlui.demactor.provider.DemactorProvider;
import com.jeanlui.demactor.provider.contract.GroupContract;
import com.jeanlui.demactor.provider.contract.GroupToCommentContract;

/**
 * Group Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class GroupProviderUtilsBase
            extends ProviderUtils<Group> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "GroupProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public GroupProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Group item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = GroupContract.itemToContentValues(item);
        itemValues.remove(GroupContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                GroupProviderAdapter.GROUP_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getComments() != null && item.getComments().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(GroupToCommentContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getComments().size(); i++) {
                inValue.addValue(String.valueOf(item.getComments().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI)
                    .withValueBackReference(
                            GroupToCommentContract
                                    .COL_GROUP_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }

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
     * Delete from DB.
     * @param item Group
     * @return number of row affected
     */
    public int delete(final Group item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = GroupProviderAdapter.GROUP_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Group
     */
    public Group query(final Group item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Group
     */
    public Group query(final int id) {
        Group result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(GroupContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            GroupProviderAdapter.GROUP_URI,
            GroupContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = GroupContract.cursorToItem(cursor);

            result.setComments(
                this.getAssociateComments(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Group>
     */
    public ArrayList<Group> queryAll() {
        ArrayList<Group> result =
                    new ArrayList<Group>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                GroupProviderAdapter.GROUP_URI,
                GroupContract.ALIASED_COLS,
                null,
                null,
                null);

        result = GroupContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Group>
     */
    public ArrayList<Group> query(CriteriaExpression expression) {
        ArrayList<Group> result =
                    new ArrayList<Group>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                GroupProviderAdapter.GROUP_URI,
                GroupContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = GroupContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Group
     
     * @return number of rows updated
     */
    public int update(final Group item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = GroupContract.itemToContentValues(
                item);

        Uri uri = GroupProviderAdapter.GROUP_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getComments() != null && item.getComments().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new comments for Group
            CriteriaExpression commentsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(GroupToCommentContract.COL_ID);
            crit.addValue(values);
            commentsCrit.add(crit);


            for (GroupToComment comments : item.getComments()) {
                values.addValue(
                    String.valueOf(comments.getId()));
            }
            selection = commentsCrit.toSQLiteSelection();
            selectionArgs = commentsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI)
                    .withValue(
                            GroupToCommentContract.COL_GROUP_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated comments
            crit.setType(Type.NOT_IN);
            commentsCrit.add(GroupToCommentContract.COL_GROUP_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI)
                    .withValue(
                            GroupToCommentContract.COL_GROUP_ID,
                            null)
                    .withSelection(
                            commentsCrit.toSQLiteSelection(),
                            commentsCrit.toSQLiteSelectionArgs())
                    .build());
        }


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
     * Get associate Comments.
     * @param item Group
     * @return GroupToComment
     */
    public ArrayList<GroupToComment> getAssociateComments(
            final Group item) {
        ArrayList<GroupToComment> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor groupToCommentCursor = prov.query(
                GroupToCommentProviderAdapter.GROUPTOCOMMENT_URI,
                GroupToCommentContract.ALIASED_COLS,
                GroupToCommentContract.ALIASED_COL_GROUP_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = GroupToCommentContract.cursorToItems(
                        groupToCommentCursor);
        groupToCommentCursor.close();

        return result;
    }

}
